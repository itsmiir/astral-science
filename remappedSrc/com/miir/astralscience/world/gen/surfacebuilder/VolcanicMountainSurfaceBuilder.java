package com.miir.astralscience.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

import static java.lang.Math.abs;

public class VolcanicMountainSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    public VolcanicMountainSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int xPos, int zPos, int yPos, double d, BlockState blockState, BlockState blockState2, int l, long m, TernarySurfaceConfig ternarySurfaceConfig) {
        for (int iterator = 0; iterator < 10; iterator++) {
            if (abs(d) < 0.8D) {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.CARVING_CONFIG);
            } else if (abs(d) < 1.1) {
                if ((random.nextFloat() / 6) > (abs(d) - 0.8D))
                    SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.MAGMATIC_BLACKSTONE_CONFIG);
            } else {
                SurfaceBuilder.DEFAULT.generate(random, chunk, biome, xPos, zPos, yPos, d, blockState, blockState2, l, m, AstralScienceSurfaceBuilder.BLACKSTONE_CONFIG);
            }
        }
    }
}
