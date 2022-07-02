package com.miir.astralscience.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

import static java.lang.Math.abs;

public class DebugSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    public DebugSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }


//    double D: biome noise settings
//    int L: something to do with dimension id
    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int xPos, int zPos, int yPos, double d, BlockState blockState, BlockState blockState2, int l, long m, TernarySurfaceConfig ternarySurfaceConfig) {
                if (abs(d) > 1.6D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.RED_CONFIG);
        } else if (abs(d) > 1.5D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.ORANGE_CONFIG);
        } else if (abs(d) > 1.4D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.YELLOW_CONFIG);
        } else if (abs(d) > 1.3D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.LIME_CONFIG);
        } else if (abs(d) > 1.2D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.GREEN_CONFIG);
        } else if (abs(d) > 1.1D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.CYAN_CONFIG);
        } else if (abs(d) > 1.0D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.LIGHT_BLUE_CONFIG);
        } else if (abs(d) > 0.9D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.BLUE_CONFIG);
        } else if (abs(d) > 0.8D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.PURPLE_CONFIG);
        } else if (abs(d) > 0.7D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.MAGENTA_CONFIG);
        } else if (abs(d) > 0.6D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.BROWN_CONFIG);
        } else if (abs(d) > 0.5D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.WHITE_CONFIG);
        } else if (abs(d) > 0.4D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.LIGHT_GRAY_CONFIG);
        } else if (abs(d) > 0.3D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.GRAY_CONFIG);
        } else if (abs(d) > 0.2D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.BLACK_CONFIG);
        } else if (abs(d) > 0.1D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.PINK_CONFIG);
        } else if (d == 0.0D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.MAGMATIC_BLACKSTONE_CONFIG);
        }
    }
}
