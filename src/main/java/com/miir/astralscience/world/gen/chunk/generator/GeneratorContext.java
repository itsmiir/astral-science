package com.miir.astralscience.world.gen.chunk.generator;

import net.minecraft.util.math.BlockPos;

public record GeneratorContext(BlockPos pos, long seed, int seaLevel, int minY, int height) { }
