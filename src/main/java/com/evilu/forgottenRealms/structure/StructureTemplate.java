package com.evilu.forgottenRealms.structure;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.evilu.forgottenRealms.structure.BaseStructureTemplate.BlockType;
import com.evilu.forgottenRealms.util.DefaultEnumMap;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

/**
 * StructureTemplate
 */
public class StructureTemplate<T extends Enum<T> & BlockType> extends BaseStructureTemplate<T> {

    private final Map<T, Supplier<BlockState>> stateMap;

    public StructureTemplate(final String name, final Class<T> blockTypeClass, final Supplier<BlockState> defaultState) {
        super(String.format("structure_templates/%s", name));
        stateMap = new DefaultEnumMap<T, Supplier<BlockState>>(blockTypeClass, defaultState);
    }

    public StructureTemplate(final String name, final Class<T> blockTypeClass) {
        this(name, blockTypeClass, Blocks.AIR::defaultBlockState);
    }

    public StructureTemplate<T> withBlockState(final T blockType, final Supplier<BlockState> blockState) {
        stateMap.put(blockType, blockState);
        return this;
    }

    public StructureTemplate<T> withBlockState(final T blockType, final BlockState blockState) {
        return withBlockState(blockType, () -> blockState);
    }

    public StructureTemplate<T> withBlock(final T blockType, final Supplier<Block> block) {
        return withBlockState(blockType, () -> block.get().defaultBlockState());
    }

    public StructureTemplate<T> withBlock(final T blockType, final Block block) {
        return withBlockState(blockType, block::defaultBlockState);
    }

    public void generate(final ChunkAccess chunkAccess, final BlockPos pos) {
        generateStructureChunk(stateMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get())), chunkAccess, pos);
    }

    public PlaceableStructure<T> placeAt(final BlockPos position) {
        return new PlaceableStructure<>(position, this);
    }
}
