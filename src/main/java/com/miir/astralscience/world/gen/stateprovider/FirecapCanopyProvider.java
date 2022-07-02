package com.miir.astralscience.world.gen.stateprovider;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.world.BlockArray;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;

public class FirecapCanopyProvider extends AdvancedBlockStateProvider {
    private static final BlockState CROWN = AstralBlocks.FIRECAP_SCALES.getDefaultState();
    private static final BlockState MIDDLE = AstralBlocks.FIRECAP_GILLS.getDefaultState();

    public FirecapCanopyProvider() {
    }

    @Override
    protected BlockStateProviderType<?> getType() {
        return BlockStateProviderType.SIMPLE_STATE_PROVIDER;
    }


    public BlockState getBlockState(Random random, BlockPos pos, BlockArray canopy) {
        if ((canopy.getFlags(pos) & 1) == 1) {
            return MIDDLE;
        } else return CROWN;
    }
}
