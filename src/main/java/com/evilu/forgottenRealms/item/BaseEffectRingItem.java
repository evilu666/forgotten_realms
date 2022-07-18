package com.evilu.forgottenRealms.item;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class BaseEffectRingItem extends Item implements ICurioItem {

  public static int STATIC_EFFECT_DURATION = 10;

  private final Map<MobEffect, Function<MobEffectInstance, MobEffectInstance>> staticEffects;
  private final Multimap<Attribute, AttributeModifier> staticAttributeModifiers;

  public BaseEffectRingItem(final Properties properties, final Map<MobEffect, Function<MobEffectInstance, MobEffectInstance>> staticEffects) {
    this(properties, staticEffects, ImmutableMultimap.of());
  }

  public BaseEffectRingItem(final Properties properties, final Map<MobEffect, Function<MobEffectInstance, MobEffectInstance>> staticEffects, final Multimap<Attribute, AttributeModifier> staticAttributeModifiers) {
      super(properties);
      this.staticEffects = staticEffects;
      this.staticAttributeModifiers = staticAttributeModifiers;
  }

	public BaseEffectRingItem(final Properties properties, final List<MobEffect> staticEffects) {
    this(properties, staticEffects, ImmutableMultimap.of());
  }

	public BaseEffectRingItem(final Properties properties, final List<MobEffect> staticEffects, final Multimap<Attribute, AttributeModifier> staticAttributeModifiers) {
      this(
          properties,
          staticEffects.stream().collect(Collectors.toMap(Function.identity(), e -> Function.identity())),
          staticAttributeModifiers
      );
	}

  @Override
  public Multimap<Attribute, AttributeModifier> getAttributeModifiers(final SlotContext slotContext, final UUID uuid, final ItemStack stack) {
    return staticAttributeModifiers;
  }

  @Override
  public void onEquip(final SlotContext slotContext, final ItemStack prevStack, final ItemStack stack) {
      staticEffects.entrySet()
          .stream()
          .map(e -> Pair.of(new MobEffectInstance(e.getKey(), Integer.MAX_VALUE), e.getValue()))
          .map(p -> p.getRight().apply(p.getLeft()))
          .forEach(slotContext.entity()::addEffect);
  }

  @Override
  public void onUnequip(final SlotContext slotContext, final ItemStack newStack, final ItemStack stack) {
      staticEffects.keySet()
          .forEach(slotContext.entity()::removeEffect);
  }

  @Override
  public boolean canEquip(final SlotContext slotContext, final ItemStack stack) {
    return SlotTypePreset.RING.getIdentifier().equals(slotContext.identifier());
  }
}
