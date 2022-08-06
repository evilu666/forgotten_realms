package com.evilu.forgottenRealms.registry;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.worldgen.CustomizedStructureFeature;

import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * StructureFeatureRegistry
 */
public class StructureFeatureRegistry {

        private static final DeferredRegister<StructureFeature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, ForgottenRealmsMod.MODID);

        public static final RegistryObject<CustomizedStructureFeature> CUSTOM_SURFACE = FEATURES.register("custom_surface", CustomizedStructureFeature::new);
        public static final RegistryObject<CustomizedStructureFeature> CUSTOM_CAVE = FEATURES.register("custom_cave", () -> new CustomizedStructureFeature(Decoration.UNDERGROUND_DECORATION));

        public static void registerStructureFeatures() {
                FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        }

}
