
package com.evilu.forgottenRealms.worldgen;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lombok.Getter;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.util.valueproviders.IntProvider;

/**
 * CustomStructureConfig
 */
@Getter
public class CustomStructureConfig extends JigsawConfiguration {
    public static final Codec<CustomStructureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(CustomStructureConfig::getStartPool),
                Codec.intRange(0, 500).fieldOf("max_depth").forGetter(CustomStructureConfig::maxDepth),
                IntProvider.CODEC.fieldOf("y_level").forGetter(CustomStructureConfig::getYLevelProvider)
            ).apply(instance, CustomStructureConfig::new));

    private final Holder<StructureTemplatePool> startPool;
    private final IntProvider yLevelProvider;

    private static final Random rand = new Random(System.nanoTime());

    public CustomStructureConfig(final Holder<StructureTemplatePool> startPool, final int maxDepth, final IntProvider yLevelProvider) {
        super(startPool, maxDepth);
        this.startPool = startPool;
        this.yLevelProvider = yLevelProvider;
    }

    public int sampleYLevel() {
        return yLevelProvider.sample(rand);
    }

    public int sampleYLevel(final long seed) {
        return yLevelProvider.sample(new Random(seed));
    }
}
