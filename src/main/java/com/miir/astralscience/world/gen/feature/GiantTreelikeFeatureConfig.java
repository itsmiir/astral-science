package com.miir.astralscience.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.FeatureConfig;

public record GiantTreelikeFeatureConfig(int maxBranches, int branchLength, int branchVariation, int depth, int initThickness, float tallness, float wideness, float scale, Identifier blockState) implements FeatureConfig {
    public static final Codec<GiantTreelikeFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
        instance.group(
                Codec.INT.fieldOf("max_branches").forGetter(GiantTreelikeFeatureConfig::maxBranches),
                Codec.INT.fieldOf("branch_length").forGetter(GiantTreelikeFeatureConfig::branchLength),
                Codec.INT.fieldOf("branch_variation").forGetter(GiantTreelikeFeatureConfig::branchVariation),
                Codec.INT.fieldOf("depth").forGetter(GiantTreelikeFeatureConfig::depth),
                Codec.INT.fieldOf("init_thickness").forGetter(GiantTreelikeFeatureConfig::initThickness),
                Codec.FLOAT.fieldOf("tallness").forGetter(GiantTreelikeFeatureConfig::tallness),
                Codec.FLOAT.fieldOf("wideness").forGetter(GiantTreelikeFeatureConfig::wideness),
                Codec.FLOAT.fieldOf("scale").forGetter(GiantTreelikeFeatureConfig::scale),
                Identifier.CODEC.fieldOf("block").forGetter(GiantTreelikeFeatureConfig::blockState)
        ).apply(instance, GiantTreelikeFeatureConfig::new));
}
