package com.miir.astralscience.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.FeatureConfig;

public record BranchingPlantFeatureConfig(
        int maxBranches, int maxTrunkSize, int avgTrunkSize,int minTrunkHeight,
        float branchChance, boolean canBend, float bendChance, float tallness,
        Identifier trunkProvider, Identifier canopyProvider, Identifier decorationProvider, Identifier rootProvider,
        int maxRoots, int avgRoots, float rootBranchiness, boolean adjacentAllowed)
        implements FeatureConfig {
    public static final Codec<BranchingPlantFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    Codec.INT.fieldOf("max_branches").forGetter(BranchingPlantFeatureConfig::maxBranches),
                    Codec.INT.fieldOf("max_trunk_size").forGetter(BranchingPlantFeatureConfig::maxTrunkSize),
                    Codec.INT.fieldOf("avg_trunk_size").forGetter(BranchingPlantFeatureConfig::avgTrunkSize),
                    Codec.INT.fieldOf("min_trunk_size").forGetter(BranchingPlantFeatureConfig::minTrunkHeight),
                    Codec.FLOAT.fieldOf("branch_chance").forGetter(BranchingPlantFeatureConfig::branchChance),
                    Codec.BOOL.fieldOf("can_bend").forGetter(BranchingPlantFeatureConfig::canBend),
                    Codec.FLOAT.fieldOf("bend_chance").forGetter(BranchingPlantFeatureConfig::bendChance),
                    Codec.FLOAT.fieldOf("tallness").forGetter(BranchingPlantFeatureConfig::tallness),
                    Identifier.CODEC.fieldOf("trunk_provider").forGetter(BranchingPlantFeatureConfig::trunkProvider),
                    Identifier.CODEC.fieldOf("canopy_provider").forGetter(BranchingPlantFeatureConfig::canopyProvider),
                    Identifier.CODEC.fieldOf("decoration_provider").forGetter(BranchingPlantFeatureConfig::decorationProvider),
                    Identifier.CODEC.fieldOf("root_provider").forGetter(BranchingPlantFeatureConfig::rootProvider),
                    Codec.INT.fieldOf("max_roots").forGetter(BranchingPlantFeatureConfig::maxRoots),
                    Codec.INT.fieldOf("avg_roots").forGetter(BranchingPlantFeatureConfig::avgRoots),
                    Codec.FLOAT.fieldOf("root_branchiness").forGetter(BranchingPlantFeatureConfig::rootBranchiness),
                    Codec.BOOL.fieldOf("adjacent_allowed").forGetter(BranchingPlantFeatureConfig::adjacentAllowed)
                    ).apply(instance, BranchingPlantFeatureConfig::new)
    );
}
