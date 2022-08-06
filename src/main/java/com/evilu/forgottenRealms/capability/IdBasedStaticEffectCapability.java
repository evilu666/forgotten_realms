package com.evilu.forgottenRealms.capability;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.effect.StaticEffect;
import com.evilu.forgottenRealms.model.StaticEffectCancelable;
import com.evilu.forgottenRealms.registry.CapabilityRegistry;

import lombok.RequiredArgsConstructor;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * IdBasedPersistentEffectCapability
 */
@RequiredArgsConstructor
@Mod.EventBusSubscriber(modid = ForgottenRealmsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class IdBasedStaticEffectCapability implements StaticEffectCapability {

	public static final ICapabilityProvider createProvider(final LivingEntity entity) {
		final IdBasedStaticEffectCapability capability = new IdBasedStaticEffectCapability(entity);

		return new ICapabilitySerializable<ListTag>() {

			@Override
			public <T> LazyOptional<T> getCapability(final Capability<T> cap, final Direction side) {
				final Capability<StaticEffectCapability> effectCap = CapabilityManager.get(new CapabilityToken<>() {});
				if (cap == effectCap) {
					return LazyOptional.of(() -> capability).cast();
				}

				return LazyOptional.empty();
			}

			@Override
			public ListTag serializeNBT() {
				return StaticEffect.CODEC.listOf()
					.encodeStart(NbtOps.INSTANCE, capability.effects.values().stream().collect(Collectors.toList()))
					.result()
					.map(ListTag.class::cast)
					.orElseGet(ListTag::new);
			}

			@Override
			public void deserializeNBT(ListTag nbt) {
				StaticEffect.CODEC.listOf()
					.parse(NbtOps.INSTANCE, nbt)
					.result()
					.ifPresent(effects -> effects.forEach(effect -> capability.addEffect(effect, false)));
			}
		};
	}

	private final LivingEntity entity;
	private final Map<UUID, StaticEffect> effects = new HashMap<>();

	@Override
	public void cancel(final UUID id) {
		if (effects.containsKey(id)) {
			final StaticEffect effect = effects.get(id);

			entity.getActiveEffects()
				.stream()
				.filter(i -> i.getEffect() == effect.getEffect() && i.getAmplifier() == effect.getAmplifier())
				.findAny()
				.map(MobEffectInstance::getEffect)
				.ifPresent(entity::removeEffect);

			effects.remove(id);
		}
	}

	@Override
	public StaticEffectCancelable addEffect(final StaticEffect effect, final boolean triggerUpdate) {
		effects.put(effect.getId(), effect);
		if (triggerUpdate) applyEffects();
		return new StaticEffectCancelable(effect.getId());
	}


	@Override
	public StaticEffectCancelable addEffect(final MobEffect effect, final int amplifier, final boolean triggerUpdate) {
		return addEffect(StaticEffect.of(newId(), effect, amplifier), triggerUpdate);
	}

	@Override
	public void applyEffects() {
		effects.values()
			.stream()
			.collect(Collectors.groupingBy(StaticEffect::getEffect, Collectors.maxBy(Comparator.comparingInt(StaticEffect::getAmplifier))))
			.values()
			.stream()
			.filter(Optional::isPresent)
			.map(Optional::get)
			.map(StaticEffect::createInstance)
			.forEach(entity::addEffect);
	}

	private UUID newId() {
		UUID id;
		do {
			id = UUID.randomUUID();
		} while (effects.containsKey(id));

		return id;
	}

		@SubscribeEvent
		public static void attachLivingEntityCaps(final AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player player) {
				event.addCapability(CapabilityRegistry.STATIC_EFFECT_LOCATION, IdBasedStaticEffectCapability.createProvider(player));
			}
		}

    
}
