package com.evilu.forgottenRealms.registry;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.effect.StaticEffect;
import com.evilu.forgottenRealms.skill.Skill;
import com.evilu.forgottenRealms.skill.StaticEffectSkill;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

/**
 * SkillRegistry
 */
public class SkillRegistry {

        private static final DeferredRegister<Skill> SKILLS = DeferredRegister.create(new ResourceLocation(ForgottenRealmsMod.MODID, "skill_registry"), ForgottenRealmsMod.MODID);

        public static final Skill MINOR_MINING_1 =  new StaticEffectSkill("minor_mining_1", StaticEffect.of(MobEffects.DIG_SPEED));
        public static final Skill MINOR_MINING_2 =  new StaticEffectSkill("minor_mining_2", StaticEffect.of(MobEffects.DIG_SPEED, 1));
        public static final Skill MINOR_MINING_3 =  new StaticEffectSkill("minor_mining_3", StaticEffect.of(MobEffects.DIG_SPEED, 2));

        public static final Skill MINING_1 =  new StaticEffectSkill("mining_1", StaticEffect.of(MobEffects.DIG_SPEED, 1));
        public static final Skill MINING_2 =  new StaticEffectSkill("mining_2", StaticEffect.of(MobEffects.DIG_SPEED, 3));
        public static final Skill MINING_3 =  new StaticEffectSkill("mining_3", StaticEffect.of(MobEffects.DIG_SPEED, 5));

        public static final Skill MAJOR_MINING_1 =  new StaticEffectSkill("major_mining_1", StaticEffect.of(MobEffects.DIG_SPEED, 2));
        public static final Skill MAJOR_MINING_2 =  new StaticEffectSkill("major_mining_2", StaticEffect.of(MobEffects.DIG_SPEED, 5));
        public static final Skill MAJOR_MINING_3 =  new StaticEffectSkill("major_mining_3", StaticEffect.of(MobEffects.DIG_SPEED, 8));


        public static final RegistryObject<Skill> REG_MINOR_MINING_1 = SKILLS.register("minor_mining_1", () -> MINOR_MINING_1);
        public static final RegistryObject<Skill> REG_MINOR_MINING_2 = SKILLS.register("minor_mining_2", () -> MINOR_MINING_2);
        public static final RegistryObject<Skill> REG_MINOR_MINING_3 = SKILLS.register("minor_mining_3", () -> MINOR_MINING_3);

        public static final RegistryObject<Skill> REG_MINING_1 = SKILLS.register("mining_1", () -> MINING_1);
        public static final RegistryObject<Skill> REG_MINING_2 = SKILLS.register("mining_2", () -> MINING_2);
        public static final RegistryObject<Skill> REG_MINING_3 = SKILLS.register("mining_3", () -> MINING_3);

        public static final RegistryObject<Skill> REG_MAJOR_MINING_1 = SKILLS.register("major_mining_1", () -> MAJOR_MINING_1);
        public static final RegistryObject<Skill> REG_MAJOR_MINING_2 = SKILLS.register("major_mining_2", () -> MAJOR_MINING_2);
        public static final RegistryObject<Skill> REG_MAJOR_MINING_3 = SKILLS.register("major_mining_3", () -> MAJOR_MINING_3);

        public static IForgeRegistry<Skill> getRegistry() {
                return RegistryRegistry.SKILL_REGISTRY.get();
        }

        public static void registerSkills() {
                SKILLS.register(FMLJavaModLoadingContext.get().getModEventBus());
        }

}
