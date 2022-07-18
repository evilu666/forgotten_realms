package com.evilu.forgottenRealms.structure;

import java.util.*;
import java.util.stream.Collectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

/**
 * StructureTemplate
 */
public class BaseStructureTemplate<T extends BaseStructureTemplate.BlockType> {

    public static interface BlockType {
        public char getSign();
    }

    private final int width, depth, height;
    private final Map<BlockPos, Character> template;

    protected BaseStructureTemplate(final String path) {
		this(path, false);
	}

    protected BaseStructureTemplate(final String path, final boolean includeWhitespace) {
        int layers = 0;
        int width = 0;
        int depth = 0;

        template = new LinkedHashMap<>();

        for (int i = 0; i < 999; ++i) {
            final String layer_path = String.format("%s/%03d", path, i+1);
            final InputStream is = BaseStructureTemplate.class.getClassLoader().getResourceAsStream(layer_path);
            if (is != null) {
                layers++;
                final List<String> lines;

                try (final BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    lines = reader.lines().toList();
                    reader.close();
                } catch (final IOException e) {
                    throw new IllegalStateException(String.format("Error reading layer %d"), e);
                }

                if (width < 1 || depth < 1) {
                    depth = lines.size();
                    if (depth < 1) throw new IllegalArgumentException(String.format("Invalid layer %d: no lines", i+1));
                    width = lines.get(0).length();
                } else {
                    if (lines.size() != depth) throw new IllegalArgumentException(String.format("Invalid layer %d: expected %d lines but got %d", i+1, depth, lines.size()));
                }

                for (int l = 0; l < lines.size(); ++l) {
                    final String line = lines.get(l);
                    if (line.length() != width) throw new IllegalArgumentException(String.format("Error in line %d of layer %d: expected length %d but got %d", l+1, i+1, width, line.length()));
                    for (int w = 0; w < width; ++w) {
                        final char c = line.charAt(w);
						if (includeWhitespace || !Character.isWhitespace(c)) {
							final BlockPos pos = new BlockPos(w, i, l);
							template.put(pos, c);
						}
                    }
                }
            } else break;

            if (layers == 0) throw new IllegalStateException("No layers found for structure path: " + path);
        }

        this.width = width;
        this.depth = depth;
        this.height = layers;
    }

    protected void generateStructureChunk(final Map<T, BlockState> blockStates, final ChunkAccess chunkAccess, final BlockPos position) {
        final Map<Character, BlockState> states = blockStates.entrySet()
            .stream()
            .collect(Collectors.toMap(e -> e.getKey().getSign(), Map.Entry::getValue));

        for (final Map.Entry<BlockPos, Character> entry : template.entrySet()) {
            final BlockPos pos = position.offset(entry.getKey());
            if (isInChunk(pos, chunkAccess)) {
                final BlockState state = states.get(entry.getValue());
                if (state != null) {
                    chunkAccess.setBlockState(pos, state, true);
                }
            }
        }
    }
    

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public int getHeight() {
        return height;
    }

    private boolean isInChunk(final BlockPos p, final ChunkAccess chunkAccess) {
        final ChunkPos pos = chunkAccess.getPos();
        return  p.getX() >= pos.getMinBlockX() && p.getX() <= pos.getMaxBlockX() && p.getZ() >= pos.getMinBlockZ() && p.getZ() <= pos.getMaxBlockZ();
    }

    public Set<ChunkPos> getChunks(final BlockPos position) {
        final int xShift = width / 16;
        final int zShift = depth / 16;

        final Set<ChunkPos> chunks = new HashSet<>();

        for (int x = 0; x <= xShift; ++x) {
            for (int z = 0; z <= zShift; ++z) {
                chunks.add(new ChunkPos(position.offset(x * 16, 0, z * 16)));
                if (x == xShift) {
                    if (z == zShift) {
                        chunks.add(new ChunkPos(position.offset(width, 0, depth)));
                    } else {
                        chunks.add(new ChunkPos(position.offset(width, 0, z * 16)));
                    }
                } else if (z == zShift && x != xShift) {
                    chunks.add(new ChunkPos(position.offset(x * 16, 0, depth)));
                }
            }
        }

        return chunks;
    }

    
}
