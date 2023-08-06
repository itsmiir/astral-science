package com.miir.astralscience.item;

import com.miir.astralscience.AstralScience;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class GPSItem extends Item {
    public GPSItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world instanceof ServerWorld) {
            if (!world.getRegistryKey().getValue().getNamespace().equals(AstralScience.MOD_ID)) {
                switch (world.getRegistryKey().getValue().getPath()) {
                    case "overworld" -> {
                        user.sendMessage(Text.of("[§6GPS§r] You are on planet §6Overworld§r."), false);
                        user.sendMessage(Text.of("[§6GPS§r] Local gravity: §69.81§rm/s^2."), false);
                    }
                    case "the_nether", "the_end" ->
                            user.sendMessage(Text.of("[§6GPS§r] §4Local galactic coordinates are outside the universe!§r"), false);
                    default ->
                            user.sendMessage(Text.of("[§6GPS§r] §4Local CMB radiation does not match any known region of space! Are you lost?§r"), false);
                }
            } else {
                if (world.getRegistryKey().getValue().getPath().equals("overworld_orbit")) {
                    user.sendMessage(Text.of("[§6GPS§r] You are at planet §6Overworld§r."), false);
                    return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));
                }
//                PlanetInfo planet = Interstellar.getCurrentPlanets().getFlags(NameGen.deorbitify(world.getRegistryKey().getValue().getPath()));
//                double gravity = planet.gravity;
                if (world.getRegistryKey().getValue().getPath().contains("_orbit")) {
//                    user.sendMessage(AstralText.of("[§6GPS§r] You are at " + planet.type + " §6" + NameGen.fromId(world.getRegistryKey().getValue().getPath()) + "§r."), false);
                } else {
//                    user.sendMessage(AstralText.of("[§6GPS§r] You are on " + planet.type + " §6" + NameGen.fromId(world.getRegistryKey().getValue().getPath()) + "§r."), false);
//                    user.sendMessage(AstralText.of("[§6GPS§r] Local gravity: §6" + AstralMath.round(gravity * AstralMath.pow(10, 2)) / 100.0 + " §rm/s^2."), false); // yes, i'm truncating the number. Ping me in the discord if you have complaints
                }
            }
            return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.astralscience.gps.tooltip"));
    }
}
