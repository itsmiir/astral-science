package com.miir.astralscience.world.gen.stateprovider;

import com.miir.astralscience.world.BlockArray;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

@FunctionalInterface
public interface BlockStateProviderFunction {BlockState apply(Random random, BlockPos pos, BlockArray array);}
