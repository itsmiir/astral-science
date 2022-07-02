package com.miir.astralscience.world.gen.surfacebuilder;

import com.miir.astralscience.block.AstralScienceBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class AstralScienceSurfaceBuilder<C extends SurfaceConfig> {
    private static final BlockState RED = Blocks.RED_CONCRETE.getDefaultState();
    private static final BlockState ORANGE = Blocks.ORANGE_CONCRETE.getDefaultState();
    private static final BlockState YELLOW = Blocks.YELLOW_CONCRETE.getDefaultState();
    private static final BlockState LIME = Blocks.LIME_CONCRETE.getDefaultState();
    private static final BlockState GREEN = Blocks.GREEN_CONCRETE.getDefaultState();
    private static final BlockState CYAN = Blocks.CYAN_CONCRETE.getDefaultState();
    private static final BlockState LIGHT_BLUE = Blocks.LIGHT_BLUE_CONCRETE.getDefaultState();
    private static final BlockState BLUE = Blocks.BLUE_CONCRETE.getDefaultState();
    private static final BlockState PURPLE = Blocks.PURPLE_CONCRETE.getDefaultState();
    private static final BlockState PINK = Blocks.PINK_CONCRETE.getDefaultState();
    private static final BlockState MAGENTA = Blocks.MAGENTA_CONCRETE.getDefaultState();
    private static final BlockState BROWN = Blocks.BROWN_CONCRETE.getDefaultState();
    private static final BlockState WHITE = Blocks.WHITE_CONCRETE.getDefaultState();
    private static final BlockState LIGHT_GRAY = Blocks.LIGHT_GRAY_CONCRETE.getDefaultState();
    private static final BlockState GRAY = Blocks.GRAY_CONCRETE.getDefaultState();
    private static final BlockState BLACK = Blocks.BLACK_CONCRETE.getDefaultState();

    //    debug configs
    public static final TernarySurfaceConfig RED_CONFIG = new TernarySurfaceConfig(RED, RED, RED);
    public static final TernarySurfaceConfig ORANGE_CONFIG = new TernarySurfaceConfig(ORANGE, ORANGE, ORANGE);
    public static final TernarySurfaceConfig YELLOW_CONFIG = new TernarySurfaceConfig(YELLOW, YELLOW, YELLOW);
    public static final TernarySurfaceConfig LIME_CONFIG = new TernarySurfaceConfig(LIME, LIME, LIME);
    public static final TernarySurfaceConfig GREEN_CONFIG = new TernarySurfaceConfig(GREEN, GREEN, GREEN);
    public static final TernarySurfaceConfig CYAN_CONFIG = new TernarySurfaceConfig(CYAN, CYAN, CYAN);
    public static final TernarySurfaceConfig LIGHT_BLUE_CONFIG = new TernarySurfaceConfig(LIGHT_BLUE, LIGHT_BLUE, LIGHT_BLUE);
    public static final TernarySurfaceConfig BLUE_CONFIG = new TernarySurfaceConfig(BLUE, BLUE, BLUE);
    public static final TernarySurfaceConfig PURPLE_CONFIG = new TernarySurfaceConfig(PURPLE, PURPLE, PURPLE);
    public static final TernarySurfaceConfig MAGENTA_CONFIG = new TernarySurfaceConfig(MAGENTA, MAGENTA, MAGENTA);
    public static final TernarySurfaceConfig PINK_CONFIG = new TernarySurfaceConfig(PINK, PINK, PINK);
    public static final TernarySurfaceConfig BROWN_CONFIG = new TernarySurfaceConfig(BROWN, BROWN, BROWN);
    public static final TernarySurfaceConfig WHITE_CONFIG = new TernarySurfaceConfig(WHITE, WHITE, WHITE);
    public static final TernarySurfaceConfig LIGHT_GRAY_CONFIG = new TernarySurfaceConfig(LIGHT_GRAY, LIGHT_GRAY, LIGHT_GRAY);
    public static final TernarySurfaceConfig GRAY_CONFIG = new TernarySurfaceConfig(GRAY, GRAY, GRAY);
    public static final TernarySurfaceConfig BLACK_CONFIG = new TernarySurfaceConfig(BLACK, BLACK, BLACK);



    private static final BlockState LIMESTONE = AstralScienceBlocks.LIMESTONE.getDefaultState();
    private static final BlockState PUMICE = AstralScienceBlocks.PUMICE.getDefaultState();
    private static final BlockState SLATE = AstralScienceBlocks.SLATE.getDefaultState();
    private static final BlockState SHALE = AstralScienceBlocks.SHALE.getDefaultState();
    private static final BlockState IRON_RICH_BASALT = AstralScienceBlocks.IRON_RICH_BASALT.getDefaultState();
    private static final BlockState MAGMATIC_BLACKSTONE = AstralScienceBlocks.MAGMATIC_FRESH_BLACKSTONE.getDefaultState();
    private static final BlockState BLACKSTONE = AstralScienceBlocks.FRESH_BLACKSTONE.getDefaultState();
    private static final BlockState ANCIENT_ICE = AstralScienceBlocks.ANCIENT_ICE.getDefaultState();

    private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
    private static final BlockState AIR = Blocks.VOID_AIR.getDefaultState();
    private static final BlockState BLUE_ICE = Blocks.BLUE_ICE.getDefaultState();
    private static final BlockState ICE = Blocks.ICE.getDefaultState();
    private static final BlockState SNOW = Blocks.SNOW_BLOCK.getDefaultState();
    private static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
//    private static final BlockState POWDER_SNOW = Blocks.POWDER_SNOW.getDefaultState();
    private static final BlockState CLAY = Blocks.CLAY.getDefaultState();
    private static final BlockState GRASS = Blocks.GRASS_BLOCK.getDefaultState();
    private static final BlockState DIRT = Blocks.DIRT.getDefaultState();
    private static final BlockState COARSE_DIRT = Blocks.COARSE_DIRT.getDefaultState();

    public static final TernarySurfaceConfig LIMESTONE_CONFIG = new TernarySurfaceConfig(LIMESTONE, LIMESTONE, GRAVEL);
    public static final TernarySurfaceConfig SHALE_CONFIG = new TernarySurfaceConfig(SHALE, SHALE, GRAVEL);
    public static final TernarySurfaceConfig SLATE_CONFIG = new TernarySurfaceConfig(SLATE, SLATE, GRAVEL);
    public static final TernarySurfaceConfig PUMICE_CONFIG = new TernarySurfaceConfig(PUMICE, PUMICE, GRAVEL);
    public static final TernarySurfaceConfig IRON_RICH_BASALT_CONFIG = new TernarySurfaceConfig(IRON_RICH_BASALT, IRON_RICH_BASALT, GRAVEL);
    public static final TernarySurfaceConfig BLACKSTONE_CONFIG = new TernarySurfaceConfig(BLACKSTONE, BLACKSTONE, GRAVEL);
    public static final TernarySurfaceConfig MAGMATIC_BLACKSTONE_CONFIG = new TernarySurfaceConfig(MAGMATIC_BLACKSTONE, MAGMATIC_BLACKSTONE, GRAVEL);
    public static final TernarySurfaceConfig CARVING_CONFIG = new TernarySurfaceConfig(AIR, AIR, AIR);
    public static final TernarySurfaceConfig ICE_SHEET_CONFIG = new TernarySurfaceConfig(BLUE_ICE, ANCIENT_ICE, BLUE_ICE);
    public static final TernarySurfaceConfig SNOW_CONFIG = new TernarySurfaceConfig(SNOW, PACKED_ICE, PACKED_ICE);
    public static final TernarySurfaceConfig POWDER_SNOW_CONFIG = new TernarySurfaceConfig(SNOW, SNOW, SNOW);
    public static final TernarySurfaceConfig OMEIA_GREENBELT_CONFIG = new TernarySurfaceConfig(GRASS, DIRT, CLAY);
    public static final TernarySurfaceConfig OMEIA_BROWNBELT_CONFIG = new TernarySurfaceConfig(COARSE_DIRT, CLAY, CLAY);
    public static final TernarySurfaceConfig OMEIA_CAP_CONFIG = SNOW_CONFIG;

}
