package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.tag.AstralTags;
import com.miir.astralscience.world.BlockArray;
import com.miir.astralscience.world.gen.stateprovider.PhosphoricThicketCanopyStateProvider;
import com.miir.astralscience.world.gen.stateprovider.SimpleStateProvider;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class PhosphoricThicketFeature extends AbstractBranchingPlantFeature {
    public PhosphoricThicketFeature() {
        super(1, 100, 75, 0.1F, 4, false, 0.3f, 0.8f, new SimpleStateProvider(AstralBlocks.BRAMBLEWOOD_LOG.getDefaultState()), new PhosphoricThicketCanopyStateProvider(), new SimpleStateProvider(Blocks.AIR.getDefaultState()), AstralTags.GIANT_PLANT_REPLACEABLE, false);
    }

    @Override
    protected BlockPos start(FeatureContext<DefaultFeatureConfig> context) {
        return AstralFeatures.findNextFloor(context.getWorld(), context.getOrigin());
    }

    @Override
    protected BlockArray buildCanopy(FeatureContext<DefaultFeatureConfig> context, BlockPos tip, BlockArray stem) {
        BlockArray canopy = new BlockArray();
        Random random = context.getRandom();
        int w = 0;
        int x = random.nextInt(12) + 7;
        int y = random.nextInt(2) + 1;
        int z = random.nextInt(12) + 7;
        for (BlockPos test :
                BlockPos.iterateOutwards(tip, x, y, z)) {
            if (w > x * y * z * Math.min(1.0f, 1 - random.nextFloat() + 0.5)) {
                break;
            }
            w++;
            canopy.add(test);
        }
        for (BlockPos pos :
                canopy.getLowest()) {
            if (context.getWorld().getBlockState(pos.down()).isOf(Blocks.AIR) && random.nextFloat() < 0.15) {
                canopy.add(pos.down(), 1);
            }
        }
//        descriptive variable names > comments
        BlockArray stemWithoutHighest = stem.copy().remove(stem.copy().getHighest());
        canopy.remove(stemWithoutHighest);
        return canopy;
    }
}
