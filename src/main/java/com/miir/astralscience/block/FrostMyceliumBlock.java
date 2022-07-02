package com.miir.astralscience.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class FrostMyceliumBlock extends SpreadableBlock {
    protected FrostMyceliumBlock(Settings settings) {
        super(settings);
    }

    private static boolean canSurvive(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos.up());
        return (!state.isSolidBlock(world, pos) && !(world.getFluidState(pos.up()).getLevel() == 8));
    }

    private static boolean canSpread(World world, BlockPos pos) {
        return canSurvive(world, pos) && !world.getFluidState(pos.up()).isIn(FluidTags.WATER);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!canSurvive(world, pos)) {
            world.setBlockState(pos, Blocks.SNOW_BLOCK.getDefaultState());
        } else {
            BlockState blockState = this.getDefaultState();
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (world.getBlockState(blockPos).isOf(Blocks.SNOW_BLOCK) && canSpread(world, blockPos)) {
                    world.setBlockState(blockPos, blockState);
                }
            }

        }
    }
}
