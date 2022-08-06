package com.evilu.forgottenRealms.skill;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.model.Element;
import com.evilu.forgottenRealms.model.SkillTree;
import com.evilu.forgottenRealms.registry.GuiRegistry;
import com.evilu.forgottenRealms.registry.SkillRegistry;
import com.mojang.serialization.Codec;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Skill
 */
@RequiredArgsConstructor
@Getter
public abstract class Skill implements IForgeRegistryEntry<Skill> {

	public static final Codec<Skill> CODEC = ResourceLocation.CODEC.xmap(id -> SkillRegistry.getRegistry().getValue(id), skill -> SkillRegistry.getRegistry().getKey(skill));

	public static final Skill ROOT = new Skill("root", new TranslatableComponent("forgottenRealms.skillTree.root.name"), new TranslatableComponent("forgottenRealms.skillTree.root.description"), GuiRegistry.SKILL_TREE_ROOT_IMAGE) {
		public List<SkillRequirement> getRequirements() {
			return List.of();
		}

		public void onEnable(LivingEntity entity, SkillsData skillData) {}
		public void onDisable(LivingEntity entity, SkillsData skillData) {}
		public void onEffectTick(LivingEntity entity, SkillsData skillData) {}
	};

	protected Skill(final String id) {
		this.id = id;

		final String keyBase = "forgottenRealms.skill." + id;
		name = new TranslatableComponent(keyBase + ".name");
		description = new TranslatableComponent(keyBase + ".description");
		icon = new ResourceLocation(ForgottenRealmsMod.MODID, "textures/skill/" + id + ".png");
	}

	private final String id;
	private final TranslatableComponent name;
	private final TranslatableComponent description;
	private final ResourceLocation icon;

	private ResourceLocation registryName;

	public Map<Element, Double> getProvidedAffinity() {
		return Map.of();
	}

	public List<SkillRequirement> getRequirements() {
		return List.of(SkillRequirement.PICKED_PREVIOUS_SKILL);
	}

	public List<SkillRequirement> getVisibilityRequirements() {
		return List.of(SkillRequirement.UNLOCKED_PREVIOUS_SKILL);
	}

	public boolean areSkillRequirementsMet(final SkillTree skillTree, final SkillsData skillData, final Set<Skill> unlockedSkillsCache) {
		return getRequirements().stream().allMatch(req -> req.isMet(this, skillTree, skillData, unlockedSkillsCache));
	}

	public boolean areVisibilityRequirementsMet(final SkillTree skillTree, final SkillsData skillData, final Set<Skill> unlockedSkillsCache) {
		return getVisibilityRequirements().stream().allMatch(req -> req.isMet(this, skillTree, skillData, unlockedSkillsCache));
	}

	@Override
	public Skill setRegistryName(final ResourceLocation name) {
		this.registryName = name;
		return this;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return registryName;
	}

	@Override
	public Class<Skill> getRegistryType() {
		return Skill.class;
	}

	public Codec<? extends SkillsData> getSkillDataCodec() {
		return SkillsData.CODEC;
	}

	public abstract void onEnable(final LivingEntity entity, final SkillsData skillData);
	public abstract void onDisable(final LivingEntity entity, final SkillsData skillData);
	public abstract void onEffectTick(final LivingEntity entity, final SkillsData skillData);
}
