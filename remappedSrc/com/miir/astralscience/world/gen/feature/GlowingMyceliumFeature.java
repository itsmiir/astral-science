package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.block.AstralBlocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class GlowingMyceliumFeature extends Feature<DefaultFeatureConfig> {
    public GlowingMyceliumFeature() {
        super(DefaultFeatureConfig.CODEC);
    }


    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = AstralFeatures.findNextFloor(world, context.getOrigin().add(8, 0, 8), 115, BlockTags.SNOW);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                BlockPos pos = AstralFeatures.findNextFloor(world, new BlockPos(origin.getX() + i, 0, origin.getZ() + j), 115, BlockTags.SNOW);
                if (world.getBlockState(pos.down()).isIn(BlockTags.SNOW)) {
                    world.setBlockState(pos.down(), AstralBlocks.FROST_MYCELIUM.getDefaultState(), 3);
                }
            }
        }
        return true;
    }
}
