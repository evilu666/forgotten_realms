package com.evilu.forgottenRealms.registry;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.dimension.nexus.NexusBiomeProvider;
import com.mojang.serialization.Codec;

import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * BiomeSourceRegistry
 */
public class BiomeSourceRegistry {

    private static final DeferredRegister<Codec<? extends BiomeSource>> BIOME_SOURCES = DeferredRegister.create(Registry.BIOME_SOURCE_REGISTRY, ForgottenRealmsMod.MODID);

    public static final RegistryObject<Codec<NexusBiomeProvider>> NEXUS = BIOME_SOURCES.register("nexus", () -> NexusBiomeProvider.CODEC);

    public static void registerBiomeSources() {
        BIOME_SOURCES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
