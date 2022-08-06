package com.evilu.forgottenRealms.model;

import java.util.UUID;

import com.evilu.forgottenRealms.capability.StaticEffectCapability;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

/**
 * StaticEffectCancelable
 */
@AllArgsConstructor
@Getter
public class StaticEffectCancelable implements SourceCancelable<LivingEntity> {

    public static final Codec<StaticEffectCancelable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.xmap(UUID::fromString, UUID::toString).fieldOf("id").forGetter(StaticEffectCancelable::getId)
            ).apply(instance, StaticEffectCancelable::new));

    private final UUID id;

    private static final Capability<StaticEffectCapability> STATIC_EFFECT_CAP = CapabilityManager.get(new CapabilityToken<>() {});

	@Override
	public void cancel(final LivingEntity source) {
      source.getCapability(STATIC_EFFECT_CAP).ifPresent(cap -> cap.cancel(id));
	}

    
}
