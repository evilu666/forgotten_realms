package com.evilu.forgottenRealms.model;

import java.util.List;

import net.minecraft.network.chat.TranslatableComponent;

/**
 * Element
 */
public enum Element {

    FIRE(0),
    EARTH(2),
    WATER(4),
    AIR(6),
    MAGAMA(1, FIRE, EARTH),
    WOOD(3, EARTH, WATER),
    ICE(5, WATER, AIR),
    LIGHTNING(7, AIR, FIRE),
    ELEMENTAL_VOLATILITY(FIRE.circlePos, LIGHTNING, MAGAMA),
    LIFE_CYCLE(EARTH.circlePos, MAGAMA, WOOD),
    SURFACE_PROPERTIES(WATER.circlePos, WOOD, ICE),
    ELECTRICAL_CURRENT(AIR.circlePos, ICE, LIGHTNING),
    NATURAL_DESTRUCTION(MAGAMA.circlePos, ELEMENTAL_VOLATILITY, LIFE_CYCLE),
    NATURAL_CREATION(WOOD.circlePos, LIFE_CYCLE, SURFACE_PROPERTIES),
    ELECTRICAL_CONDUCTIVITY(ICE.circlePos, SURFACE_PROPERTIES, ELECTRICAL_CURRENT),
    ATMOSPHERIC_CONTROL(LIGHTNING.circlePos, ELECTRICAL_CURRENT, ELEMENTAL_VOLATILITY)
    ;

    public static double getAffinity(final Element first, final Element second) {
        final int c1 = Math.min(first.circlePos, second.circlePos);
        final int c2 = Math.max(first.circlePos, second.circlePos);

        final double dist;
        if (c2 - c1 > 4) {
            dist = (c2 - 8) - c1;
        } else dist = c2 - c1;


        return 1d - (Math.abs(dist) / 4d);
    }

    public double getAffinity(final Element other) {
        return Element.getAffinity(this, other);
    }

    private final int circlePos;
    private final List<Element> sourceElements;

    private Element(final int circlePos, final Element... elements) {
        this.circlePos = circlePos;
        sourceElements = List.of(elements);
    }

    public List<Element> getSourceElements() {
        return sourceElements;
    }

    public TranslatableComponent getDisplayName() {
        return new TranslatableComponent(String.format("forgottenRealms.element.%s.name", name()));
    }

    public TranslatableComponent getDescription() {
        return new TranslatableComponent(String.format("forgottenRealms.element.%s.description", name()));
    }

    
}
