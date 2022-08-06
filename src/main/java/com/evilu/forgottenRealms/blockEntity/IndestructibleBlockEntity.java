package com.evilu.forgottenRealms.blockEntity;

import com.evilu.forgottenRealms.registry.BlockEntityTypeRegistry;

import org.apache.commons.lang3.mutable.MutableObject;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * IndestructibleBlockEntity
 */
public class IndestructibleBlockEntity extends BlockEntity {

  private final MutableObject<Block> textureSource = new MutableObject<>(Blocks.STONE);
  private final MutableObject<BlockState> blockState = new MutableObject<>(Blocks.STONE.defaultBlockState());

  public IndestructibleBlockEntity(final BlockPos pos, final BlockState blockState) {
    super(BlockEntityTypeRegistry.INDESTRUCTIBLE.get(), pos, blockState);
  }

  @Override
  protected void saveAdditional(final CompoundTag tag) {
    super.saveAdditional(tag);

    if (textureSource.getValue() != null) {
      tag.putString("textureSource", textureSource.getValue().getRegistryName().toString());
    }

    if (blockState.getValue() != null) {
      tag.put("blockState", BlockState.CODEC.encodeStart(NbtOps.INSTANCE, blockState.getValue()).result().orElseThrow());
    }
  }

  @Override
  public void load(final CompoundTag tag) {
    super.load(tag);

    if (tag.contains("textureSource")) {
      final String[] resourcePath = tag.getString("textureSource").split(":");
      if (resourcePath.length == 2) {
        final ResourceLocation blockLocation = new ResourceLocation(resourcePath[0], resourcePath[1]);
        final Block newTextureSource = ForgeRegistries.BLOCKS.getValue(blockLocation);
        if (newTextureSource != null) {
          textureSource.setValue(newTextureSource);
        }
      }
    }

    if (tag.contains("blockState")) {
      BlockState.CODEC.parse(NbtOps.INSTANCE, tag.get("blockState")).result().ifPresent(blockState::setValue);
    }
  }

  public Block getTextureSourceBlock() {
    if (textureSource.getValue() == null)
      throw new IllegalStateException("Texture source block is null");
    return textureSource.getValue();
  }

  public void setBlockSource(final Block source) {
    if (source != null) {
      textureSource.setValue(source);
      setChanged();
    }
  }

  public void setBlockState(final BlockState blockState) {
    if (blockState != null) {
      this.blockState.setValue(blockState);
      setChanged();
    }
  }

  public BlockState getBlockState() {
    if (blockState.getValue() == null) throw new IllegalStateException("Blockstate is null");
    return blockState.getValue();
  }

  public BlockState getEffectiveBlockState() {
    return getTextureSourceBlock().withPropertiesOf(getBlockState());
  }

}
