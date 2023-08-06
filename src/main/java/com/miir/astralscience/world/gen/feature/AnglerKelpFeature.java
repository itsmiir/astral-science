package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.block.AstralBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.KelpBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class AnglerKelpFeature extends AbstractFeature<DefaultFeatureConfig> {
    public AnglerKelpFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext context) {
        int i = 0;
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        int j = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX(), blockPos.getZ());
        BlockPos blockPos2 = new BlockPos(blockPos.getX(), j, blockPos.getZ());
        while (!context.getWorld().getBlockState(blockPos2).isOf(Blocks.WATER)) {
            if (blockPos2.getY() <= 0) {
                break;
            }
            blockPos2 = blockPos2.down();
        }
        while (context.getWorld().getBlockState(blockPos2).isOf(Blocks.WATER) && context.getWorld().getBlockState(blockPos2.down()).isOf(Blocks.WATER)) {
            if (blockPos2.getY() <= 0) {
                break;
            }
            blockPos2 = blockPos2.down();
        }
        if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER)) {
            BlockState kelp = AstralBlocks.ANGLER_KELP.getDefaultState();
            BlockState kelpPlant = AstralBlocks.ANGLER_KELP_PLANT.getDefaultState();
            int k = 1 + random.nextInt(10);

            for(int l = 0; l <= k; ++l) {
                if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER) && structureWorldAccess.getBlockState(blockPos2.up()).isOf(Blocks.WATER) && kelpPlant.canPlaceAt(structureWorldAccess, blockPos2)) {
                    if (l == k) {
                        structureWorldAccess.setBlockState(blockPos2, kelp.with(KelpBlock.AGE, random.nextInt(4) + 20), Block.NOTIFY_LISTENERS);
                        ++i;
                    } else {
                        structureWorldAccess.setBlockState(blockPos2, kelpPlant, Block.NOTIFY_LISTENERS);
                    }
                } else if (l > 0) {
                    BlockPos blockPos3 = blockPos2.down();
                    if (kelp.canPlaceAt(structureWorldAccess, blockPos3) && !structureWorldAccess.getBlockState(blockPos3.down()).isOf(Blocks.KELP)) {
                        structureWorldAccess.setBlockState(blockPos3, kelp.with(KelpBlock.AGE, random.nextInt(4) + 20), Block.NOTIFY_LISTENERS);
                        ++i;
                    }
                    break;
                }

                blockPos2 = blockPos2.up();
            }
        }

        return i > 0;
    }
}
