package com.miir.astralscience.world.gen.surfacebuilder;

import com.miir.astralscience.Config;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class OmeiaSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    public OmeiaSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }
    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        if (Math.abs(x) < (Config.GREEN_BELT_WIDTH / 2)) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, AstralScienceSurfaceBuilder.OMEIA_GREENBELT_CONFIG);
        } else if (Math.abs(x) < ((Config.GREEN_BELT_WIDTH / 2) + Config.BROWN_BELT_WIDTH)) {
            float r = random.nextInt(Config.BROWN_BELT_WIDTH);
            if ((r / Config.BROWN_BELT_WIDTH) < 0.5) {
                if (r < x) {
                    SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, AstralScienceSurfaceBuilder.OMEIA_BROWNBELT_CONFIG);
                }
            } else {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, AstralScienceSurfaceBuilder.OMEIA_GREENBELT_CONFIG);
            }
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, AstralScienceSurfaceBuilder.OMEIA_CAP_CONFIG);
        }
    }
}
