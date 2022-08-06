package com.evilu.forgottenRealms.attribute;

import com.mojang.serialization.Codec;

/**
 * Attribute
 */
public enum EntityAttribute {

    DEXTERITY,
    STRENGTH,
    CONSTITUTION,
    INTELLIGENCE,
    WISDOM,
    CHARISMA;

    public static final Codec<EntityAttribute> CODEC = Codec.STRING.xmap(EntityAttribute::valueOf, EntityAttribute::name);
}
