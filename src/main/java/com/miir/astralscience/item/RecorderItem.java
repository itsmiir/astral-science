package com.miir.astralscience.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;


public class RecorderItem extends Item {
    public RecorderItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        float NOTE = world.random.nextFloat();
        playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), 1.0F, NOTE);
        return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
    }
    @Override
    public void appendTooltip(ItemStack itemstack, World world, List <Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.astralscience.recorder.tooltip"));
    }
}
