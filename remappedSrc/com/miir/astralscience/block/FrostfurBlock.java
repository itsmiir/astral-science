package com.miir.astralscience.block;

import com.miir.astralscience.world.BlockArray;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FernBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

import java.util.Random;

public class FrostfurBlock extends FernBlock {
    public FrostfurBlock(Settings settings) {
        super(settings);
    }
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return (floor.isIn(BlockTags.SNOW) || floor.isIn(BlockTags.ICE) || floor.isOf(AstralBlocks.FROST_MYCELIUM));
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockArray array = new BlockArray();
        for (BlockPos testPos :
                BlockPos.iterateOutwards(pos, 4, 2, 4)) {
            if (canPlantOnTop(world.getBlockState(testPos), world, testPos) && random.nextFloat() > .75 && world.getBlockState(testPos.up()).isOf(Blocks.AIR))
            {
                array.add(testPos.up());
            }
        }
        array.build(world, new SimpleBlockStateProvider(AstralBlocks.FROSTFUR.getDefaultState()));
    }
}
