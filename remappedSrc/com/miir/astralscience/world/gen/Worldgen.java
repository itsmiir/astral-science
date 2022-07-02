package com.miir.astralscience.world.gen;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.world.biome.AstralScienceBiomes;
import com.miir.astralscience.world.gen.surfacebuilder.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public abstract class Worldgen {
    public static void register() {
        AstralSurfaceBuilder.DEBUG = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("debug"), new DebugSurfaceBuilder());
        AstralSurfaceBuilder.ROCKY_SURFACE_BUILDER = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("rocky"), new RockySurfaceBuilder());
        AstralSurfaceBuilder.LIMESTONE_MOUNTAIN = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("limestone_mountain"), new LimestoneMountainSurfaceBuilder());
        AstralSurfaceBuilder.SHALE_MOUNTAIN = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("shale_mountain"), new LimestoneMountainSurfaceBuilder());
        AstralSurfaceBuilder.PUMICE_MOUNTAIN = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("pumice_mountain"), new LimestoneMountainSurfaceBuilder());
        AstralSurfaceBuilder.SLATE_MOUNTAIN = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("slate_mountain"), new LimestoneMountainSurfaceBuilder());
        AstralSurfaceBuilder.BASALT_MOUNTAIN = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("basalt_mountain"), new LimestoneMountainSurfaceBuilder());
        AstralSurfaceBuilder.VOLCANIC_MOUNTAIN = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("volcanic"), new VolcanicMountainSurfaceBuilder());
        AstralSurfaceBuilder.ICE_SHEET = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("ice_sheet"), new IceSheetSurfaceBuilder());
        AstralSurfaceBuilder.SNOWDRIFT = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("snowdrift"), new SnowdriftSurfaceBuilder());
        AstralSurfaceBuilder.PHOSPHOR = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("phosphor"), new PhosphorSurfaceBuilder());
        AstralSurfaceBuilder.PHOSPHOR_LAKE = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("phosphor_lake"), new PhosphorLakeSurfaceBuilder());

        AstralSurfaceBuilder.SYLENE = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("sylene"), new SyleneSurfaceBuilder());
        AstralSurfaceBuilder.CYRI = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("cyri"), new CyriCurfaceBuilder());
        AstralSurfaceBuilder.PSIDON = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("psidon"), new PsidonSurfaceBuilder());
        AstralSurfaceBuilder.FIRECAP_FOREST = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("firecap_forest"), new FirecapForestSurfaceBuilder());

        AstralSurfaceBuilder.OMEIA = Registry.register(Registry.SURFACE_BUILDER, AstralScience.id("omeia"), new OmeiaSurfaceBuilder(TernarySurfaceConfig.CODEC));
        AstralSurfaceBuilder.OMEIA_SURFACE_CONFIGURED = AstralSurfaceBuilder.OMEIA
                .withConfig(new TernarySurfaceConfig(
                        Blocks.GRASS_BLOCK.getDefaultState(),
                        Blocks.DIRT.getDefaultState(),
                        Blocks.GRAVEL.getDefaultState()));
        Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, AstralScience.id("omeia_greenbelt"), AstralSurfaceBuilder.OMEIA_SURFACE_CONFIGURED);
//        AstralScience.OMEIA_BIOME_SOURCE = Registry.register(Registry.BIOME_SOURCE, AstralScience.id("omeia"), new OmeiaBiomeSource(AstralScience.OMEIA_GREENBELT));
        AstralScienceBiomes.OMEIA_GREENBELT = AstralScienceBiomes.createOmeiaGreenbelt();
        Registry.register(BuiltinRegistries.BIOME, AstralScience.id("omeia_greenbelt"), AstralScienceBiomes.OMEIA_GREENBELT);
//        OverworldBiomes.addContinentalBiome(AstralScience.GREENBELT_KEY, OverworldClimate.COOL, 2D);
    }
}
