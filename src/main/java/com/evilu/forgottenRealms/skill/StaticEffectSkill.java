package com.evilu.forgottenRealms.skill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.evilu.forgottenRealms.effect.StaticEffect;
import com.evilu.forgottenRealms.registry.CapabilityRegistry;

import net.minecraft.world.entity.LivingEntity;

/**
* StaticEffectSkill
*/
public class StaticEffectSkill extends Skill implements SkillWithData<StaticEffectSkillData> {

	private final List<StaticEffect> effects;

	public StaticEffectSkill(String id, final List<StaticEffect> effects) {
		super(id);

		this.effects = effects;
	}

	public StaticEffectSkill(String id, final StaticEffect... effects) {
		super(id);

		this.effects = Arrays.asList(effects);
	}

	@Override
	public void onEnable(final LivingEntity entity, final SkillsData skillData) {
		final StaticEffectSkillData data = skillData.getSkillData(this);
		entity.getCapability(CapabilityRegistry.STATIC_EFFECT).ifPresent(cap -> effects.stream()
				.map(cap::addEffect)
				.forEach(data.getRunningEffects()::add));
	}

	@Override
	public void onDisable(final LivingEntity entity, final SkillsData skillData) {
		final StaticEffectSkillData data = skillData.getSkillData(this);
		data.cancelAllEffects(entity);
	}

	@Override
	public void onEffectTick(final LivingEntity entity, final SkillsData skillData) {}

	@Override
	public StaticEffectSkillData createEmpty() {
		return new StaticEffectSkillData(new ArrayList<>());
	}

	@Override
	public Class<StaticEffectSkillData> getDataClass() {
		return StaticEffectSkillData.class;
	}

	
}
