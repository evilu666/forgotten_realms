package com.evilu.forgottenRealms.registry;

import com.evilu.forgottenRealms.ForgottenRealmsMod;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/**
 * DimensionRegistry
 */
public class DimensionRegistry {

    public static final ResourceKey<Level> NEXUS = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(ForgottenRealmsMod.MODID, "nexus"));
    
}
