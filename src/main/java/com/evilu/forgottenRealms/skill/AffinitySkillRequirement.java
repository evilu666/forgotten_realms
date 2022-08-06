package com.evilu.forgottenRealms.skill;

import java.util.Map;
import java.util.Set;

import com.evilu.forgottenRealms.model.Element;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.chat.TranslatableComponent;

/**
* AffinitySkillRequirement
*/
@AllArgsConstructor
public class AffinitySkillRequirement implements SkillRequirement {

	private final boolean isVisible;
	private final Element element;
	private final double affinity;
	private final boolean isMinRequirement;

	private static final int BASE_ORDER_INDEX = 1000;

	@Override
	public boolean isVisible() {
		return isVisible;
	}

	@Override
	public TranslatableComponent getDescription() {
		return new TranslatableComponent(String.format("forgottenRealms.skillRequirement.affinity.%s", isMinRequirement ? "min" : "max"), element.getDisplayName(), affinity);
	}

	@Override
	public boolean isMet(final SkillsData skillData) {
		final double providedAffinity = skillData.getPickedSkills().stream()
			.map(Skill::getProvidedAffinity)
			.map(Map::entrySet)
			.flatMap(Set::stream)
			.filter(e -> e.getKey() == element)
			.mapToDouble(Map.Entry::getValue)
			.sum();

		return isMinRequirement ? providedAffinity >= affinity : providedAffinity <= affinity;
	}

	@Override
	public int getOrder() {
		return BASE_ORDER_INDEX + element.ordinal();
	}

}
