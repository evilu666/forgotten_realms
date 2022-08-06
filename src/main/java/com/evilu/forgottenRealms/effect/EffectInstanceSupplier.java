package com.evilu.forgottenRealms.effect;

import java.util.function.Supplier;

import net.minecraft.world.effect.MobEffectInstance;

/**
 * EffectInstanceProvider
 */
@FunctionalInterface
public interface EffectInstanceSupplier<I extends MobEffectInstance> extends Supplier<I> {

    default I createInstance() {
        return get();
    }
}
