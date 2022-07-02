package com.miir.astralscience.item;

import com.miir.astralscience.tag.AstralTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ScrewdriverItem extends Item {
    public ScrewdriverItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        PlayerEntity playerEntity = context.getPlayer();
        if (!world.isClient()) {
            assert playerEntity != null;
            if (playerEntity.isSneaking()) {
                if (blockState.isIn(AstralTags.SCREWABLE_BLOCKS)) {
                    world.breakBlock(blockPos, true);
                }
            }
        }
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
