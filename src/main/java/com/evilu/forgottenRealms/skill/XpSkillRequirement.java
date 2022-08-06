package com.evilu.forgottenRealms.skill;

import lombok.AllArgsConstructor;
import net.minecraft.network.chat.TranslatableComponent;

/**
* XpSkillRequirement
*/
@AllArgsConstructor
public class XpSkillRequirement implements SkillRequirement {

	private final boolean isVisible;
	private final long xpRequirement;

	@Override
	public boolean isVisible() {
		return isVisible;
	}

	@Override
	public TranslatableComponent getDescription() {
		return new TranslatableComponent("forgottenRealms.skillRequirement.xp", xpRequirement);
	}

	@Override
	public boolean isMet(final SkillsData skillData) {
		return skillData.getXp() >= xpRequirement;
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
}
