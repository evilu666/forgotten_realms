package com.evilu.forgottenRealms.model;

import java.util.Objects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DimensionProgressData
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class DimensionProgressData {

    public static final Codec<DimensionProgressData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("canTravelToPreviousDimensions").forGetter(DimensionProgressData::isTravelToPreviousDimensionsAllowed)
        ).apply(instance, travelToPreviousDimensionsAllowed -> DimensionProgressData.builder()
            .travelToPreviousDimensionsAllowed(Objects.requireNonNullElse(travelToPreviousDimensionsAllowed, false))
            .build()));

    @Builder.Default
    private boolean travelToPreviousDimensionsAllowed = false;

    public static DimensionProgressData createEmpty() {
        return new DimensionProgressData();
    }
    
}
