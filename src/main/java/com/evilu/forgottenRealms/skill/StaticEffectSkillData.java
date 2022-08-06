package com.evilu.forgottenRealms.skill;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.evilu.forgottenRealms.model.StaticEffectCancelable;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lombok.Builder;
import lombok.Getter;
import net.minecraft.world.entity.LivingEntity;

/**
* StaticEffectSkillData
*/
@Builder
@Getter
public class StaticEffectSkillData extends SkillData<StaticEffectSkillData> {

	public static final SkillDataType<StaticEffectSkillData> TYPE = () -> StaticEffectSkillData.CODEC;
	public static final Codec<StaticEffectSkillData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				StaticEffectCancelable.CODEC.listOf().fieldOf("runningEffects").forGetter(StaticEffectSkillData::getRunningEffects)
		).apply(instance, (runningEffects) -> StaticEffectSkillData.builder()
			.runningEffects(Optional.ofNullable(runningEffects).map(ArrayList::new).orElseGet(ArrayList::new))
			.build()));

	private final List<StaticEffectCancelable> runningEffects;

	public StaticEffectSkillData(final List<StaticEffectCancelable> runningEffects) {
		super(TYPE);

		this.runningEffects = runningEffects;
	}

	public void cancelAllEffects(final LivingEntity entity) {
		runningEffects.forEach(e -> e.cancel(entity));
		runningEffects.clear();
	}

	
}
