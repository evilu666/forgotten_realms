package com.evilu.forgottenRealms.dimension.nexus;

import java.util.Collections;
import java.util.List;

import com.evilu.forgottenRealms.registry.BiomeRegistry;
import com.mojang.serialization.Codec;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

/**
 * NexusBiomeProvider
 */
public class NexusBiomeProvider extends BiomeSource {

    public static final Codec<NexusBiomeProvider> CODEC = RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).xmap(NexusBiomeProvider::new, NexusBiomeProvider::getBiomeRegistry).codec();

    private final Biome biome;
    private final Registry<Biome> biomeRegistry;
    private static final List<ResourceKey<Biome>> SPAWN = Collections.singletonList(BiomeRegistry.CENTRAL_NEXUS.getKey());

    public NexusBiomeProvider(final Registry<Biome> biomeRegistry) {
        super(SPAWN.stream().map(biomeRegistry::getHolderOrThrow));

        this.biomeRegistry = biomeRegistry;
        this.biome = biomeRegistry.get(BiomeRegistry.CENTRAL_NEXUS.getKey());
    }

    public Registry<Biome> getBiomeRegistry() {
        return biomeRegistry;
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(final long seed) {
        return this;
    }

    @Override
    public Holder<Biome> getNoiseBiome(final int x, final int y, final int z, final Climate.Sampler sampler) {
        return Holder.direct(biome);
    }
}
