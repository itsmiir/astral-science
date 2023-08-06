package com.miir.astralscience.world.gen;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.world.gen.chunk.generator.AstralChunkGenerator;
import com.miir.astralscience.world.gen.feature.AstralFeatures;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public abstract class AstralWorldgen {
    public static void register() {
        Registry.register(Registries.CHUNK_GENERATOR, AstralScience.id("astral"), AstralChunkGenerator.CODEC);
        AstralFeatures.register();
    }
}
