package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.structure.CascadiumSkeletonGenerator;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;

public class FeatureRegistry {

    public static StructurePieceType CASCADIUM_SKELETON_PIECE = CascadiumSkeletonGenerator.Piece::new;
    private static final StructureFeature<DefaultFeatureConfig> CASCADIUM_SKELETON = new CascadiumSkeletonFeature(DefaultFeatureConfig.CODEC);
    private static final ConfiguredStructureFeature<?, ?> CSKELETON_CONFIGURED = CASCADIUM_SKELETON.configure(DefaultFeatureConfig.DEFAULT);

//    private static final Feature<DefaultFeatureConfig> GIANT_FLOWER = new GiantFlowerFeature(DefaultFeatureConfig.CODEC);
//    public static final ConfiguredFeature<?, ?> GIANT_FLOWER_CONFIGURED = GIANT_FLOWER.configure(FeatureConfig.DEFAULT)
//            .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(5)));

    public static void register() {
        Registry.register(Registry.STRUCTURE_PIECE, AstralScience.id("cascadium_skeleton_piece"), CASCADIUM_SKELETON_PIECE);
        FabricStructureBuilder.create(AstralScience.id("cascadium_skeleton"), CASCADIUM_SKELETON)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(1, 1, 12345)
                .adjustsSurface()
                .register();
        RegistryKey<ConfiguredStructureFeature<?, ?>> cskeleton_configured = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_WORLDGEN,
                AstralScience.id("cascadium_skeleton"));
        BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, cskeleton_configured.getValue(), CSKELETON_CONFIGURED);


//        Registry.register(Registry.FEATURE, AstralScience.id("giant_flower"), GIANT_FLOWER);
//        RegistryKey<ConfiguredFeature<?, ?>> giant_flower = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
//                AstralScience.id("giant_flower"));
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, giant_flower.getValue(), GIANT_FLOWER_CONFIGURED);
    }
}
