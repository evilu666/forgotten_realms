package com.evilu.forgottenRealms.dimension.nexus;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.evilu.forgottenRealms.structure.BridgeStructures;
import com.evilu.forgottenRealms.structure.IslandStructures;
import com.evilu.forgottenRealms.structure.PlaceableStructure;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.StructureSet;

/**
 * NexusWorldProvider
 */
public class NexusDimensionChunkGenerator extends ChunkGenerator {


    public static final Codec<NexusDimensionChunkGenerator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            RegistryOps.retrieveRegistry(Registry.STRUCTURE_SET_REGISTRY)
                .forGetter(NexusDimensionChunkGenerator::getStructureSetRegistry),
            RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY)
                .forGetter(NexusDimensionChunkGenerator::getBiomeRegistry)
        )
        .apply(instance, NexusDimensionChunkGenerator::new)
    );

    public static int SPAWN_HEIGHT = 64;

    private static final List<PlaceableStructure<?>> STRUCTURES = List.of(
        IslandStructures.START_ISLAND.placeAt(new BlockPos(-11, 50, -11)),
        IslandStructures.OVERWORLD_ISLAND.placeAt(new BlockPos(-11, 50, 85)),
        BridgeStructures.NORTH_SOUTH.placeAt(new BlockPos(-2, 59, 7))
    );

    private final Registry<Biome> biomeRegistry;

    public NexusDimensionChunkGenerator(final Registry<StructureSet> structureSet, final Registry<Biome> biomeRegistry) {
        super(structureSet, Optional.empty(), new NexusBiomeProvider(biomeRegistry));
        this.biomeRegistry = biomeRegistry;
    }

    public Registry<StructureSet> getStructureSetRegistry() {
        return structureSets;
    }

    public Registry<Biome> getBiomeRegistry() {
        return biomeRegistry;
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return this;
    }

    @Override
    public void buildSurface(WorldGenRegion region, StructureFeatureManager featureManager, ChunkAccess chunkAccess) {
    }

    @Override
    public int getSpawnHeight(LevelHeightAccessor levelHeightAccessor) {
        return SPAWN_HEIGHT;
    }

    @Override
    protected Holder<Biome> adjustBiome(final Holder<Biome> biome) {
        return biome;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(final Executor executor, final Blender blender, final StructureFeatureManager structureFeatureManager, final ChunkAccess chunkAccess) {
        return CompletableFuture.supplyAsync(() -> {
            for (final PlaceableStructure<?> struct : STRUCTURES) {
                struct.generate(chunkAccess);
            }

            return chunkAccess;
        }, executor);
    }

    @Override
    public int getBaseHeight(final int x, final int z, final Heightmap.Types heightMapType, final LevelHeightAccessor levelHeightAccessor) {
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
