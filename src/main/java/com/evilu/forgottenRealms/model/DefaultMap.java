package com.evilu.forgottenRealms.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * DefaultMap
 */
public class DefaultMap<K, V> extends HashMap<K, V> {

    private final V defaultValue;
    private final Supplier<V> defaultValueSupplier;

    public DefaultMap(final V defaultValue) {
        this.defaultValue = defaultValue;
        this.defaultValueSupplier = null;
    }

    public DefaultMap(final Supplier<V> defaultValueSupplier) {
        this.defaultValue = null;
        this.defaultValueSupplier = defaultValueSupplier;
    }

    public static <K, V> DefaultMap<K, V> withDefaultValue(final Map<K, V> map, final V defaultValue) {
        final DefaultMap<K, V> m = new DefaultMap<>(defaultValue);
        m.putAll(map);
        return m;
    }

    public static <K, V> DefaultMap<K, V> withDefaultSupplier(final Map<K, V> map, final Supplier<V> defaultValueSupplier) {
        final DefaultMap<K, V> m = new DefaultMap<>(defaultValueSupplier);
        m.putAll(map);
        return m;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(final Object key) {
        return super.computeIfAbsent((K) key, k -> getDefaultValue());
    }

    private V getDefaultValue() {
        if (defaultValue != null) {
            return defaultValue;
        }

        return defaultValueSupplier.get();
    }

    
}
