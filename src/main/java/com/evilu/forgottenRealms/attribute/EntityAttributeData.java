package com.evilu.forgottenRealms.attribute;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;

/**
 * AttributeData
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
public class EntityAttributeData {

    public static final Codec<EntityAttributeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.unboundedMap(EntityAttribute.CODEC, Codec.DOUBLE).fieldOf("baseValues").forGetter(EntityAttributeData::getBaseValues),
        Codec.unboundedMap(EntityAttribute.CODEC, EntityAttributeModifier.CODEC.listOf().<Set<EntityAttributeModifier>>xmap(HashSet::new, ArrayList::new)).fieldOf("baseAttributeModifiers").forGetter(EntityAttributeData::getBaseAttributeModifiers),
        Codec.unboundedMap(EntityAttribute.CODEC, EntityAttributeModifier.CODEC.listOf().<Set<EntityAttributeModifier>>xmap(HashSet::new, ArrayList::new)).fieldOf("totalAttributeModifiers").forGetter(EntityAttributeData::getTotalAttributeModifiers)
    ).apply(instance, EntityAttributeData::new));

    public static final String DATA_KEY = "forgottenRealms:entityAttributes";

    public static EntityAttributeData read(final LivingEntity entity) {
        if (entity.getPersistentData().contains(DATA_KEY)) {
            final Optional<EntityAttributeData> maybeData = CODEC.parse(NbtOps.INSTANCE, entity.getPersistentData().get(DATA_KEY)).result();
            if (maybeData.isPresent()) return maybeData.get();
        }

        return new EntityAttributeData(new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public Tag write(final LivingEntity entity) {
        final Tag tag = CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow(false, msg -> {
            throw new IllegalStateException("Error saving entity attribute data: " + msg);
        });

        entity.getPersistentData().put(DATA_KEY, tag);

        return tag;
    }

    private final Map<EntityAttribute, Double> baseValues;

    private final Map<EntityAttribute, Set<EntityAttributeModifier>> baseAttributeModifiers;
    private final Map<EntityAttribute, Set<EntityAttributeModifier>> totalAttributeModifiers;

    public double getBaseValue(final EntityAttribute attribute) {
        return baseValues.getOrDefault(attribute, 1d);
    }

    public void setBaseValue(final EntityAttribute attribute, final double value) {
        baseValues.put(attribute, value);
    }

    public Set<EntityAttributeModifier> getBaseAttributeModifiers(final EntityAttribute attribute) {
        return baseAttributeModifiers.getOrDefault(attribute, Set.of());
    }

    public Set<EntityAttributeModifier> getTotalAttributeModifiers(final EntityAttribute attribute) {
        return totalAttributeModifiers.getOrDefault(attribute, Set.of());
    }

    public void addBaseAttributeModifier(final EntityAttribute attribute, final EntityAttributeModifier modifier) {
        if (!baseAttributeModifiers.containsKey(attribute)) {
            baseAttributeModifiers.put(attribute, new HashSet<>());
        }

        baseAttributeModifiers.get(attribute).add(modifier);
    }

    public void addTotalAttributeModifier(final EntityAttribute attribute, final EntityAttributeModifier modifier) {
        if (!baseAttributeModifiers.containsKey(attribute)) {
            baseAttributeModifiers.put(attribute, new HashSet<>());
        }

        baseAttributeModifiers.get(attribute).add(modifier);
    }

    public void removeBaseAttributeModifier(final EntityAttribute attribute, final EntityAttributeModifier modifier) {
        if (baseAttributeModifiers.containsKey(attribute)) {
            baseAttributeModifiers.get(attribute).remove(modifier);
        }
    }

    public void removeTotalAttributeModifier(final EntityAttribute attribute, final EntityAttributeModifier modifier) {
        if (totalAttributeModifiers.containsKey(attribute)) {
            totalAttributeModifiers.get(attribute).remove(modifier);
        }
    }

    public double calculateValue(final EntityAttribute attribute) {
        final double baseValue = getBaseValue(attribute);
        final Map<EntityAttributeModifier.EntityAttributeModifierOperation, Set<EntityAttributeModifier>> modsByOp = baseAttributeModifiers.getOrDefault(attribute, Set.of()).stream()
            .collect(Collectors.groupingBy(EntityAttributeModifier::getOperation, Collectors.toSet()));
        
        final double totalValue = modsByOp.keySet().stream()
                .sorted()
                .map(modsByOp::get)
                .map(EntityAttributeModifier::combine)
                .map(mod -> (Function<Double, Double>) mod::apply)
                .reduce(Function.identity(), Function::andThen)
                .apply(baseValue);

        final Map<EntityAttributeModifier.EntityAttributeModifierOperation, Set<EntityAttributeModifier>> totalModsByOp = totalAttributeModifiers.getOrDefault(attribute, Set.of()).stream()
            .collect(Collectors.groupingBy(EntityAttributeModifier::getOperation, Collectors.toSet()));

        return totalModsByOp.keySet().stream()
                .sorted()
                .map(modsByOp::get)
                .map(EntityAttributeModifier::combine)
                .map(mod -> (Function<Double, Double>) mod::apply)
                .reduce(Function.identity(), Function::andThen)
                .apply(totalValue);
    }


}
