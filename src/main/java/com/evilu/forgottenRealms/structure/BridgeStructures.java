package com.evilu.forgottenRealms.structure;

import com.evilu.forgottenRealms.structure.BaseStructureTemplate.BlockType;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.WallSide;

/**
 * BridgeStructures
 */
public class BridgeStructures {

    public static enum BridgeStructureBlockType implements BlockType {

        WALL_POST('W'),
        WALL_CONNECTED('w'),
        BASIS('B'),
        BASIS_ACCENT('C'),
        STAIRS_START('S'),
        STAIRS_END('N'),
        SIDE('E'),
        ROOF_OUTER('R'),
        ROOF_INNER('r'),
        LANTERN('L')

        ;

        private final char sign;

        private BridgeStructureBlockType(final char sign) {
            this.sign = sign;
        }

        @Override
        public char getSign() {
            return sign;
        }

    }

  private static StructureTemplate<BridgeStructureBlockType> structure(final String name) {
      return new StructureTemplate<>(name, BridgeStructureBlockType.class);
  }

    public static final StructureTemplate<BridgeStructureBlockType> NORTH_SOUTH = structure("bridge_ns")
        .withBlock(BridgeStructureBlockType.WALL_POST, Blocks.DEEPSLATE_BRICK_WALL)
        .withBlockState(BridgeStructureBlockType.WALL_CONNECTED, Blocks.DEEPSLATE_BRICK_WALL
                .defaultBlockState()
                .setValue(BlockStateProperties.NORTH_WALL, WallSide.LOW)
                .setValue(BlockStateProperties.SOUTH_WALL, WallSide.LOW))
        .withBlock(BridgeStructureBlockType.BASIS, Blocks.DEEPSLATE_BRICKS)
        .withBlock(BridgeStructureBlockType.BASIS_ACCENT, Blocks.CRACKED_DEEPSLATE_BRICKS)
        .withBlockState(BridgeStructureBlockType.STAIRS_START, Blocks.DEEPSLATE_BRICK_STAIRS
                .defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH))
        .withBlockState(BridgeStructureBlockType.STAIRS_END, Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH))
        .withBlock(BridgeStructureBlockType.SIDE, Blocks.DEEPSLATE_BRICKS)
        .withBlock(BridgeStructureBlockType.ROOF_INNER, Blocks.DEEPSLATE_BRICKS)
        .withBlock(BridgeStructureBlockType.ROOF_OUTER, Blocks.DEEPSLATE_BRICKS)
        .withBlock(BridgeStructureBlockType.LANTERN, Blocks.LANTERN);

    public static final StructureTemplate<BridgeStructureBlockType> EAST_WEST = structure("bridge_ew")
        .withBlock(BridgeStructureBlockType.WALL_POST, Blocks.DEEPSLATE_BRICK_WALL)
        .withBlockState(BridgeStructureBlockType.WALL_CONNECTED, Blocks.DEEPSLATE_BRICK_WALL
                .defaultBlockState()
                .setValue(BlockStateProperties.EAST_WALL, WallSide.LOW)
                .setValue(BlockStateProperties.WEST_WALL, WallSide.LOW))
        .withBlock(BridgeStructureBlockType.BASIS, Blocks.DEEPSLATE_BRICKS)
        .withBlock(BridgeStructureBlockType.BASIS_ACCENT, Blocks.CRACKED_DEEPSLATE_BRICKS)
        .withBlockState(BridgeStructureBlockType.STAIRS_START, Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST))
        .withBlockState(BridgeStructureBlockType.STAIRS_END, Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST))
        .withBlock(BridgeStructureBlockType.SIDE, Blocks.DEEPSLATE_BRICKS)
        .withBlock(BridgeStructureBlockType.ROOF_INNER, Blocks.DEEPSLATE_BRICKS)
        .withBlock(BridgeStructureBlockType.ROOF_OUTER, Blocks.DEEPSLATE_BRICKS)
        .withBlock(BridgeStructureBlockType.LANTERN, Blocks.LANTERN);


}
