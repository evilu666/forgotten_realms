package com.evilu.forgottenRealms.dimension.nexus;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.structure.IslandStructures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blending.Blender;

/**
 * NexusWorldProvider
 */
public class NexusDimensionChunkGenerator extends ChunkGenerator {

    public static final Codec<NexusDimensionChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> {
        return commonCodec(instance).apply(instance, instance.stable(structureSet -> new NexusDimensionChunkGenerator()));
    });

    public static int SPAWN_HEIGHT = 64;

    private static final BlockPos START_ISLAND_POS = new BlockPos(-11, 50, -11);
    private static final Set<ChunkPos> START_ISLAND_CHUNKS = IslandStructures.START_ISLAND.getChunks(START_ISLAND_POS);

    public NexusDimensionChunkGenerator() {
        super(null, Optional.empty(), new FixedBiomeSource(Holder.direct(BuiltinRegistries.BIOME.get(new ResourceLocation(ForgottenRealmsMod.MODID, "central_nexus")))));
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long p_64180_) {
        return this;
    }

    @Override
    public void buildSurface(WorldGenRegion region, StructureFeatureManager featureManager, ChunkAccess chunkAccess) {
    }

    @Override
    public int getSpawnHeight(LevelHeightAccessor levelHeightAccessor) {
        return  SPAWN_HEIGHT;
    }

    @Override
    protected Holder<Biome> adjustBiome(final Holder<Biome> biome) {
        return biome;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(final Executor executor, final Blender blender, final StructureFeatureManager structureFeatureManager, final ChunkAccess chunkAccess) {
        final ChunkPos pos = chunkAccess.getPos();
        if (START_ISLAND_CHUNKS.contains(pos)) {
            IslandStructures.START_ISLAND.generate(chunkAccess, START_ISLAND_POS);
        }

        return CompletableFuture.completedFuture(chunkAccess);
    }

    @Override
    public int getBaseHeight(int p_158274_, int p_158275_, final Heightmap.Types p_158276_, LevelHeightAccessor p_158277_) {
        return 0;
    }

    @Override
    public NoiseColumn getBaseColumn(int p_158270_, int p_158271_, LevelHeightAccessor p_158272_) {
        return new NoiseColumn(0, new BlockState[0]);
    }

    @Override
    public void addDebugScreenInfo(final List<String> strings, final BlockPos blockPos) {
    }

    @Override
    public Climate.Sampler climateSampler() {
        return Climate.empty();
    }

    @Override
    public void applyCarvers(final WorldGenRegion region, final long p_188548_, final BiomeManager biomeManager, final StructureFeatureManager structureFeatureManager, final ChunkAccess chunkAccess, final GenerationStep.Carving carving) {
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion p_188545_) {
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getGenDepth() {
        return 128;
    }

    @Override
    public int getSeaLevel() {
        return -128;
    }
}
