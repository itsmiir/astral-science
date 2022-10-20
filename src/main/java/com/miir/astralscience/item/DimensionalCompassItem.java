package com.miir.astralscience.item;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
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
import net.minecraft.world.TeleportTarget;
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
                    FabricDimensions.teleport(playerEntity, server.getOverworld(), new TeleportTarget(playerEntity.getPos(), playerEntity.getVelocity(), playerEntity.getYaw(), playerEntity.getPitch()));
                } else if (world.getRegistryKey() == World.NETHER) {
                    FabricDimensions.teleport(playerEntity, server.getWorld(World.END), new TeleportTarget(playerEntity.getPos(), playerEntity.getVelocity(), playerEntity.getYaw(), playerEntity.getPitch()));
                } else {
                    FabricDimensions.teleport(playerEntity, server.getWorld(World.NETHER), new TeleportTarget(playerEntity.getPos(), playerEntity.getVelocity(), playerEntity.getYaw(), playerEntity.getPitch()));
                }
                return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
            } else {
                return new TypedActionResult<>(ActionResult.FAIL, playerEntity.getStackInHand(hand));
            }
        } else {
            if (world instanceof ServerWorld) {
                if (world.getRegistryKey() == World.END) {
                    FabricDimensions.teleport(playerEntity, server.getOverworld(), new TeleportTarget(playerEntity.getPos(), playerEntity.getVelocity(), playerEntity.getYaw(), playerEntity.getPitch()));
                } else if (world.getRegistryKey() == World.NETHER) {
                    FabricDimensions.teleport(playerEntity, server.getWorld(World.END), new TeleportTarget(playerEntity.getPos(), playerEntity.getVelocity(), playerEntity.getYaw(), playerEntity.getPitch()));
                } else {
                    FabricDimensions.teleport(playerEntity, server.getWorld(World.NETHER), new TeleportTarget(playerEntity.getPos(), playerEntity.getVelocity(), playerEntity.getYaw(), playerEntity.getPitch()));
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
