package com.evilu.forgottenRealms.worldgen;

import com.evilu.forgottenRealms.util.JigsawPlacement;


import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;

/**
 * CustomizedStructureFeature
 */
public class CustomizedStructureFeature extends StructureFeature<CustomStructureConfig> {

	private final Decoration generationStep;

	public CustomizedStructureFeature() {
		this(Decoration.SURFACE_STRUCTURES);
	}

	@SuppressWarnings("unchecked")
	public CustomizedStructureFeature(final Decoration generationStep) {
		super(CustomStructureConfig.CODEC, (context) -> {
			BlockPos blockpos = new BlockPos(context.chunkPos().getMinBlockX(), context.config().sampleYLevel(context.seed()), context.chunkPos().getMinBlockZ());
			Pools.bootstrap();
			return JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, blockpos, 256, true, false);
		});

		this.generationStep = generationStep;
	}

	@Override
	public Decoration step() {
		return generationStep;
	}

}
