package com.miir.astralscience.world.gen.stateprovider;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.world.BlockArray;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class PhosphoricThicketCanopyStateProvider extends AdvancedBlockStateProvider {
    //    wow, that's a mouthful
    private static final BlockState LEAVES = Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1);
    private static final BlockState GLOW_PODS = AstralBlocks.GHOST_VINES.getDefaultState();
    @Override
    public BlockState getBlockState(Random random, BlockPos pos, BlockArray array) {
        if (array.getFlags(pos) == 1) {
            return GLOW_PODS;
        } else return LEAVES;
    }
}
