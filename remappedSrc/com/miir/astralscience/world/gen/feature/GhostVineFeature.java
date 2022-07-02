package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.Config;
import com.miir.astralscience.block.AstralBlocks;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class GhostVineFeature extends Feature<DefaultFeatureConfig> {
    public GhostVineFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess access = context.getWorld();
        Random random = context.getRandom();
        for (int i = 0; i < random.nextInt(40); i++) {
            BlockPos pos = context.getOrigin();
            BlockPos pos1 = pos.add(random.nextInt(5), 0, random.nextInt(5));
            pos1 = AstralFeatures.findNextCeiling(access, pos1);
            if (
                    !pos1.equals(BlockPos.ORIGIN)
                    && access.getBlockState(pos1.up()).isSolidBlock(access, pos1.up())
                    && pos1.getY() < Config.IRIS_SURFACE_HEIGHT
            ) {
                int age = random.nextInt(10);
                BlockState vineState = AstralBlocks.GHOST_VINES.getDefaultState().with(AbstractPlantStemBlock.AGE, age);
                context.getWorld().setBlockState(pos1, vineState, 3);
                for (int j = 0; j < 10; j++) {
                    if (context.getWorld().getBlockState(pos1.add(0, -j, 0)).isOf(Blocks.AIR) || context.getWorld().getBlockState(pos1.add(0, -j, 0)).isOf(Blocks.CAVE_AIR)) {
                        context.getWorld().setBlockState(pos1.add(0, -j, 0), vineState, 3);
                        context.getWorld().setBlockState(pos1.add(0, 1 -j, 0), AstralBlocks.GHOST_VINES_PLANT.getDefaultState(), 3);
                        if (random.nextFloat() < .3) {
                            break;
                        }
                    }
                }
                pos = AstralFeatures.findNextCeiling(access, pos);
                if (!pos.equals(BlockPos.ORIGIN) && access.getBlockState(pos.up()).isSolidBlock(access, pos)) {
                    context.getWorld().setBlockState(pos, vineState, 3);
                    return true;
                }
            }
        }
        return false;
    }
}
