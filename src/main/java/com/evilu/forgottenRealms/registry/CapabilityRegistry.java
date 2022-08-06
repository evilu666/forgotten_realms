package com.evilu.forgottenRealms.registry;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.capability.GameProgressCapability;
import com.evilu.forgottenRealms.capability.SkillsDataCapability;
import com.evilu.forgottenRealms.capability.StaticEffectCapability;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * CapabilityRegistry
 */
@Mod.EventBusSubscriber(modid = ForgottenRealmsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityRegistry {

        public static final Capability<StaticEffectCapability> STATIC_EFFECT = CapabilityManager.get(new CapabilityToken<>() {});
        public static final ResourceLocation STATIC_EFFECT_LOCATION = new ResourceLocation(ForgottenRealmsMod.MODID, "static_effect_capability");

        public static final Capability<GameProgressCapability> GAME_PROGRESS = CapabilityManager.get(new CapabilityToken<>() {});
        public static final ResourceLocation GAME_PROGRESS_LOCATION = new ResourceLocation(ForgottenRealmsMod.MODID, "game_progress_capability");

        public static final Capability<SkillsDataCapability> SKILLS_DATA = CapabilityManager.get(new CapabilityToken<>() {});
        public static final ResourceLocation SKILLS_DATA_LOCATION = new ResourceLocation(ForgottenRealmsMod.MODID, "skills_data_capability");

        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
                event.register(StaticEffectCapability.class);
                event.register(GameProgressCapability.class);
                event.register(SkillsDataCapability.class);
        }


        
}
