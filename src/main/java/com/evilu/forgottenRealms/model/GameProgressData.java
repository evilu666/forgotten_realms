package com.evilu.forgottenRealms.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.evilu.forgottenRealms.registry.DimensionRegistry;
import com.evilu.forgottenRealms.util.Codecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;

/**
 * GameProgressData
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
public class GameProgressData {

    public static final Codec<GameProgressData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.unboundedMap(ResourceLocation.CODEC, DimensionProgressData.CODEC).fieldOf("dimensionProgres").forGetter(GameProgressData::getDimensionProgress),
                Codecs.set(ResourceLocation.CODEC).fieldOf("finishedDimensions").forGetter(GameProgressData::getFinishedDimensions),
                ResourceLocation.CODEC.fieldOf("currentDimension").forGetter(GameProgressData::getCurrentDimension)
            ).apply(instance, (dimensionProgress, finishedDimensions, currentDimension) -> GameProgressData.builder()
                .dimensionProgress(Optional.ofNullable(dimensionProgress).map(p -> DefaultMap.withDefaultSupplier(p, DimensionProgressData::createEmpty)).orElseGet(() -> new DefaultMap<>(DimensionProgressData::createEmpty)))
                .finishedDimensions(Objects.requireNonNullElseGet(finishedDimensions, HashSet::new))
                .currentDimension(Objects.requireNonNullElse(currentDimension, new ResourceLocation("minecraft", "overworld")))
                .build()));

    @Builder.Default
    private Map<ResourceLocation, DimensionProgressData> dimensionProgress = new DefaultMap<>(DimensionProgressData::createEmpty);
    @Builder.Default
    private Set<ResourceLocation> finishedDimensions = new HashSet<>();

    @Builder.Default
    private ResourceLocation currentDimension = new ResourceLocation("minecraft", "overworld");

    public static GameProgressData createEmpty() {
        return new GameProgressData(new HashMap<>(), new HashSet<>(), DimensionRegistry.NEXUS.getRegistryName());
    }

}
