package com.evilu.forgottenRealms.registry;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.dimension.nexus.NexusDimensionChunkGenerator;
import com.mojang.serialization.Codec;

import net.minecraft.core.Registry;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * ChunkGeneratorRegistry
 */
public class ChunkGeneratorRegistry {

    private static final DeferredRegister<Codec<? extends ChunkGenerator>> GENERATORS = DeferredRegister.create(Registry.CHUNK_GENERATOR_REGISTRY, ForgottenRealmsMod.MODID);

    public static final RegistryObject<Codec<NexusDimensionChunkGenerator>> NEXUS = GENERATORS.register("nexus", () -> NexusDimensionChunkGenerator.CODEC);

    public static void registerGenerators() {
        GENERATORS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    
}
