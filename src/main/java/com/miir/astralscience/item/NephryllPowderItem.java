package com.miir.astralscience.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class NephryllPowderItem extends AliasedBlockItem {
    public NephryllPowderItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity.world instanceof ServerWorld world) {
            if (!entity.hasNoGravity()) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 40, 3, true, true, true));
                BoneMealItem.createParticles(world, entity.getBlockPos(), world.random.nextBetween(3, 7));
                stack.decrement(1);
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
