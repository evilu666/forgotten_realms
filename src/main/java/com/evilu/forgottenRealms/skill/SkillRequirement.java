package com.evilu.forgottenRealms.skill;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import com.evilu.forgottenRealms.model.Element;
import com.evilu.forgottenRealms.model.SkillTree;
import com.evilu.forgottenRealms.model.Tree;

import net.minecraft.network.chat.TranslatableComponent;

/**
* SkillRequirement
*/
public interface SkillRequirement {

	public boolean isVisible();

	public TranslatableComponent getDescription();

	public boolean isMet(final SkillsData skillData);

	default boolean isMet(final Skill skill, final SkillTree skillTree, final SkillsData skillData, final Set<Skill> unlockedSkillsCache) {
		return isMet(skillData);
	}

	default boolean apply(final SkillsData skillData) {
		return false;
	}

	default int getOrder() {
		return 0;
	}

	public static SkillRequirement minAffinity(final Element element, final double minAffinity) {
		return SkillRequirement.minAffinity(element, minAffinity, true);
	}

	public static SkillRequirement minAffinity(final Element element, final double minAffinity, final boolean isVisible) {
		return new AffinitySkillRequirement(isVisible, element, minAffinity, true);
	}

	public static SkillRequirement maxAffinity(final Element element, final double maxAffinity) {
		return SkillRequirement.maxAffinity(element, maxAffinity, true);
	}

	public static SkillRequirement maxAffinity(final Element element, final double maxAffinity, final boolean isVisible) {
		return new AffinitySkillRequirement(isVisible, element, maxAffinity, false);
	}

	public static SkillRequirement pickedSkils(final Skill... skills) {
		return SkillRequirement.pickedSkils(true, skills);
	}

	public static SkillRequirement pickedSkils(final boolean isVisible, final Skill... skills) {
		return new PickedSkillsRequirement(isVisible, Set.of(skills));
	}

	public static SkillRequirement pickedPreviousSkill() {
		return PICKED_PREVIOUS_SKILL;
	}

	public static SkillRequirement unlockedPreviousSkill() {
		return UNLOCKED_PREVIOUS_SKILL;
	}

	public static SkillRequirement xp(final long requiredXP) {
		return SkillRequirement.xp(requiredXP, true);
	}

	public static SkillRequirement xp(final long requiredXP, final boolean isVisible) {
		return new XpSkillRequirement(isVisible, requiredXP);
	}

	public static final SkillRequirement PICKED_PREVIOUS_SKILL = new SkillRequirement() {

		@Override
		public boolean isVisible() {
			return false;
		}

		@Override
		public TranslatableComponent getDescription() {
			return null;
		}

		@Override
		public boolean isMet(final SkillsData skillData) {
			return false;
		}

		@Override
		public boolean isMet(final Skill skill, final SkillTree skillTree, final SkillsData skillData, final Set<Skill> unlockedSkillsCache) {
			return skillTree.findRecursive(skill.getId())
				.map(Tree::getParent)
				.map(Tree::getValue)
				.map(parent -> Skill.ROOT == parent || skillData.getPickedSkills().contains(parent))
				.orElse(false);
		}
		
	};

	public static final SkillRequirement UNLOCKED_PREVIOUS_SKILL = new SkillRequirement() {

		@Override
		public boolean isVisible() {
			return false;
		}

		@Override
		public TranslatableComponent getDescription() {
			return null;
		}

		@Override
		public boolean isMet(final SkillsData skillData) {
			return false;
		}

		@Override
		public boolean isMet(final Skill skill, final SkillTree skillTree, final SkillsData skillData, final Set<Skill> unlockedSkillsCache) {
			return skillTree.findRecursive(skill.getId())
				.map(Tree::getParent)
				.filter(Objects::nonNull)
				.map(Tree::getValue)
				.map(parentSkill -> Skill.ROOT == parentSkill || unlockedSkillsCache.contains(parentSkill) || parentSkill.areSkillRequirementsMet(skillTree, skillData, unlockedSkillsCache))
				.orElse(false);
		}
		
	};






}
