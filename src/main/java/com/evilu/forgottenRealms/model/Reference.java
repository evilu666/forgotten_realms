package com.evilu.forgottenRealms.model;

import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * Reference
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reference<T> {

    private T value;

    public static <T> Reference<T> of(final T value) {
        return new Reference<>(value);
    }

    public static <T> Reference<T> fromSupplier(final Supplier<T> supplier) {
        return new Reference<>(null) {
            public T get() {
                return supplier.get();
            }
        };
    }


    public T get() {
        return value;
    }

}
