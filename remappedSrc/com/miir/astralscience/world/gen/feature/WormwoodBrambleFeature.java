package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.world.BlockArray;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class WormwoodBrambleFeature extends AbstractBranchingPlantFeature {
    public WormwoodBrambleFeature(TagKey<Block> replaceable) {
        super(13, 70, 40, 0.5f, 2, true, .5f, 0.5f, new SimpleBlockStateProvider(AstralBlocks.WORMWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()), new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()), replaceable, false);
    }

    @Override
    protected BlockPos start(FeatureContext<DefaultFeatureConfig> context) {
        return null;
    }

    @Override
    protected BlockArray buildCanopy(FeatureContext<DefaultFeatureConfig> context, BlockPos tip, BlockArray stem) {
        return new BlockArray();
    }
}
