package com.miir.astralscience.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class DimensionalCompassItem extends Item {
    public DimensionalCompassItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        MinecraftServer server = playerEntity.getServer();
        ServerCommandSource serverCommandSource = playerEntity.getCommandSource();
        if (playerEntity.isSneaking()) {
            if (world instanceof ServerWorld) {
                if (world.getRegistryKey() == World.END) {
                    server.getCommandManager().execute(serverCommandSource, "/execute in minecraft:overworld run tp @p ~ 256 ~");
                } else if (world.getRegistryKey() == World.NETHER) {
                    server.getCommandManager().execute(serverCommandSource, "/execute in minecraft:the_end run tp @p ~ 256 ~");
                } else {
                    server.getCommandManager().execute(serverCommandSource, "/execute in minecraft:the_nether run tp @p ~ 256 ~");
                }
                return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
            } else {
                return new TypedActionResult<>(ActionResult.FAIL, playerEntity.getStackInHand(hand));
            }
        } else {
            if (world instanceof ServerWorld) {
                if (world.getRegistryKey() == World.END) {
                    server.getCommandManager().execute(serverCommandSource, "/execute in minecraft:overworld run tp @p ~ ~ ~");
                } else if (world.getRegistryKey() == World.NETHER) {
                    server.getCommandManager().execute(serverCommandSource, "/execute in minecraft:the_end run tp @p ~ ~ ~");
                } else {
                    server.getCommandManager().execute(serverCommandSource, "/execute in minecraft:the_nether run tp @p ~ ~ ~");
                }
                return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
            } else {
                return new TypedActionResult<>(ActionResult.FAIL, playerEntity.getStackInHand(hand));
            }
        }
    }
    @Override
    public void appendTooltip (ItemStack itemstack, World world, List <Text> tooltip, TooltipContext
            tooltipContext){
        tooltip.add(Text.translatable("item.astralscience.dimensional_compass.tooltip"));
        tooltip.add(Text.translatable("item.astralscience.dimensional_compass.tooltip_2"));
    }

}
