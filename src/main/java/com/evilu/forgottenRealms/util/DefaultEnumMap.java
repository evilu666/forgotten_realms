package com.evilu.forgottenRealms.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * EnumMap
 */
public class DefaultEnumMap<K extends Enum<K>, V> extends HashMap<K, V> {

    public DefaultEnumMap(final Class<K> keyClass, final Supplier<V> defaultSupplier) {
        super(keyClass.getEnumConstants().length);

        Arrays.stream(keyClass.getEnumConstants())
            .forEach(e -> DefaultEnumMap.this.put(e, defaultSupplier.get()));
    }

    public DefaultEnumMap(final Class<K> keyClass, final V defaultValue) {
        super(keyClass.getEnumConstants().length);

        Arrays.stream(keyClass.getEnumConstants())
            .forEach(e -> DefaultEnumMap.this.put(e, defaultValue));
    }
}
