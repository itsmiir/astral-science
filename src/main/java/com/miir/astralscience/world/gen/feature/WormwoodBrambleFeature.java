package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.world.BlockArray;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class WormwoodBrambleFeature extends AbstractBranchingPlantFeature {
    public WormwoodBrambleFeature(Codec<BranchingPlantFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected void buildRoots(FeatureContext<BranchingPlantFeatureConfig> context) {}

    @Override
    protected BlockPos start(FeatureContext<BranchingPlantFeatureConfig> context) {
        return AstralFeatures.findNextFloor(context);
    }

    @Override
    protected BlockArray buildCanopy(FeatureContext<BranchingPlantFeatureConfig> context, BlockPos tip, BlockArray stem) {
        return new BlockArray();
    }
}
