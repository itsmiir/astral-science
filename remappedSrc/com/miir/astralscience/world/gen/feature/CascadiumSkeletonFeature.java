package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.structure.CascadiumSkeletonGenerator;
import com.mojang.serialization.Codec;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class CascadiumSkeletonFeature extends StructureFeature<DefaultFeatureConfig> {
    public CascadiumSkeletonFeature() {
        super(DefaultFeatureConfig.CODEC);
    }
    @Override
    public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return CascadiumSkeletonFeature.Start::new;
    }
    public static class Start extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> defaultFeatureConfigStructureFeature, ChunkPos chunkPos, int i, long l) {
            super(defaultFeatureConfigStructureFeature, chunkPos, i, l);

        }

        @Override
        public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, ChunkPos pos, Biome biome, DefaultFeatureConfig config, HeightLimitView world) {
            int k = pos.getStartX() + this.random.nextInt(16);
            int l = pos.getStartZ() + this.random.nextInt(16);
            int m = chunkGenerator.getSeaLevel();
            int n = m - 2 + this.random.nextInt(40);

            CascadiumSkeletonGenerator.addPieces(manager, this.children, this.random, new BlockPos(k, n, l));
            this.setBoundingBoxFromChildren();
        }
    }
}
