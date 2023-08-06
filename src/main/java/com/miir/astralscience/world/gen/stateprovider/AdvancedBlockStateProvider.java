package com.miir.astralscience.world.gen.stateprovider;

import com.miir.astralscience.world.BlockArray;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;

/**
 * a more powerful version of <code>BlockStateProvider</code>. Uses BlockArrays to allow for context-dependent
 * blockstate providing.
 */
public class AdvancedBlockStateProvider extends BlockStateProvider {
    public BlockStateProviderFunction f;

    public AdvancedBlockStateProvider(BlockStateProviderFunction f) {

    }

    @Override
    protected BlockStateProviderType<?> getType() {
        return BlockStateProviderType.SIMPLE_STATE_PROVIDER;
    }

    @Override
    public BlockState get(Random random, BlockPos pos) {
        return this.get(random, pos, new BlockArray(pos));
    }

    public BlockState get(Random random, BlockPos pos, BlockArray array) {
        return this.f.apply(random, pos, array);
    }
}
