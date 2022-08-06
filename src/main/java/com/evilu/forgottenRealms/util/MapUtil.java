package com.evilu.forgottenRealms.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;

/**
 * MapUtil
 */
public interface MapUtil {

    public static <K, V> Collector<Map<K, V>, Map<K, V>, Map<K, V>> collectMerging(final BinaryOperator<V> duplicateHandler) {
        return Collector.of(HashMap::new, (a, b) -> b.forEach((key, value) -> {
            if (a.containsKey(key)) {
                a.put(key, duplicateHandler.apply(a.get(key), value));
            } else {
                a.put(key, value);
            }
        }) , (a, b) -> {
            b.forEach((key, value) -> {
                if (a.containsKey(key)) {
                    a.put(key, duplicateHandler.apply(a.get(key), value));
                } else {
                    a.put(key, value);
                }
            });

            return a;
        });
    }

    
}
