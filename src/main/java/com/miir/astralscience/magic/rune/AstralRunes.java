package com.miir.astralscience.magic.rune;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.block.StarlightCollectorBlock;
import com.miir.astralscience.world.BlockArray;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AstralRunes {
    public static Object2ObjectArrayMap<BlockState, BlockState> TRANSMUTATIONS;
    public static Object2ObjectArrayMap<BlockState, String[]> RECIPES;
    public static final int MAX_LENGTH = 8;

    public static void register() {
        TRANSMUTATIONS = Util.make(new Object2ObjectArrayMap<>(), (map -> {
            map.put(Blocks.PISTON.getDefaultState(), AstralBlocks.NEPHRYLL_BLOCK.getDefaultState());
            map.put(Blocks.DAYLIGHT_DETECTOR.getDefaultState(), AstralBlocks.STARLIGHT_COLLECTOR.getDefaultState());
        }));
        RECIPES = Util.make(new Object2ObjectArrayMap<>(), (map -> {
            map.put(Blocks.PISTON.getDefaultState(), new String[] {
                    "pierke", "physe", "caphta", "waergo",
                    "elef", "caphta", "waergo", "mica"
            });
            map.put(Blocks.DAYLIGHT_DETECTOR.getDefaultState(), new String[] {
                    "pierke", "physe", "caphta", "elef",
                    "elef", "caphta", "elef", "kyter"
            });
        }));
    }

//    at some point i'll make these recipes data-driven, until then, they're in this super ugly thing. sorry ~em

    public static boolean runesMatch(World world, BlockState state, BlockPos pos) {
        try {
            BlockState cleanedState = state.getBlock().getDefaultState();
            String[] runes = RECIPES.get(cleanedState);
            return StarlightCollectorBlock.checkPattern(pos, world, runes[0], runes[1], runes[2], runes[3], runes[4], runes[5], runes[6], runes[7]);
        } catch (NullPointerException e) {
            return false;
        }
    }
    public static boolean hasStarlight(World world, BlockPos pos) {
        BlockArray array = StarlightCollectorBlock.getMultiblock().offset(pos);
        return array.check((blockPos) -> StarlightCollectorBlock.hasStarlight(world, blockPos));
    }

    public static BlockState transmute(BlockState state) {
        return TRANSMUTATIONS.get(state.getBlock().getDefaultState());
    }

}
