package com.evilu.forgottenRealms.effect;

import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

/**
 * StaticEffect
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class StaticEffect implements EffectInstanceSupplier<MobEffectInstance> {

    public static final Codec<StaticEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.xmap(UUID::fromString, UUID::toString).fieldOf("id").forGetter(StaticEffect::getId),
            Codec.INT.fieldOf("effectId").forGetter(se -> MobEffect.getId(se.getEffect())),
            Codec.INT.fieldOf("amplifier").forGetter(StaticEffect::getAmplifier)
        ).apply(instance, (id, effectId, amplifier) -> new StaticEffect(id, MobEffect.byId(effectId), amplifier)));

    private final UUID id;
    private final MobEffect effect;
    private final int amplifier;

    public static StaticEffect of(final UUID id, final MobEffect effect, final int amplifier) {
        return new StaticEffect(id, effect, amplifier);
    }

    public static StaticEffect of(final UUID id, final MobEffect effect) {
        return of(id, effect, 0);
    }

    public static StaticEffect of(final MobEffect effect, final int amplifier) {
        return of(UUID.randomUUID(), effect, amplifier);
    }

    public static StaticEffect of(final MobEffect effect) {
        return of(UUID.randomUUID(), effect, 0);
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StaticEffect) {
            final StaticEffect other = (StaticEffect) obj;
            return id.equals(other.id) && effect.equals(other.effect) && amplifier == other.amplifier;
        }

        return false;
    }

	@Override
	public MobEffectInstance get() {
      return new MobEffectInstance(effect, Integer.MAX_VALUE, amplifier);
	}
}
