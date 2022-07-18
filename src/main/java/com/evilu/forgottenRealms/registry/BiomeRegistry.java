package com.evilu.forgottenRealms.registry;

import com.evilu.forgottenRealms.ForgottenRealmsMod;

import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * BiomeRegistry
 */
public class BiomeRegistry {

    private static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(Registry.BIOME_REGISTRY, ForgottenRealmsMod.MODID);

    public static final RegistryObject<Biome> CENTRAL_NEXUS = BIOMES.register("central_nexus", () -> new Biome.BiomeBuilder()
            .biomeCategory(Biome.BiomeCategory.NONE)
            .downfall(0f)
            .generationSettings(BiomeGenerationSettings.EMPTY)
            .mobSpawnSettings(MobSpawnSettings.EMPTY)
            .precipitation(Biome.Precipitation.NONE)
            .temperature(0f)
            .temperatureAdjustment(Biome.TemperatureModifier.NONE)
            .specialEffects(new BiomeSpecialEffects.Builder()
                    .skyColor(0)
                    .fogColor(0)
                    .waterColor(0)
                    .waterFogColor(0)
                    .build())
            .build());


    public static void registerBiomes() {
        BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    
}
