package com.evilu.forgottenRealms.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.mojang.serialization.Codec;

/**
 * Codecs
 */
public interface Codecs {

    public static <T> Codec<Set<T>> set(final Codec<T> valueCodec) {
        return valueCodec.listOf().<Set<T>>xmap(HashSet::new, ArrayList::new);
    }

    
}
