package com.evilu.forgottenRealms.structure;

import java.util.Set;

import com.evilu.forgottenRealms.structure.BaseStructureTemplate.BlockType;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;

/**
 * PlaceableStructure
 */
public class PlaceableStructure<T extends Enum<T> & BlockType> {

    private final BlockPos position;
    private final StructureTemplate<T> template;
    private final Set<ChunkPos> chunks;

    public PlaceableStructure(final BlockPos position, final StructureTemplate<T> template) {
        this.position = position;
        this.template = template;
        this.chunks = template.getChunks(position);
    }

    public boolean generate(final ChunkAccess chunkAccess) {
        if (chunks.contains(chunkAccess.getPos())) {
            template.generate(chunkAccess, position);
            return true;
        }

        return false;
    }
    
}
