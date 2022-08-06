package com.evilu.forgottenRealms.skill;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.mojang.serialization.Codec;

/**
* SkillDataType
*/
public interface SkillDataType<T extends SkillData<T>> {

	public Codec<T> getCodec();

	public static final Map<String, SkillDataType<?>> TYPES = Collections.unmodifiableMap(Map.of(
		"forgotten_realms:static_effect_data", StaticEffectSkillData.TYPE
	));

	public static String getId(final SkillDataType<?> type) {
		return TYPES.entrySet()
			.stream()
			.filter(e -> e.getValue() == type)
			.findAny()
			.map(Map.Entry::getKey)
			.orElseThrow();
	}

	public static final Codec<SkillDataType<?>> CODEC = Codec.STRING.xmap(TYPES::get, SkillDataType::getId);
}
