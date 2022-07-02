package com.miir.astralscience.world.gen.stateprovider;

import com.miir.astralscience.world.BlockArray;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;

public abstract class AdvancedBlockStateProvider extends BlockStateProvider {
//    allows for BlockState provision given a BlockArray, so you can (for example) set all the blocks at the top of
//    a particular feature to a certain blockstate or whatever
    @Override
    protected BlockStateProviderType<?> getType() {
        return BlockStateProviderType.SIMPLE_STATE_PROVIDER;
    }

    @Override
    public BlockState getBlockState(net.minecraft.util.math.random.Random random, BlockPos pos) {
        return this.getBlockState(random, pos, new BlockArray(pos));
    }

    public abstract BlockState getBlockState(Random random, BlockPos pos, BlockArray array);


}
