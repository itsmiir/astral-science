package com.miir.astralscience.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class IceSheetSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    public IceSheetSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, Blocks.BLUE_ICE.getDefaultState(), seaLevel, seed, AstralScienceSurfaceBuilder.ICE_SHEET_CONFIG);
    }
}
