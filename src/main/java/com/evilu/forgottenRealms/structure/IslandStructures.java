package com.evilu.forgottenRealms.structure;

import com.evilu.forgottenRealms.registry.BlockRegistry;
import com.evilu.forgottenRealms.structure.BaseStructureTemplate.BlockType;

import net.minecraft.world.level.block.Blocks;

/**
 * IslandStructureTemplate
 */
public class IslandStructures {

  public static enum IslandStructureBlockType implements BlockType {
      BASIS('1'),
      ACCENT_1('2'),
      ACCENT_2('4'),
      TOP_1('3'),
      TOP_2('5'),
      TOP_3('6'),
      PORTAL_FRAME('B'),
      PORTAL_BLOCK('P')
      ;

      private final char sign;

      private IslandStructureBlockType(final char sign) {
          this.sign = sign;
      }

      public char getSign() {
        return sign;
      }
  }

  private static StructureTemplate<IslandStructureBlockType> structure(final String name) {
      return new StructureTemplate<>(name, IslandStructureBlockType.class);
  }

  public static final StructureTemplate<IslandStructureBlockType> START_ISLAND = structure("island")
      .withBlock(IslandStructureBlockType.BASIS, Blocks.DEEPSLATE_BRICKS)
      .withBlock(IslandStructureBlockType.ACCENT_1, Blocks.CRACKED_DEEPSLATE_BRICKS)
      .withBlock(IslandStructureBlockType.ACCENT_2, Blocks.DEEPSLATE_TILES)
      .withBlock(IslandStructureBlockType.TOP_1, Blocks.DEEPSLATE_TILES)
      .withBlock(IslandStructureBlockType.TOP_2, Blocks.DEEPSLATE_TILE_SLAB)
      .withBlock(IslandStructureBlockType.TOP_3, Blocks.DEEPSLATE_BRICK_SLAB)
      .withBlock(IslandStructureBlockType.PORTAL_FRAME, Blocks.CHISELED_DEEPSLATE);

  public static final StructureTemplate<IslandStructureBlockType> SAND_ISLAND = structure("island")
      .withBlock(IslandStructureBlockType.BASIS, Blocks.SANDSTONE)
      .withBlock(IslandStructureBlockType.ACCENT_1, Blocks.SMOOTH_SANDSTONE)
      .withBlock(IslandStructureBlockType.ACCENT_2, Blocks.CUT_SANDSTONE)
      .withBlock(IslandStructureBlockType.TOP_1, Blocks.SAND)
      .withBlock(IslandStructureBlockType.TOP_2, Blocks.SANDSTONE_SLAB)
      .withBlock(IslandStructureBlockType.TOP_3, Blocks.SMOOTH_SANDSTONE_SLAB)
      .withBlock(IslandStructureBlockType.PORTAL_FRAME, Blocks.CHISELED_SANDSTONE);

  public static final StructureTemplate<IslandStructureBlockType> OVERWORLD_ISLAND = structure("island")
      .withBlock(IslandStructureBlockType.BASIS, Blocks.STONE)
      .withBlock(IslandStructureBlockType.ACCENT_1, Blocks.GRASS)
      .withBlock(IslandStructureBlockType.ACCENT_2, Blocks.OAK_LEAVES)
      .withBlock(IslandStructureBlockType.TOP_1, Blocks.COBBLESTONE)
      .withBlock(IslandStructureBlockType.TOP_2, Blocks.STONE_SLAB)
      .withBlock(IslandStructureBlockType.TOP_3, Blocks.COBBLESTONE_SLAB)
      .withBlock(IslandStructureBlockType.PORTAL_FRAME, Blocks.OAK_WOOD)
      .withBlock(IslandStructureBlockType.PORTAL_BLOCK, BlockRegistry.OVERWORLD_PORTAL::get);






    
}
