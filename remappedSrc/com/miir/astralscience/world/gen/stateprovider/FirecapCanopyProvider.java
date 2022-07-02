package com.miir.astralscience.world.gen.stateprovider;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.world.BlockArray;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;

import java.util.Random;

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
        if (canopy.getFlags(pos) == 1) {
//                should not be doing this but i can't remember how to do bitwise stuff and i'm on an airplane right now
//                with no wifi
            return MIDDLE;
        } else return CROWN;
    }
}
