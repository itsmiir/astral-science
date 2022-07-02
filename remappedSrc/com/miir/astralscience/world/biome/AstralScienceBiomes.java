package com.miir.astralscience.world.biome;

import com.miir.astralscience.world.biome.source.OmeiaBiomeSource;
import com.miir.astralscience.world.gen.surfacebuilder.AstralSurfaceBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public abstract class AstralScienceBiomes {

    public static OmeiaBiomeSource OMEIA_BIOME_SOURCE;
    public static Biome OMEIA_GREENBELT;

    public static Biome createOmeiaGreenbelt() {

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        DefaultBiomeFeatures.addMonsters(spawnSettings, 100, 0, 100);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        generationSettings.surfaceBuilder(AstralSurfaceBuilder.OMEIA.withConfig(new TernarySurfaceConfig(
                Blocks.GRASS.getDefaultState(),
                Blocks.DIRT.getDefaultState(),
                Blocks.GRAVEL.getDefaultState()
        )));
        DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addDefaultLakes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return (new Biome.Builder())
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.NONE)
                .depth(0.125F)
                .scale(0.1F)
                .temperature(0.8F)
                .downfall(0)
                .effects((new BiomeEffects.Builder())
                .waterColor(2441823)
                .waterFogColor(1447457)
                .fogColor(2505307)
                .skyColor(2175312)
                .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}