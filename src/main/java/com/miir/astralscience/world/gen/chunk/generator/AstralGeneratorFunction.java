package com.miir.astralscience.world.gen.chunk.generator;

import net.minecraft.block.BlockState;

@FunctionalInterface
public interface AstralGeneratorFunction {
    GeneratorSample sample(GeneratorContext context);
}
