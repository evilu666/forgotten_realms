package com.evilu.forgottenRealms.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

/**
 * DimensionPortalBlock
 */
public class DimensionPortalBlock extends Block implements ITeleporter {

    protected static final VoxelShape Y_AXIS_AABB = Block.box(0.0D, 4.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    private final ResourceKey<Level> targetDimension;

    public DimensionPortalBlock(final MaterialColor color, final ResourceKey<Level> targetDimension) {
        super(Properties.of(Material.AIR, color).dynamicShape());
        this.targetDimension = targetDimension;
    }

    @Override
    protected ImmutableMap<BlockState, VoxelShape> getShapeForEachState(final Function<BlockState, VoxelShape> mapperFunc) {

        return super.getShapeForEachState(state -> Y_AXIS_AABB);
    }

    @Override
    public boolean hasDynamicShape() {
        return true;
    }

    @Override
    public VoxelShape getVisualShape(BlockState p_60479_, BlockGetter p_60480_, BlockPos p_60481_, CollisionContext p_60482_) {
        return Y_AXIS_AABB;
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState p_60581_, BlockGetter p_60582_, BlockPos p_60583_) {
        return Y_AXIS_AABB;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState p_60547_, BlockGetter p_60548_, BlockPos p_60549_) {
        return Y_AXIS_AABB;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Y_AXIS_AABB;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
        return Y_AXIS_AABB;
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        return false;
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
