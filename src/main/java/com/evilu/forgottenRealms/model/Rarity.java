package com.evilu.forgottenRealms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Rarity
 */
@AllArgsConstructor
@Getter
public enum Rarity {

    COMMON,
    UNCOMMON,
    RARE,
    EPIC,
    LEGENDARY,
    MYTHIC,
    GODLY,
    UNIQUE
    ;

    public int getThreshold() {
        if (this == COMMON) {
            return Integer.MIN_VALUE;
        }

        return (int) Math.pow(2, 9 + this.ordinal());
    }

    
}
