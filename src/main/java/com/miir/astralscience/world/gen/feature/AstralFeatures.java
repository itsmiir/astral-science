package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.AstralScience;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public abstract class AstralFeatures {

//    public static StructurePieceType CASCADIUM_SKELETON_PIECE = CascadiumSkeletonGenerator.Piece::new;
//    private static final Structure<DefaultFeatureConfig> CASCADIUM_SKELETON = new CascadiumSkeletonFeature();
//    private static final ConfiguredFeature<?, ?> CASCADIUM_SKELETON_CONFIGURED = CASCADIUM_SKELETON.c(DefaultFeatureConfig.DEFAULT);

    public static final GhostVineFeature GHOST_VINE = new GhostVineFeature();
    public static final FirecapPatchFeature FIRECAP_PATCH = new FirecapPatchFeature();
    public static final SpearFernFeature SPEAR_FERN = new SpearFernFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> FROSTFUR = new FrostfurFeature(DefaultFeatureConfig.CODEC);
    public static final GlowingMyceliumFeature GLOWING_MYCELIUM = new GlowingMyceliumFeature();
    public static final NephrumPatchFeature NEPHRUM_PATCH = new NephrumPatchFeature();
    public static final Feature<DefaultFeatureConfig> ANGLER_KELP_FEATURE = new AnglerKelpFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> CUP_LILY = new CupLilyFeature(DefaultFeatureConfig.CODEC);
    public static final GiantFirecapFeature GIANT_FIRECAP = new GiantFirecapFeature(BranchingPlantFeatureConfig.CODEC);
    public static final PhosphoricThicketFeature PHOSPHORIC_THICKET = new PhosphoricThicketFeature(BranchingPlantFeatureConfig.CODEC);
    public static final Feature<BranchingPlantFeatureConfig> WORMWOOD_BRAMBLE = new WormwoodBrambleFeature(BranchingPlantFeatureConfig.CODEC);
    public static final GiantFlowerFeature GIANT_FLOWER = new GiantFlowerFeature(BranchingPlantFeatureConfig.CODEC);
    public static final GiantTreelikeFeature GIANT_TREELIKE = new GiantTreelikeFeature(GiantTreelikeFeatureConfig.CODEC);

    public static void register() {
        Registry.register(Registries.FEATURE, AstralScience.id("nephrum_patch"), NEPHRUM_PATCH);
        Registry.register(Registries.FEATURE, AstralScience.id("ghost_vine"), GHOST_VINE);
        Registry.register(Registries.FEATURE, AstralScience.id("firecap_patch"), FIRECAP_PATCH);
        Registry.register(Registries.FEATURE, AstralScience.id("glowing_mycelium"), GLOWING_MYCELIUM);
        Registry.register(Registries.FEATURE, AstralScience.id("spear_fern"), SPEAR_FERN);
        Registry.register(Registries.FEATURE, AstralScience.id("frostfur"), FROSTFUR);
        Registry.register(Registries.FEATURE, AstralScience.id("cup_lily"), CUP_LILY);
        Registry.register(Registries.FEATURE, AstralScience.id("angler_kelp"), ANGLER_KELP_FEATURE);
        Registry.register(Registries.FEATURE, AstralScience.id("phosphoric_thicket"), PHOSPHORIC_THICKET);
        Registry.register(Registries.FEATURE, AstralScience.id("giant_firecap"), GIANT_FIRECAP);
        Registry.register(Registries.FEATURE, AstralScience.id("wormwood_bramble"), WORMWOOD_BRAMBLE);
        Registry.register(Registries.FEATURE, AstralScience.id("giant_flower"), GIANT_FLOWER);
        Registry.register(Registries.FEATURE, AstralScience.id("giant_treelike"), GIANT_TREELIKE);
    }

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

    public static BlockPos findNextFloor(FeatureContext fc) {
        return findNextFloor(fc.getWorld(), fc.getOrigin());
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
