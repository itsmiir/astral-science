package com.miir.astralscience.item;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.world.BlockArray;
import com.miir.astralscience.world.gen.feature.GiantTreelikeFeature;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class ScrewdriverItem extends Item {
    public ScrewdriverItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos origin = context.getBlockPos().up();

        int branchLength = 10;
        int maxBranches = 4;
        int branchVariation = 4;
        int depth = 2;
        int initThickness = 3;
        float scale = 5;
        float tallness = 0.75f;
        float width = .75f;
//            assert playerEntity != null;
//            if (playerEntity.isSneaking()) {
//                if (blockState.isIn(AstralTags.SCREWABLE_BLOCKS)) {
//                    world.breakBlock(blockPos, true);
//                }
//            }
//        }
            return super.useOnBlock(context);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BlockPos pos = user.getBlockPos();
        if (world.getBlockState(pos).isOf(Blocks.MOVING_PISTON)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
        return super.use(world, user, hand);
    }
}
