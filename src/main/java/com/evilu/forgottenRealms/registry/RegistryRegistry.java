package com.evilu.forgottenRealms.registry;

import java.util.function.Supplier;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.skill.Skill;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * SkillRegistry
 */
@EventBusSubscriber(modid = ForgottenRealmsMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegistryRegistry {

		private static final DeferredRegister<Skill> SKILL_REGISTRY_REGISTER = DeferredRegister.create(new ResourceLocation(ForgottenRealmsMod.MODID, "skill_registry"), ForgottenRealmsMod.MODID);
    
    public static final Supplier<IForgeRegistry<Skill>> SKILL_REGISTRY = SKILL_REGISTRY_REGISTER.makeRegistry(Skill.class, RegistryBuilder::new);

    public static void registerRegistries() {
        SKILL_REGISTRY_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
