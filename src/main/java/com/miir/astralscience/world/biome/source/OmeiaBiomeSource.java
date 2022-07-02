package com.miir.astralscience.world.biome.source;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.stream.Stream;

public class OmeiaBiomeSource extends BiomeSource {
    protected OmeiaBiomeSource(Stream<RegistryEntry<Biome>> biomeStream) {
        super(biomeStream);
    }
//    private long seed;
//    private Identifier polarBiome;
//    private Identifier midBiome;
//    private Identifier lowBiome;
//
//    private static Supplier<Biome> biomePoints;
//    public static final Codec<? extends BiomeSource> CODEC = MultiNoiseBiomeSource.CODEC;
//
//    public OmeiaBiomeSource(List<Biome> biomes) {
//        super(biomes);
//    }
//
//
//
////            RecordCodecBuilder.mapCodec((instance) -> {
////        return instance.group(Codec.LONG.fieldOf("seed").forGetter((omeiaBiomeSource) -> {
////            return omeiaBiomeSource.seed;
////        }), Identifier.CODEC.fieldOf("polar_biome").forGetter((omeiaBiomeSource) -> {
////            return omeiaBiomeSource.polarBiome;
////        }), OmeiaBiomeSource.CODEC.fieldOf("temperature_noise").forGetter((omeiaBiomeSource) -> {
////            return omeiaBiomeSource.temperatureNoiseParameters;
////        }), OmeiaBiomeSource.CODEC.fieldOf("humidity_noise").forGetter((omeiaBiomeSource) -> {
////            return omeiaBiomeSource.humidityNoiseParameters;
////        }), OmeiaBiomeSource.CODEC.fieldOf("altitude_noise").forGetter((omeiaBiomeSource) -> {
////            return omeiaBiomeSource.altitudeNoiseParameters;
////        }), OmeiaBiomeSource.CODEC.fieldOf("weirdness_noise").forGetter((omeiaBiomeSource) -> {
////            return omeiaBiomeSource.weirdnessNoiseParameters;
////        })).apply(instance, (Function6)(OmeiaBiomeSource::new));
////    });

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }
//
    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        return null;
    }
//
//    @Override
//    public BiomeSource withSeed(long seed) {
//        return null;
//    }
//
//    @Override
//    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
//        int longitude = Math.abs(biomeX);
//        return DefaultBiomeCreator.createBasaltDeltas();
//
//    }
}
