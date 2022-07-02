package com.miir.astralscience.block;

import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.function.Supplier;

public class FirecapPlantBlock extends MushroomPlantBlock {
    public FirecapPlantBlock(Settings settings, Supplier<ConfiguredFeature<?, ?>> feature) {
        super(settings, feature);
    }
}
