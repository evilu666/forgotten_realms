package com.evilu.forgottenRealms.skill;

import java.util.Set;

import com.evilu.forgottenRealms.capability.ItemSkillsDataCapability;
import com.evilu.forgottenRealms.capability.SkillsDataCapability;
import com.evilu.forgottenRealms.model.Reference;
import com.evilu.forgottenRealms.model.SkillTree;
import com.evilu.forgottenRealms.registry.CapabilityRegistry;

import net.minecraft.world.item.ItemStack;

/**
* SkilledItem
*/
public interface SkilledItem {

	public SkillTree getSkillTree();

	default void tryPickSkill(final Skill skill, final ItemStack stack) {
		final SkillTree tree = getSkillTree();
		final SkillsData data = getSkillsData(stack).get();
		if (tree.findRecursive(skill.getId()).isPresent() && skill.areVisibilityRequirementsMet(tree, data, Set.of()) && skill.areSkillRequirementsMet(tree, data, Set.of())) {
			data.getPickedSkills().add(skill);
		}
	}

	default Reference<SkillsData> getSkillsData(final ItemStack stack) {
		return stack.getCapability(CapabilityRegistry.SKILLS_DATA)
			.resolve()
			.map(ItemSkillsDataCapability.class::cast)
			.map(ItemSkillsDataCapability::getDataReference)
			.orElseThrow();
	}
	
}
