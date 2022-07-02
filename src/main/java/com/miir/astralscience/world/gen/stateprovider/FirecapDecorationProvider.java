package com.miir.astralscience.world.gen.stateprovider;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.world.BlockArray;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;

public class FirecapDecorationProvider extends AdvancedBlockStateProvider {
    public FirecapDecorationProvider() {
    }

    @Override
    protected BlockStateProviderType<?> getType() {
        return BlockStateProviderType.SIMPLE_STATE_PROVIDER;
    }

    @Override
    public BlockState getBlockState(Random random, BlockPos pos, BlockArray array) {
        return AstralBlocks.BLUEMOSS.getDefaultState();
    }
}
