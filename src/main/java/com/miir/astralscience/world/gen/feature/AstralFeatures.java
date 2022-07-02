package com.miir.astralscience.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

public abstract class AstralFeatures {

//    public static StructurePieceType CASCADIUM_SKELETON_PIECE = CascadiumSkeletonGenerator.Piece::new;
//    private static final Structure<DefaultFeatureConfig> CASCADIUM_SKELETON = new CascadiumSkeletonFeature();
//    private static final ConfiguredFeature<?, ?> CASCADIUM_SKELETON_CONFIGURED = CASCADIUM_SKELETON.c(DefaultFeatureConfig.DEFAULT);
//
//    public static final GhostVineFeature GHOST_VINE = new GhostVineFeature();
//    public static ConfiguredFeature<?, ?> GHOST_VINE_CONFIGURED = GHOST_VINE.configure(FeatureConfig.DEFAULT)
//            .spreadHorizontally()
//            .decorate(Decorator.COUNT_NOISE
//                    .configure(new CountNoiseDecoratorConfig(-0.8D, 12, 20)));
//
//    public static final FirecapPatchFeature FIRECAP_PATCH = new FirecapPatchFeature();
//    public static ConfiguredFeature<?, ?> FIRECAP_PATCH_CONFIGURED = FIRECAP_PATCH.configure(FeatureConfig.DEFAULT)
//                .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(1)));
//    public static final SpearFernFeature SPEAR_FERN = new SpearFernFeature();
//    public static ConfiguredFeature<?, ?> SPEAR_FERN_CONFIGURED = SPEAR_FERN.configure(FeatureConfig.DEFAULT)
//            .spreadHorizontally()
//            .decorate(Decorator.COUNT_NOISE
//                    .configure(new CountNoiseDecoratorConfig(-0.8D, 12, 15)));
//    public static final FrostfurFeature FROSTFUR = new FrostfurFeature();
//    public static ConfiguredFeature<?, ?> FROSTFUR_CONFIGURED = FROSTFUR.configure(FeatureConfig.DEFAULT)
//            .spreadHorizontally()
//            .spreadHorizontally()
//            .decorate(Decorator.COUNT_NOISE
//                    .configure(new CountNoiseDecoratorConfig(-0.8D, 60, 90)));
//
//    public static final GlowingMyceliumFeature GLOWING_MYCELIUM = new GlowingMyceliumFeature();
//    public static ConfiguredFeature<?, ?> GLOWING_MYCELIUM_CONFIGURED = GLOWING_MYCELIUM.configure(FeatureConfig.DEFAULT)
//            .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(1)));
//
//    public static final NephrumPatchFeature NEPHRUM_PATCH = new NephrumPatchFeature();
//    public static ConfiguredFeature<?, ?> NEPHRUM_PATCH_CONFIGURED = NEPHRUM_PATCH.configure(FeatureConfig.DEFAULT)
//            .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(1)));
//
//    public static final AnglerKelpFeature ANGLER_KELP_FEATURE = new AnglerKelpFeature();
//    public static ConfiguredFeature<?, ?> ANGLER_KELP_CONFIGURED = ANGLER_KELP_FEATURE.configure(FeatureConfig.DEFAULT)
//            .spreadHorizontally()
//            .decorate(Decorator.COUNT_NOISE
//                .configure(new CountNoiseDecoratorConfig(-0.8D, 12, 20)));
//
//    public static final CupLilyFeature CUP_LILY = new CupLilyFeature();
//    public static ConfiguredFeature<?, ?> CUP_LILY_CONFIGURED = CUP_LILY.configure(FeatureConfig.DEFAULT)
//            .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(15)));
//
//    public static GiantFirecapFeature GIANT_FIRECAP;
//    public static PhosphoricThicketFeature PHOSPHORIC_THICKET;
//    public static ConfiguredFeature<?, ?> PHOSPHORIC_THICKET_CONFIGURED;
//    public static OreLikeFeature ORE_CHEESE;
//    public static ConfiguredFeature<?,?> ORE_CHEESE_CONFIGURED;
//    public static ConfiguredFeature<?,?> GIANT_FIRECAP_CONFIGURED;
//
//
//    public static void register() {
//        GIANT_FIRECAP = new GiantFirecapFeature();
//        PHOSPHORIC_THICKET = new PhosphoricThicketFeature();
//        ORE_CHEESE = new OreLikeFeature(DefaultFeatureConfig.CODEC, 12, 50, 30, AstralTags.BASE_STONE_SYLENE, AstralBlocks.STALE_CHEESE.getDefaultState());
//        ORE_CHEESE_CONFIGURED = ORE_CHEESE.configure(FeatureConfig.DEFAULT)
//                .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(1)));
//        GIANT_FIRECAP_CONFIGURED = GIANT_FIRECAP
//                .configure(FeatureConfig.DEFAULT)
//                .spreadHorizontally()
//                .decorate(Decorator.COUNT_NOISE
//                        .configure(new CountNoiseDecoratorConfig(-0.8D, 0, 7)));
//        PHOSPHORIC_THICKET_CONFIGURED = PHOSPHORIC_THICKET
//                .configure(FeatureConfig.DEFAULT)
//                .spreadHorizontally()
//                .decorate(Decorator.COUNT_NOISE
//                        .configure(new CountNoiseDecoratorConfig(-0.8D, 0, 7)));
//
//        Registry.register(Registry.STRUCTURE_PIECE, AstralScience.id("cascadium_skeleton_piece"), CASCADIUM_SKELETON_PIECE);
//        FabricStructureBuilder.create(AstralScience.id("cascadium_skeleton"), CASCADIUM_SKELETON)
//                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
//                .defaultConfig(2, 1, 12345)
//                .adjustsSurface()
//                .register();
//        RegistryKey<ConfiguredStructure<?, ?>> cskeleton_configured = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
//                AstralScience.id("cascadium_skeleton"));
//        BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, cskeleton_configured.getValue(), CASCADIUM_SKELETON_CONFIGURED);
//
//
//        Registry.register(Registry.FEATURE, AstralScience.id("nephrum_patch"), NEPHRUM_PATCH);
//        RegistryKey<ConfiguredFeature<?, ?>> nephrumPatch = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AstralScience.id("nephrum_patch"));
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, nephrumPatch.getValue(), NEPHRUM_PATCH_CONFIGURED);
//
//        Registry.register(Registry.FEATURE, AstralScience.id("ghost_vine"), GHOST_VINE);
//        RegistryKey<ConfiguredFeature<?, ?>> ghostVine = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AstralScience.id("ghost_vine"));
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ghostVine.getValue(), GHOST_VINE_CONFIGURED);
//
//        Registry.register(Registry.FEATURE, AstralScience.id("firecap_patch"), FIRECAP_PATCH);
//        RegistryKey<ConfiguredFeature<?, ?>> firecapPatch = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AstralScience.id("firecap_patch"));
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, firecapPatch.getValue(), FIRECAP_PATCH_CONFIGURED);
//
//        Registry.register(Registry.FEATURE, AstralScience.id("glowing_mycelium"), GLOWING_MYCELIUM);
//        RegistryKey<ConfiguredFeature<?, ?>> glowingMycelium = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AstralScience.id("glowing_mycelium"));
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, glowingMycelium.getValue(), GLOWING_MYCELIUM_CONFIGURED);
//
//        Registry.register(Registry.FEATURE, AstralScience.id("spear_fern"), SPEAR_FERN);
//        RegistryKey<ConfiguredFeature<?, ?>> spearFern = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AstralScience.id("spear_fern"));
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, spearFern.getValue(), SPEAR_FERN_CONFIGURED);
//
//        Registry.register(Registry.FEATURE, AstralScience.id("frostfur"), FROSTFUR);
//        RegistryKey<ConfiguredFeature<?, ?>> frostfur = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AstralScience.id("frostfur"));
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, frostfur.getValue(), FROSTFUR_CONFIGURED);
//
//        Registry.register(Registry.FEATURE, AstralScience.id("cup_lily"), CUP_LILY);
//        RegistryKey<ConfiguredFeature<?, ?>> cupLily = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AstralScience.id("cup_lily"));
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, cupLily.getValue(), CUP_LILY_CONFIGURED);
//
//        Registry.register(Registry.FEATURE, AstralScience.id("angler_kelp"), ANGLER_KELP_FEATURE);
//        RegistryKey<ConfiguredFeature<?, ?>> anglerKelp = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AstralScience.id("angler_kelp"));
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, anglerKelp.getValue(), ANGLER_KELP_CONFIGURED);
//
//        Registry.register(Registry.FEATURE, AstralScience.id("phosphoric_thicket"), PHOSPHORIC_THICKET);
//        RegistryKey<ConfiguredFeature<?, ?>> phosphoricThicket = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AstralScience.id("phosphoric_thicket"));
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, phosphoricThicket.getValue(), PHOSPHORIC_THICKET_CONFIGURED);
//
//        Registry.register(Registry.FEATURE, AstralScience.id("ore_cheese"), ORE_CHEESE);
//        RegistryKey<ConfiguredFeature<?, ?>> oreCheese = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AstralScience.id("ore_cheese"));
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, oreCheese.getValue(), ORE_CHEESE_CONFIGURED);
//
//        Registry.register(Registry.FEATURE, AstralScience.id("giant_firecap"), GIANT_FIRECAP);
//        RegistryKey<ConfiguredFeature<?, ?>> giantFirecap = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AstralScience.id("giant_firecap"));
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, giantFirecap.getValue(), GIANT_FIRECAP_CONFIGURED);
//
//
////        Registry.register(Registry.FEATURE, AstralScience.id("giant_flower"), GIANT_FLOWER);
////        RegistryKey<ConfiguredFeature<?, ?>> giant_flower = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
////                AstralScience.id("giant_flower"));
////        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, giant_flower.getValue(), GIANT_FLOWER_CONFIGURED);
//    }
//
    public static boolean isWater(BlockState state) {
        return state.isOf(Blocks.WATER);
    }

    public static boolean isAir(BlockState state) {
        return state.isOf(Blocks.AIR) || state.isOf(Blocks.CAVE_AIR);
    }

    public static boolean isCaveAir(BlockState state) {
        return state.isOf(Blocks.CAVE_AIR);
    }

    public static boolean isAirOrWater(BlockState state, boolean cave) {
        if (cave) {
            return (isCaveAir(state) || isWater(state));
        } else {
            return (isCaveAir(state) || isAir(state) || isWater(state));
        }
    }

    public static boolean isOnFloor(StructureWorldAccess worldAccess, BlockPos pos) {
        try {
            return isAir(worldAccess.getBlockState(pos)) && !isAirOrWater(worldAccess.getBlockState(pos.add(0, -1, 0)), false);
        } catch (RuntimeException ignored) {
//           a RuntimeException can be thrown if a feature placer random grouping tries to place a feature in an unloaded chunk. i just ignore it
        }
        return false;
    }

    public static BlockPos findNextFloor(StructureWorldAccess world, BlockPos start) {
        return findNextFloor(world, start, world.getTopY());
    }
    public static BlockPos findNextFloor(StructureWorldAccess world, BlockPos start, int maxY, Block floorType) {
        try {
            for (int i = 0; i < maxY; i++) {
                if (
                        !world.getBlockState(start.add(0, i, 0)).isOf(Blocks.POWDER_SNOW) &&
                !world.getBlockState(start.add(0, i, 0)).isSolidBlock(world, start.add(0, i, 0))
                        && world.getBlockState(start.add(0,  i - 1, 0)).isOf(floorType)
                ) {
                    return start.add(0, i, 0);
                }
            }
        } catch (RuntimeException ignored) {
        }
        return start;
    }
    public static BlockPos findNextFloor(StructureWorldAccess world, BlockPos start, int maxY, TagKey<Block> floorType) {
        try {
            for (int i = 0; i < maxY; i++) {
                if (
                        !world.getBlockState(start.add(0, i, 0)).isOf(Blocks.POWDER_SNOW)
                        && !world.getBlockState(start.add(0, i, 0)).isSolidBlock(world, start.add(0, i, 0))
                        && world.getBlockState(start.add(0,  i - 1, 0)).isIn(floorType)
                ) {
                    return start.add(0, i, 0);
                }
            }
        } catch (RuntimeException ignored) {
        }
        return start;
    }
    public static BlockPos findNextFloor(StructureWorldAccess world, BlockPos start, int maxY) {
        try {
            for (int i = 0; i < maxY; i++) {
                if (
                        !world.getBlockState(start.add(0, i, 0)).isOf(Blocks.POWDER_SNOW) &&
                                !world.getBlockState(start.add(0, i, 0)).isSolidBlock(world, start.add(0, i, 0))
                ) {
                    return start.add(0, i, 0);
                }
            }
        } catch (RuntimeException ignored) {
        }
        return start;
    }

    public static BlockPos findNextCeiling(StructureWorldAccess world, BlockPos start) {
        try {
            for (int i = 10; i < world.getHeight(); i++) {
                if (
                        !world.getBlockState(start.add(0, i, 0)).isOf(Blocks.AIR) &&
                                !world.getBlockState(start.add(0, i, 0)).isOf(Blocks.CAVE_AIR) &&
                                !world.getBlockState(start.add(0, i, 0)).isOf(Blocks.WATER) &&
                                !world.getBlockState(start.add(0, i, 0)).isOf(Blocks.LAVA)
                ) {
                    if (world.getBlockState(start.add(0, i - 1, 0)).isOf(Blocks.CAVE_AIR) || world.getBlockState(start.add(0, i - 1, 0)).isOf(Blocks.AIR))
                        return start.add(0, i - 1, 0);
                }
            }
        } catch (RuntimeException ignored) {
//            this actually should never happen but 1am em does not want to change it and have it break two months later
//            and i'll never be able to find out what went wrong
        }
        return BlockPos.ORIGIN;
    }
}
