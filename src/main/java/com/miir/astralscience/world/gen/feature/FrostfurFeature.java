package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.Config;
import com.miir.astralscience.block.AstralBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class FrostfurFeature extends AbstractFeature {
    public FrostfurFeature(Codec codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext context) {
        StructureWorldAccess access = context.getWorld();
        BlockPos pos = context.getOrigin();
//        pos = AstralFeatures.findNextFloor(access, pos, 150, AstralBlocks.FROST_MYCELIUM);
        if (
                pos.equals(BlockPos.ORIGIN)
                || pos.getY() > Config.IRIS_SURFACE_HEIGHT - 25
                || !access.getBlockState(pos).isOf(Blocks.AIR)
                || !access.getBlockState(pos.down()).isSolidBlock(access, pos.down())
        ) {
            return false;
        }
        access.setBlockState(pos, AstralBlocks.FROSTFUR.getDefaultState(), 3);
        return true;
    }
}
