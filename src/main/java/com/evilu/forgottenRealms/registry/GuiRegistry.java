package com.evilu.forgottenRealms.registry;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.skill.Skill;

import net.minecraft.resources.ResourceLocation;

/**
 * GuiRegistry
 */
public class GuiRegistry {

        public static ResourceLocation SKILL_TREE_ROOT_IMAGE = new ResourceLocation(ForgottenRealmsMod.MODID, "textures/skill/test.png");

        public static ResourceLocation skillImage(final Skill skill) {
                return new ResourceLocation(ForgottenRealmsMod.MODID, String.format("textures/skill/%s.png", skill.getId()));
        }

        
}
