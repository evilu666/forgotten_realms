package com.evilu.forgottenRealms.skill;

import com.mojang.serialization.Codec;

import lombok.AllArgsConstructor;

/**
* SkillData
*/
@AllArgsConstructor
public abstract class SkillData<T extends SkillData<T>> {

	public static final Codec<SkillData<?>> CODEC = SkillDataType.CODEC.dispatch("type", SkillData::getType, SkillDataType::getCodec);

	private final SkillDataType<T> type;

	public SkillDataType<T> getType() {
		return type;
	}
	
}
