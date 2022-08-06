package com.evilu.forgottenRealms.skill;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import net.minecraft.network.chat.TranslatableComponent;

/**
* PickedSkillsRequirement
*/
@RequiredArgsConstructor
public class PickedSkillsRequirement implements SkillRequirement {

	private final boolean isVisible;
	private final Set<Skill> skills;

	@Override
	public boolean isVisible() {
		return isVisible;
	}

	@Override
	public TranslatableComponent getDescription() {
		return new TranslatableComponent("forgottenRealms.skillRequirement.pickedSkills", skills.stream()
				.map(Skill::getName)
				.map(TranslatableComponent::toString)
				.collect(Collectors.joining(", ")));
	}

	@Override
	public boolean isMet(final SkillsData skillData) {
		return skills.stream()
			.allMatch(skillData.getPickedSkills()::contains);
	}

	@Override
	public int getOrder() {
		return 100;
	}
}
