package com.miir.astralscience.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;

public class GalacticMapItem extends Item {
    public GalacticMapItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.isSneaking()) {
            if (world instanceof ServerWorld) {
                ArrayList<String> worldList = new ArrayList<>();
                Iterable<ServerWorld> worlds = world.getServer().getWorlds();
                for (ServerWorld serverWorld:
                        worlds) {
//                    if (PlanetInfo.canHouseStarship(serverWorld.getRegistryKey())) {
//                        worldList.add(serverWorld.getRegistryKey().getValue().getPath());
//                    }
                }
                if (stack.getItem().equals(AstralItems.GALACTIC_MAP)) {
//                    map(stack, worldList);
                }
            }
//        } else if (stack.getTag() != null) {
//            CompoundTag tag = stack.getTag();
//            int i = tag.getInt("dimensions");
//            for (int j = 1; j < i; j++) {
//               user.sendMessage(
//                       AstralText.of(tag.getString(Integer.toString(j))),
//                       false);
//            }
        }
        return super.use(world, user, hand);
    }

//    public static void map(ItemStack stack, ArrayList<String> dimensions) {
//        CompoundTag tag = stack.getOrCreateTag();
//        int i = 1;
//        for (String dimensionName :
//                dimensions) {
//            addDimension(stack, dimensionName, i);
//            i++;
//        }
//        tag.putInt("dimensions", i);
//        tag.putBoolean("mapped", true);
//    }
//
//    public static void addDimension(ItemStack stack, String dimension, int ordinal) {
//        CompoundTag tag = stack.getOrCreateTag();
//        tag.putString(Integer.toString(ordinal), dimension);
//    }
//
//    @Override
//    public boolean hasGlint(ItemStack stack) {
//        CompoundTag tag = stack.getOrCreateTag();
//        return tag.getBoolean("mapped");
//    }
}
