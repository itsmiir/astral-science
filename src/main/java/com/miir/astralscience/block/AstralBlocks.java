package com.miir.astralscience.block;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.block.entity.CascadicCoolerBlockEntity;
import com.miir.astralscience.block.entity.CascadicHeaterBlockEntity;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

public class AstralBlocks {
    private static final Object2ObjectArrayMap<Block, String> BLOCKS = new Object2ObjectArrayMap<>();

    //    ore blocks
    public static final Block CASCADIUM_BLOCK = new Block(FabricBlockSettings.create().strength(10.0F, 20.0F).sounds(BlockSoundGroup.GLASS).requiresTool());
    public static final Block CASCADIC_BONE = new PillarBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.BONE).strength(1.5F, 12.0F).requiresTool());
    public static final Block CASCADIUM_ORE = new Block(FabricBlockSettings.create().strength(3.0F, 3.0F).sounds(BlockSoundGroup.STONE).requiresTool());

    public static final Block NEPHRYLL_BLOCK = new Block(FabricBlockSettings.create().strength(7.5F, 17.0F).sounds(BlockSoundGroup.METAL).requiresTool());

    //    machine blocks
    public static final Block MACHINE_CHASSIS = new Block(FabricBlockSettings.create().hardness(5.0F).sounds(BlockSoundGroup.NETHERITE).nonOpaque().requiresTool());

    public static final Block CASCADIC_HEATER = new CascadicHeaterBlock(FabricBlockSettings.create().nonOpaque().sounds(BlockSoundGroup.NETHERITE).requiresTool().strength(1.5F, 6.0F).requiresTool());
    public static BlockEntityType<CascadicHeaterBlockEntity> CASCADIC_HEATER_TYPE;

    public static final Block CASCADIC_COOLER = new CascadicCoolerBlock(FabricBlockSettings.create().requiresTool().nonOpaque().sounds(BlockSoundGroup.NETHERITE).requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static BlockEntityType<CascadicCoolerBlockEntity> CASCADIC_COOLER_TYPE;

    public static final Block STARLIGHT_COLLECTOR = new StarlightCollectorBlock(FabricBlockSettings.create().requiresTool().strength(1.5f, 6.0f).requiresTool().pistonBehavior(PistonBehavior.BLOCK).sounds(BlockSoundGroup.STONE).nonOpaque().luminance((state) -> state.get(StarlightCollectorBlock.LIT) ? 14 : 0));

    public static final Block STARSHIP_HELM = new StarshipHelmBlock(FabricBlockSettings.create().nonOpaque().requiresTool().sounds(BlockSoundGroup.NETHERITE).strength(1.5F, 6.0F));

    //    natural blocks
    public static final Block ANCIENT_ICE = new IceBlock(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE).pistonBehavior(PistonBehavior.DESTROY));
    public static final Block LIMESTONE = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static final Block SHALE = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static final Block SLATE = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static final Block PUMICE = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static final Block IRON_RICH_BASALT = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static final Block FRESH_BLACKSTONE = new Block(FabricBlockSettings.create().requiresTool().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static final Block MAGMATIC_FRESH_BLACKSTONE = new MagmaBlock(FabricBlockSettings.create().requiresTool().strength(1.0F, 4.5F).sounds(BlockSoundGroup.STONE).luminance((state) -> 5).emissiveLighting((state, world, pos) -> true));
    public static final Block REGOLITH = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static final Block SYLIUM = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static final Block STALE_CHEESE = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().burnable().sounds(BlockSoundGroup.FUNGUS));

    public static final Block PHOSPHORITE = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));

    public static final Block BLACK_SAND = new SandBlock(0, FabricBlockSettings.create().mapColor(MapColor.BLACK).strength(0.5F).sounds(BlockSoundGroup.SAND));

    public static final Block PSIONIC_SAND = new SandBlock(8078591, FabricBlockSettings.create().mapColor(MapColor.PURPLE).strength(0.5F).sounds(BlockSoundGroup.SAND));

    //    produced blocks
    public static final Block POLISHED_LIMESTONE = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static final Block IRON_RICH_BASALT_BRICKS = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static final Block POLISHED_SHALE = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static final Block POLISHED_SLATE = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));
    public static final Block POLISHED_PUMICE = new Block(FabricBlockSettings.create().requiresTool().strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE));


    //    plants
    public static final Block GIANT_LEAVES = new Block(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).requiresTool().strength(0.2F).burnable().sounds(BlockSoundGroup.GRASS));
    public static final Block GIANT_STEM = new Block(FabricBlockSettings.create().requiresTool().strength(0.2F).sounds(BlockSoundGroup.STEM).burnable());

    public static final Block RED_PETAL = new Block(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).strength(0.2F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block CYAN_PETAL = new Block(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).strength(0.2F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block BLUE_PETAL = new Block(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).strength(0.2F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block YELLOW_PETAL = new Block(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).strength(0.2F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block MAGENTA_PETAL = new Block(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).strength(0.2F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block LAVENDER_PETAL = new Block(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).strength(0.2F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block MINT_PETAL = new Block(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).strength(0.2F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block ORANGE_PETAL = new Block(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).strength(0.2F).sounds(BlockSoundGroup.WOOL).burnable());



    public static final Block FROST_MYCELIUM = new FrostMyceliumBlock(FabricBlockSettings.create().requiresTool().hardness(0.1F).sounds(BlockSoundGroup.FUNGUS));
    public static final Block GHOST_VINES = new GhostVinesBlock(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.WHITE).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES).luminance((state) -> 12));
    public static final Block GHOST_VINES_PLANT = new GhostVinesPlantBlock(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.WHITE).noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES).luminance((state) -> 12));
    public static final Block FROSTFUR = new FrostfurBlock(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).mapColor(MapColor.WHITE).luminance((state) -> 6));
    public static final Block BLUEMOSS = new GlowLichenBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.PALE_PURPLE).noCollision().strength(0.2F).sounds(BlockSoundGroup.GLOW_LICHEN).luminance(GlowLichenBlock.getLuminanceSupplier(7)));
    public static final Block SPEAR_FERN = new SpearFernBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).burnable().mapColor(MapColor.PALE_YELLOW).strength(0.2f).sounds(BlockSoundGroup.FUNGUS));
    public static final Block FIRECAP = new FirecapPlantBlock(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).burnable().mapColor(MapColor.ORANGE).noCollision().breakInstantly().sounds(BlockSoundGroup.SHROOMLIGHT).luminance((state) -> 10));
    public static final Block FIRECAP_SCALES = new Block(FabricBlockSettings.create().sounds(BlockSoundGroup.FUNGUS).strength(0.2F).burnable().luminance((state) -> 10).mapColor(MapColor.PINK));
    public static final Block FIRECAP_GILLS = new Block(FabricBlockSettings.create().sounds(BlockSoundGroup.FUNGUS).strength(0.2F).burnable().mapColor(MapColor.LICHEN_GREEN));
    public static final Block FIRECAP_HYPHAE = new Block(FabricBlockSettings.create().sounds(BlockSoundGroup.FUNGUS).strength(0.2F).burnable().mapColor(MapColor.LICHEN_GREEN));

    public static final Block ANGLER_KELP = new AnglerKelpBlock(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).burnable().mapColor(MapColor.GREEN).sounds(BlockSoundGroup.GRASS).breakInstantly().luminance((state) -> 15).noCollision().emissiveLighting((state, world, pos) -> true));
    public static final Block ANGLER_KELP_PLANT = new AnglerKelpPlantBlock(FabricBlockSettings.create().mapColor(MapColor.GREEN).burnable().sounds(BlockSoundGroup.GRASS).breakInstantly().noCollision());
    public static final Block PINK_PETAL = new Block(FabricBlockSettings.create().burnable().pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.PINK).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block PURPLE_PETAL = new Block(FabricBlockSettings.create().burnable().pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.PURPLE).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block PEACH_PETAL = new Block(FabricBlockSettings.create().burnable().pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.DARK_DULL_PINK).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block BLACK_PETAL = new Block(FabricBlockSettings.create().burnable().pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.BLACK).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block WHITE_PETAL = new Block(FabricBlockSettings.create().burnable().pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.WHITE).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block NEPHRUM = new NephrumBlock(FabricBlockSettings.create().burnable().pistonBehavior(PistonBehavior.DESTROY).noCollision().mapColor(MapColor.GREEN).sounds(BlockSoundGroup.GRASS).breakInstantly().luminance(NephrumBlock::getLuminance).emissiveLighting((state, world, pos) -> NephrumBlock.getLuminance(state) > 0));
    public static final Block BRAMBLEWOOD = new PillarBlock(FabricBlockSettings.create().burnable().mapColor(MapColor.BLACK).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block BRAMBLEWOOD_LOG = new PillarBlock(FabricBlockSettings.create().burnable().mapColor((state) -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.BLACK : MapColor.LIGHT_GRAY).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block BRAMBLEWOOD_PLANKS = new Block(FabricBlockSettings.create().mapColor(MapColor.LICHEN_GREEN).burnable().strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block BRAMBLEWOOD_DOOR = new AstralDoorBlock(FabricBlockSettings.create().burnable().mapColor(MapColor.LICHEN_GREEN).strength(3.0F).sounds(BlockSoundGroup.WOOD));


    public static final Block WORMWOOD = new PillarBlock(FabricBlockSettings.create().burnable().strength(1.5f, 6.0f).sounds(BlockSoundGroup.WOOD));
    public static final Block WORMWOOD_LOG = new PillarBlock(FabricBlockSettings.create().burnable().strength(1.5f, 6.0f).sounds(BlockSoundGroup.WOOD));


    private static void registerSimpleBlock(Block block, String path) {
        Registry.register(Registries.BLOCK, AstralScience.id(path), block);
        Registry.register(Registries.ITEM, AstralScience.id(path), new BlockItem(block, new Item.Settings()));
    }


    public static void register() {
//        BLOCKS.put(CASCADIUM_BLOCK, "cascadium_block");
//        BLOCKS.put(CASCADIUM_ORE, "cascadium_ore");
//        BLOCKS.put(CASCADIC_BONE, "cascadic_bone");
//        BLOCKS.put(NEPHRYLL_BLOCK, "nephryll_block");
//        BLOCKS.put(MACHINE_CHASSIS, "machine_chassis");
//        BLOCKS.put(CASCADIC_HEATER, "cascadic_heater");
//        BLOCKS.put(CASCADIC_COOLER, "cascadic_cooler");
//        BLOCKS.put(STARLIGHT_COLLECTOR, "starlight_collector");
//        BLOCKS.put(STARSHIP_HELM, "starship_helm");
//        BLOCKS.put(ANCIENT_ICE, "ancient_ice");
//        BLOCKS.put(LIMESTONE, "limestone");
//        BLOCKS.put(POLISHED_LIMESTONE, "polished_limestone");
//        BLOCKS.put(SHALE, "shale");
//        BLOCKS.put(POLISHED_SHALE, "polished_shale");
//        BLOCKS.put(SLATE, "slate");
//        BLOCKS.put(POLISHED_SLATE, "polished_slate");
//        BLOCKS.put(PUMICE, "pumice");
//        BLOCKS.put(POLISHED_PUMICE, "polished_pumice");
//        BLOCKS.put(IRON_RICH_BASALT, "iron_rich_basalt");
//        BLOCKS.put(IRON_RICH_BASALT_BRICKS, "iron_rich_basalt_bricks");
//        BLOCKS.put(MAGMATIC_FRESH_BLACKSTONE, "magmatic_fresh_blackstone");
//        BLOCKS.put(REGOLITH, "regolith");
//        BLOCKS.put(SYLIUM, "sylium");
//        BLOCKS.put(PHOSPHORITE, "phosphorite");
//        BLOCKS.put(BLACK_SAND, "black_sand");
//        BLOCKS.put(PSIONIC_SAND, "psionic_sand");
//        BLOCKS.put(FROST_MYCELIUM,"frost_mycelium");


//        for (Block block :
//                BLOCKS.keySet()) {
//            registerSimpleBlock(block, BLOCKS.get(block));
//        }
//        ore blocks
        Registry.register(Registries.BLOCK, AstralScience.id("cascadium_block"), CASCADIUM_BLOCK);
        Registry.register(Registries.ITEM, AstralScience.id("cascadium_block"), new BlockItem(CASCADIUM_BLOCK, new Item.Settings().rarity(Rarity.UNCOMMON)));
        Registry.register(Registries.BLOCK, AstralScience.id("cascadium_ore"), CASCADIUM_ORE);
        Registry.register(Registries.ITEM, AstralScience.id("cascadium_ore"), new BlockItem(CASCADIUM_ORE, new Item.Settings().rarity(Rarity.UNCOMMON)));
        Registry.register(Registries.BLOCK, AstralScience.id("cascadic_bone"), CASCADIC_BONE);
        Registry.register(Registries.ITEM, AstralScience.id("cascadic_bone"), new BlockItem(CASCADIC_BONE, new Item.Settings().rarity(Rarity.UNCOMMON)));

        Registry.register(Registries.BLOCK, AstralScience.id("nephryll_block"), NEPHRYLL_BLOCK);
        Registry.register(Registries.ITEM, AstralScience.id("nephryll_block"), new BlockItem(NEPHRYLL_BLOCK, new Item.Settings().rarity(Rarity.UNCOMMON)));
//        machine blocks
        Registry.register(Registries.BLOCK, AstralScience.id("machine_chassis"), MACHINE_CHASSIS);
        Registry.register(Registries.ITEM, AstralScience.id("machine_chassis"), new BlockItem(MACHINE_CHASSIS, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("cascadic_heater"), CASCADIC_HEATER);
        Registry.register(Registries.ITEM, AstralScience.id("cascadic_heater"), new BlockItem(CASCADIC_HEATER, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("cascadic_cooler"), CASCADIC_COOLER);
        Registry.register(Registries.ITEM, AstralScience.id("cascadic_cooler"), new BlockItem(CASCADIC_COOLER, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("starlight_collector"), STARLIGHT_COLLECTOR);
        Registry.register(Registries.ITEM, AstralScience.id("starlight_collector"), new BlockItem(STARLIGHT_COLLECTOR, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("starship_helm"), STARSHIP_HELM);
        Registry.register(Registries.ITEM, AstralScience.id("starship_helm"), new BlockItem(STARSHIP_HELM, new Item.Settings().rarity(Rarity.COMMON)));
//        natural blocks
        Registry.register(Registries.BLOCK, AstralScience.id("ancient_ice"), ANCIENT_ICE);
        Registry.register(Registries.ITEM, AstralScience.id("ancient_ice"), new BlockItem(ANCIENT_ICE, new Item.Settings()));
//        rocks
        Registry.register(Registries.BLOCK, AstralScience.id("limestone"), LIMESTONE);
        Registry.register(Registries.ITEM, AstralScience.id("limestone"), new BlockItem(LIMESTONE, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("shale"), SHALE);
        Registry.register(Registries.ITEM, AstralScience.id("shale"), new BlockItem(SHALE, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("slate"), SLATE);
        Registry.register(Registries.ITEM, AstralScience.id("slate"), new BlockItem(SLATE, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("pumice"), PUMICE);
        Registry.register(Registries.ITEM, AstralScience.id("pumice"), new BlockItem(PUMICE, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("iron_rich_basalt"), IRON_RICH_BASALT);
        Registry.register(Registries.ITEM, AstralScience.id("iron_rich_basalt"), new BlockItem(IRON_RICH_BASALT, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("fresh_blackstone"), FRESH_BLACKSTONE);
        Registry.register(Registries.ITEM, AstralScience.id("fresh_blackstone"), new BlockItem(FRESH_BLACKSTONE, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("magmatic_fresh_blackstone"), MAGMATIC_FRESH_BLACKSTONE);
        Registry.register(Registries.ITEM, AstralScience.id("magmatic_fresh_blackstone"), new BlockItem(MAGMATIC_FRESH_BLACKSTONE, new Item.Settings()));

        Registry.register(Registries.BLOCK, new Identifier(AstralScience.MOD_ID, "regolith"), REGOLITH);
        Registry.register(Registries.ITEM, new Identifier(AstralScience.MOD_ID, "regolith"), new BlockItem(REGOLITH, new Item.Settings()));
        Registry.register(Registries.BLOCK, new Identifier(AstralScience.MOD_ID, "sylium"), SYLIUM);
        Registry.register(Registries.ITEM, new Identifier(AstralScience.MOD_ID, "sylium"), new BlockItem(SYLIUM, new Item.Settings()));

        Registry.register(Registries.BLOCK, AstralScience.id("phosphorite"), PHOSPHORITE);
        Registry.register(Registries.ITEM, AstralScience.id("phosphorite"), new BlockItem(PHOSPHORITE, new Item.Settings()));

        Registry.register(Registries.BLOCK, AstralScience.id("black_sand"), BLACK_SAND);
        Registry.register(Registries.ITEM, AstralScience.id("black_sand"), new BlockItem(BLACK_SAND, new Item.Settings()));

        Registry.register(Registries.BLOCK, AstralScience.id("psionic_sand"), PSIONIC_SAND);
        Registry.register(Registries.ITEM, AstralScience.id("psionic_sand"), new BlockItem(PSIONIC_SAND, new Item.Settings()));
//        produced blocks
        Registry.register(Registries.BLOCK, AstralScience.id("polished_limestone"), POLISHED_LIMESTONE);
        Registry.register(Registries.ITEM, AstralScience.id("polished_limestone"), new BlockItem(POLISHED_LIMESTONE, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("polished_slate"), POLISHED_SLATE);
        Registry.register(Registries.ITEM, AstralScience.id("polished_slate"), new BlockItem(POLISHED_SLATE, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("polished_shale"), POLISHED_SHALE);
        Registry.register(Registries.ITEM, AstralScience.id("polished_shale"), new BlockItem(POLISHED_SHALE, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("polished_pumice"), POLISHED_PUMICE);
        Registry.register(Registries.ITEM, AstralScience.id("polished_pumice"), new BlockItem(POLISHED_PUMICE, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("iron_rich_basalt_bricks"), IRON_RICH_BASALT_BRICKS);
        Registry.register(Registries.ITEM, AstralScience.id("iron_rich_basalt_bricks"), new BlockItem(IRON_RICH_BASALT_BRICKS, new Item.Settings()));

        Registry.register(Registries.BLOCK, AstralScience.id("frost_mycelium"), FROST_MYCELIUM);
        Registry.register(Registries.ITEM, AstralScience.id("frost_mycelium"), new BlockItem(FROST_MYCELIUM, new Item.Settings()));

//        plants
        Registry.register(Registries.BLOCK, AstralScience.id("giant_leaves"), GIANT_LEAVES);
        Registry.register(Registries.ITEM, AstralScience.id("giant_leaves"), new BlockItem(GIANT_LEAVES, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("giant_stem"), GIANT_STEM);
        Registry.register(Registries.ITEM, AstralScience.id("giant_stem"), new BlockItem(GIANT_STEM, new Item.Settings()));

        Registry.register(Registries.BLOCK, AstralScience.id("red_petal"), RED_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("red_petal"), new BlockItem(RED_PETAL, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("cyan_petal"), CYAN_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("cyan_petal"), new BlockItem(CYAN_PETAL, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("yellow_petal"), YELLOW_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("yellow_petal"), new BlockItem(YELLOW_PETAL, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("black_petal"), BLACK_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("black_petal"), new BlockItem(BLACK_PETAL, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("white_petal"), WHITE_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("white_petal"), new BlockItem(WHITE_PETAL, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("blue_petal"), BLUE_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("blue_petal"), new BlockItem(BLUE_PETAL, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("magenta_petal"), MAGENTA_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("magenta_petal"), new BlockItem(MAGENTA_PETAL, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("pink_petal"), PINK_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("pink_petal"), new BlockItem(PINK_PETAL, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("peach_petal"), PEACH_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("peach_petal"), new BlockItem(PEACH_PETAL, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("purple_petal"), PURPLE_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("purple_petal"), new BlockItem(PURPLE_PETAL, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("mint_petal"), MINT_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("mint_petal"), new BlockItem(MINT_PETAL, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("lavender_petal"), LAVENDER_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("lavender_petal"), new BlockItem(LAVENDER_PETAL, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("orange_petal"), ORANGE_PETAL);
        Registry.register(Registries.ITEM, AstralScience.id("orange_petal"), new BlockItem(ORANGE_PETAL, new Item.Settings()));

        Registry.register(Registries.BLOCK, AstralScience.id("wormwood"), WORMWOOD);
        Registry.register(Registries.ITEM, AstralScience.id("wormwood"), new BlockItem(WORMWOOD, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("wormwood_log"), WORMWOOD_LOG);
        Registry.register(Registries.ITEM, AstralScience.id("wormwood_log"), new BlockItem(WORMWOOD_LOG, new Item.Settings()));

        Registry.register(Registries.BLOCK, AstralScience.id("bramblewood"), BRAMBLEWOOD);
        Registry.register(Registries.ITEM, AstralScience.id("bramblewood"), new BlockItem(BRAMBLEWOOD, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("bramblewood_log"), BRAMBLEWOOD_LOG);
        Registry.register(Registries.ITEM, AstralScience.id("bramblewood_log"), new BlockItem(BRAMBLEWOOD_LOG, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("bramblewood_planks"), BRAMBLEWOOD_PLANKS);
        Registry.register(Registries.ITEM, AstralScience.id("bramblewood_planks"), new BlockItem(BRAMBLEWOOD_PLANKS, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("bramblewood_door"), BRAMBLEWOOD_DOOR);
        Registry.register(Registries.ITEM, AstralScience.id("bramblewood_door"), new BlockItem(BRAMBLEWOOD_DOOR, new Item.Settings()));

        Registry.register(Registries.BLOCK, AstralScience.id("nephrum"), NEPHRUM);

        Registry.register(Registries.BLOCK, AstralScience.id("ghost_vines"), GHOST_VINES);
        Registry.register(Registries.ITEM, AstralScience.id("ghost_vines"), new BlockItem(GHOST_VINES, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("ghost_vines_plant"), GHOST_VINES_PLANT);
        Registry.register(Registries.BLOCK, AstralScience.id("spear_fern"), SPEAR_FERN);
        Registry.register(Registries.ITEM, AstralScience.id("spear_fern"), new BlockItem(SPEAR_FERN, new Item.Settings()));

        Registry.register(Registries.BLOCK, AstralScience.id("firecap"), FIRECAP);
        Registry.register(Registries.ITEM, AstralScience.id("firecap"), new BlockItem(FIRECAP, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("firecap_scales"), FIRECAP_SCALES);
        Registry.register(Registries.ITEM, AstralScience.id("firecap_scales"), new BlockItem(FIRECAP_SCALES, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("firecap_gills"), FIRECAP_GILLS);
        Registry.register(Registries.ITEM, AstralScience.id("firecap_gills"), new BlockItem(FIRECAP_GILLS, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("firecap_hyphae"), FIRECAP_HYPHAE);
        Registry.register(Registries.ITEM, AstralScience.id("firecap_hyphae"), new BlockItem(FIRECAP_HYPHAE, new Item.Settings()));

        Registry.register(Registries.BLOCK, AstralScience.id("frostfur"), FROSTFUR);
        Registry.register(Registries.ITEM, AstralScience.id("frostfur"), new BlockItem(FROSTFUR, new Item.Settings()));

        Registry.register(Registries.BLOCK, AstralScience.id("bluemoss"), BLUEMOSS);
        Registry.register(Registries.ITEM, AstralScience.id("bluemoss"), new BlockItem(BLUEMOSS, new Item.Settings()));

        Registry.register(Registries.BLOCK, AstralScience.id("angler_kelp"), ANGLER_KELP);
        Registry.register(Registries.ITEM, AstralScience.id("angler_kelp"), new BlockItem(ANGLER_KELP, new Item.Settings()));
        Registry.register(Registries.BLOCK, AstralScience.id("angler_kelp_plant"), ANGLER_KELP_PLANT);

        Registry.register(Registries.BLOCK, AstralScience.id("stale_cheese"), STALE_CHEESE);
        Registry.register(Registries.ITEM, AstralScience.id("stale_cheese"), new BlockItem(STALE_CHEESE, new Item.Settings()));



//        block entities
        CASCADIC_COOLER_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, AstralScience.id("cascadic_cooler"), FabricBlockEntityTypeBuilder.create(CascadicCoolerBlockEntity::new, CASCADIC_COOLER).build());
        CASCADIC_HEATER_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, AstralScience.id("cascadic_heater"), FabricBlockEntityTypeBuilder.create(CascadicHeaterBlockEntity::new, CASCADIC_HEATER).build());
    }

}
