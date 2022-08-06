package com.evilu.forgottenRealms.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.evilu.forgottenRealms.model.StaticEffectCancelable;
import com.evilu.forgottenRealms.registry.RegistryRegistry;
import com.mojang.datafixers.types.templates.Tag;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;

/**
* SkillData
*/
public class SkillsData {

	public static final Codec<SkillsData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ResourceLocation.CODEC.listOf().fieldOf("pickedSkills").forGetter(SkillsData::getPickedSkillsLocations),
				Codec.INT.fieldOf("xp").forGetter(SkillsData::getXp),
				Codec.unboundedMap(Skill.CODEC, SkillData.CODEC).fieldOf("skillData").forGetter(SkillsData::getSkillData)
	).apply(instance, SkillsData::new));

	public static final String DATA_KEY = "skillsData";

	public static SkillsData createEmpty() {
		return new SkillsData(new HashSet<>(), 0);
	}

	@Getter
	private final Set<Skill> pickedSkills;
	@Getter
	private int xp;

	@Getter(AccessLevel.PRIVATE)
	private final Map<Skill, SkillData<?>> skillData;

	private SkillsData(final List<ResourceLocation> skillLocations, final int xp, final Map<Skill, SkillData<?>> skillData) {
		this.pickedSkills = skillLocations.stream()
			.map(RegistryRegistry.SKILL_REGISTRY.get()::getValue)
			.collect(Collectors.toSet());

		this.xp = xp;
		this.skillData = new HashMap<>(skillData);
	}

	public SkillsData(final Set<Skill> pickedSkills, final int xp) {
		this.pickedSkills = pickedSkills;
		this.xp = xp;
		this.skillData = new HashMap<>();
	}

	public List<ResourceLocation> getPickedSkillsLocations() {
		return pickedSkills.stream()
			.map(Skill::getRegistryName)
			.toList();
	}

	@SuppressWarnings("unchecked")
	public <D extends SkillData<D>, T extends Skill & SkillWithData<D>> D getSkillData(final T skill) {
		return (D) skillData.computeIfAbsent(skill, s -> skill.createEmpty());
	}

	public void save(final CompoundTag tag) {
		CODEC.encodeStart(NbtOps.INSTANCE, this)
			.result()
			.ifPresent(t -> tag.put(DATA_KEY, t));
	}

	public static SkillsData load(final CompoundTag tag) {
		if (tag.contains(DATA_KEY)) {
			return CODEC.parse(NbtOps.INSTANCE, tag.get(DATA_KEY))
				.result()
				.orElseGet(SkillsData::createEmpty);
		}

		return createEmpty();
	}

}
