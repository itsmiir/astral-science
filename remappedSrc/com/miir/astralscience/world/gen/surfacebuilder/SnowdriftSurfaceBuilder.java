package com.miir.astralscience.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

import static java.lang.Math.abs;

public class SnowdriftSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    public SnowdriftSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }
    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seedNoise, TernarySurfaceConfig surfaceBlocks) {
        if (abs(noise) > 1.6D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 1.5D) {
            for (int i = 0; i < 1; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 1.4D) {
            for (int i = 0; i < 2; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 1.3D) {
            for (int i = 0; i < 3; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 1.2D) {
            for (int i = 0; i < 4; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 1.1D) {
            for (int i = 0; i < 5; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 1.0D) {
            for (int i = 0; i < 6; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 0.9D) {
            for (int i = 0; i < 7; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 0.8D) {
            for (int i = 0; i < 8; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 0.7D) {
            for (int i = 0; i < 9; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 0.6D) {
            for (int i = 0; i < 10; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 0.5D) {
            for (int i = 0; i < 11; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 0.4D) {
            for (int i = 0; i < 12; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 0.3D) {
            for (int i = 0; i < 13; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 0.2D) {
            for (int i = 0; i < 14; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else if (abs(noise) > 0.1D) {
            for (int i = 0; i < 15; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        } else {
            for (int i = 0; i < 16; i++) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.POWDER_SNOW_CONFIG);
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seedNoise, AstralScienceSurfaceBuilder.SNOW_CONFIG);
        }
    }
}
