package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.world.BlockArray;
import com.miir.astralscience.world.gen.stateprovider.SimpleStateProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class GiantFlowerFeature extends AbstractBranchingPlantFeature {


    public GiantFlowerFeature(int maxBranches, int maxTrunkSize, int avgTrunkSize, float branchChance, int minTrunkHeight, boolean canBend, float bendChance, float tallness, BlockStateProvider trunkProvider, BlockStateProvider canopyProvider, BlockStateProvider decorationProvider, TagKey<Block> replaceable) {
        super(1, 15, 10, 0f, 6, true, 0.25f, 0.9f, new SimpleStateProvider(AstralBlocks.GIANT_STEM.getDefaultState()), new SimpleStateProvider(AstralBlocks.CYAN_PETAL.getDefaultState()), new SimpleStateProvider(Blocks.AIR.getDefaultState()), replaceable, false);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos topPos = context.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, context.getOrigin());
        Direction offset = Direction.NORTH;

        for (int y = 1; y <= 15; y++) {
            offset = offset.rotateYClockwise();
            context.getWorld().setBlockState(topPos.up(y).offset(offset), Blocks.STONE.getDefaultState(), 3);
        }

        return true;
    }

    @Override
    protected BlockPos start(FeatureContext<DefaultFeatureConfig> context) {
        return null;
    }

    @Override
    protected BlockArray buildCanopy(FeatureContext<DefaultFeatureConfig> context, BlockPos tip, BlockArray stem) {
        return null;
    }
}
