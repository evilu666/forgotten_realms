package com.evilu.forgottenRealms.capability;

import java.util.Set;
import java.util.UUID;

import com.evilu.forgottenRealms.effect.StaticEffect;
import com.evilu.forgottenRealms.model.Cancelable;
import com.evilu.forgottenRealms.model.StaticEffectCancelable;
import com.mojang.serialization.Codec;

import net.minecraft.world.effect.MobEffect;


/**
 * PersistentEffectCapability
 */
public interface StaticEffectCapability {

    public StaticEffectCancelable addEffect(final StaticEffect effect, final boolean triggerUpdate);

    default StaticEffectCancelable addEffect(final StaticEffect effect) {
        return addEffect(effect, true);
    }


    public StaticEffectCancelable addEffect(final MobEffect effect, final int amplifier, final boolean triggerUpdate);

    default StaticEffectCancelable addEffect(final MobEffect effect, final int amplifier) {
        return addEffect(effect, amplifier, true);
    }

    default StaticEffectCancelable addEffect(final MobEffect effect) {
        return addEffect(effect, 0);
    }

    public void cancel(final UUID id);

    public void applyEffects();
}
