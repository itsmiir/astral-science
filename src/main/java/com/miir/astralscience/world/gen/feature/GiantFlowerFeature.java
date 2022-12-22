package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.tag.AstralTags;
import com.miir.astralscience.world.BlockArray;
import com.miir.astralscience.world.gen.stateprovider.AdvancedBlockStateProvider;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class GiantFlowerFeature extends AbstractBranchingPlantFeature {


    public GiantFlowerFeature(Codec<BranchingPlantFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext context) {
        BlockPos topPos = context.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, context.getOrigin());
        Direction offset = Direction.NORTH;

        for (int y = 1; y <= 15; y++) {
            offset = offset.rotateYClockwise();
            context.getWorld().setBlockState(topPos.up(y).offset(offset), Blocks.STONE.getDefaultState(), 3);
        }

        return true;
    }

    @Override
    protected void buildRoots(FeatureContext<BranchingPlantFeatureConfig> context) {}

    @Override
    protected BlockPos start(FeatureContext<BranchingPlantFeatureConfig> context) {
        return null;
    }

    @Override
    protected BlockArray buildCanopy(FeatureContext<BranchingPlantFeatureConfig> context, BlockPos tip, BlockArray stem) {
        return null;
    }
}
