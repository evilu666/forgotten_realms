package com.evilu.forgottenRealms.block;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.evilu.forgottenRealms.blockEntity.IndestructibleBlockEntity;
import com.google.common.collect.ImmutableMap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * IndestructibleBlock
 */
public class IndestructibleBlock extends Block implements EntityBlock {

    public IndestructibleBlock() {
        super(Properties.copy(Blocks.STONE));
    }

    @Override
    public boolean onDestroyedByPlayer(final BlockState state, final Level level, final BlockPos pos, final Player player, final boolean willHarvest, final FluidState fluid) {
        return player.isCreative();
    }

    @Override
    public VoxelShape getVisualShape(final BlockState blockState, final BlockGetter blockGetter, final BlockPos blockPos, final CollisionContext collisionContext) {
        return withMirrorData(blockGetter, blockPos, (block, state) -> block.getVisualShape(blockState, blockGetter, blockPos, collisionContext), () -> super.getVisualShape(blockState, blockGetter, blockPos, collisionContext));
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState blockState, BlockGetter p_60582_, BlockPos p_60583_) {
        return withMirrorData(p_60582_, p_60583_, (block, state) -> block.getBlockSupportShape(blockState, p_60582_, p_60583_), () -> super.getBlockSupportShape(blockState, p_60582_, p_60583_));
    }

    @Override
    public VoxelShape getInteractionShape(BlockState blockState, BlockGetter p_60548_, BlockPos p_60549_) {
        return withMirrorData(p_60548_, p_60549_, (block, state) -> block.getInteractionShape(blockState, p_60548_, p_60549_), () -> super.getInteractionShape(blockState, p_60548_, p_60549_));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return withMirrorData(p_60556_, p_60557_, (block, state) -> block.getShape(blockState, p_60556_, p_60557_, p_60558_), () -> super.getShape(blockState, p_60556_, p_60557_, p_60558_));
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState blockState, BlockGetter p_60579_, BlockPos p_60580_) {
        return withMirrorData(p_60579_, p_60580_, (block, state) -> block.getOcclusionShape(blockState, p_60579_, p_60580_), () -> super.getOcclusionShape(blockState, p_60579_, p_60580_));
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState blockState, BlockState neighborState, Direction dir) {
        return withMirrorData(level, pos, (block, state) -> block.hidesNeighborFace(level, pos, blockState, neighborState, dir), () -> super.hidesNeighborFace(level, pos, blockState, neighborState, dir));
    }

    @Override
    public VoxelShape getCollisionShape(final BlockState blockState, final BlockGetter blockGetter, final BlockPos blockPos, final CollisionContext collisionContext) {
        return withMirrorData(blockGetter, blockPos, (block, state) -> block.getCollisionShape(blockState, blockGetter, blockPos, collisionContext), () -> super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext));
    }


    @Override
    public BlockEntity newBlockEntity(final BlockPos pos, final BlockState blockState) {
        return new IndestructibleBlockEntity(pos, blockState);
    }

    @Override
    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
        return withMirrorData(level, pos, (block, state) -> block.addLandingEffects(state1, level, pos, state2, entity, numberOfParticles), () -> super.addLandingEffects(state1, level, pos, state2, entity, numberOfParticles));
    }

    @Override
    public boolean addRunningEffects(BlockState blockState, Level level, BlockPos pos, Entity entity) {
        return withMirrorData(level, pos, (block, state) -> block.addRunningEffects(blockState, level, pos, entity), () -> super.addRunningEffects(blockState, level, pos, entity));
    }

    @Override
    public boolean canConnectRedstone(BlockState blockState, BlockGetter level, BlockPos pos, Direction direction) {
        return withMirrorData(level, pos, (block, state) -> block.canConnectRedstone(blockState, level, pos, direction), () -> super.canConnectRedstone(blockState, level, pos, direction));
    }

    @Override
    public int getLightBlock(BlockState blockState, BlockGetter p_60586_, BlockPos p_60587_) {
        return withMirrorData(p_60586_, p_60587_, (block, state) -> block.getLightBlock(blockState, p_60586_, p_60587_), () -> super.getLightBlock(blockState, p_60586_, p_60587_));
    }

    @Override
    public int getLightEmission(BlockState blockState, BlockGetter level, BlockPos pos) {
        return withMirrorData(level, pos, (block, state) -> block.getLightEmission(blockState, level, pos), () -> super.getLightEmission(blockState, level, pos));
    }

    @Override
    public SoundType getSoundType(BlockState blockState, LevelReader level, BlockPos pos, Entity entity) {
        return withMirrorData(level, pos, (block, state) -> block.getSoundType(blockState, level, pos, entity), () -> super.getSoundType(blockState, level, pos, entity));
    }

    @Override
    public BlockPathTypes getAiPathNodeType(final BlockState blockState, final BlockGetter level, final BlockPos pos, final Mob entity) {
        return withMirrorData(level, pos, (block, state) -> block.getAiPathNodeType(state, level, pos, entity), () -> super.getAiPathNodeType(blockState, level, pos, entity));
    }

    @Override
    public boolean isPathfindable(final BlockState blockState, final BlockGetter blockGetter, final BlockPos pos, final PathComputationType type) {
        return withMirrorData(blockGetter, pos, (block, state) -> block.isPathfindable(state, blockGetter, pos, type), () -> super.isPathfindable(blockState, blockGetter, pos, type));
    }

    @Override
    public float getShadeBrightness(final BlockState newState, final BlockGetter blockGetter, final BlockPos pos) {
        return withMirrorData(blockGetter, pos, (block, state) -> block.getShadeBrightness(state, blockGetter, pos), () -> super.getShadeBrightness(newState, blockGetter, pos));
    }

    @Override
    public float getFriction(BlockState blockState, LevelReader level, BlockPos pos, Entity entity) {
        return withMirrorData(level, pos, (block, state) -> block.getFriction(state, level, pos, entity), () -> super.getFriction(blockState, level, pos, entity));
    }

    @Override
    public boolean propagatesSkylightDown(final BlockState blockState, BlockGetter p_49929_, BlockPos p_49930_) {
        return withMirrorData(p_49929_, p_49930_, (block, state) -> block.propagatesSkylightDown(state, p_49929_, p_49930_), () -> super.propagatesSkylightDown(blockState, p_49929_, p_49930_));
    }

    @Override
    public BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_) {
        return withMirrorData(p_60544_, p_60546_, (block, state) -> block.updateShape(state, p_60542_, p_60543_, p_60544_, p_60545_, p_60546_), () -> super.updateShape(p_60541_, p_60542_, p_60543_, p_60544_, p_60545_, p_60546_));
    }

    @Override
    public void setPlacedBy(Level p_49847_, BlockPos p_49848_, BlockState p_49849_, LivingEntity p_49850_, ItemStack p_49851_) {
        mirror(p_49847_, p_49848_, (block, state) -> block.setPlacedBy(p_49847_, p_49848_, state, p_49850_, p_49851_), () -> super.setPlacedBy(p_49847_, p_49848_, p_49849_, p_49850_, p_49851_));
    }

    private final <T> T withMirrorData(final BlockGetter blockGetter, final BlockPos pos, final BiFunction<Block, BlockState, T> mapper, final Supplier<T> elseGet) {
        final BlockState state = getBlockState(blockGetter, pos);
        if (state != null) return mapper.apply(state.getBlock(), state);
        return elseGet.get();
    }

    private final void mirror(final BlockGetter blockGetter, final BlockPos pos, final BiConsumer<Block, BlockState> consumer, final Runnable elseDo) {
        final BlockState state = getBlockState(blockGetter, pos);
        if (state != null) {
            consumer.accept(state.getBlock(), state);
        } else {
            elseDo.run();
        }
    }

    private final BlockState getBlockState(final BlockGetter blockGetter, final BlockPos pos) {
        final BlockEntity entity = blockGetter.getBlockEntity(pos);
        if (entity instanceof IndestructibleBlockEntity e) return e.getEffectiveBlockState();
        return null;
    }
}
