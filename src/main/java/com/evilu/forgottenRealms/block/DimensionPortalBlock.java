package com.evilu.forgottenRealms.block;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.ITeleporter;

/**
 * DimensionPortalBlock
 */
public class DimensionPortalBlock extends Block implements ITeleporter {

    protected static final VoxelShape Y_AXIS_AABB = Block.box(0.0D, 4.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    private final ResourceKey<Level> targetDimension;

    public DimensionPortalBlock(final MaterialColor color, final ResourceKey<Level> targetDimension) {
        super(Properties.copy(Blocks.NETHER_PORTAL).color(color));
        this.targetDimension = targetDimension;
    }

    @Override
    public VoxelShape getCollisionShape(final BlockState blockState, final BlockGetter blockGetter, final BlockPos blockPos, final CollisionContext collisionContext) {
        return Y_AXIS_AABB;
    }

    @Override
    public void entityInside(final BlockState blockState, final Level level, final BlockPos pos, final Entity entity) {
        if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
            if (level instanceof ServerLevel) {
                final MinecraftServer minecraftserver = ((ServerLevel) level).getServer();
                final ServerLevel serverlevel = minecraftserver.getLevel(targetDimension);
                entity.changeDimension(serverlevel, this);
            }
        }
    }
}
