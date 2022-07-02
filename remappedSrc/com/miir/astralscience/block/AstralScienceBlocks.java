package com.miir.astralscience.block;

import com.miir.astralscience.AstralScience;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class AstralScienceBlocks {
    private static final Material METAL_CLONE = new FabricMaterialBuilder(MaterialColor.GRAY).build();
    //    ore blocks
    public static final Block CASCADIUM_BLOCK = new Block(FabricBlockSettings.of(METAL_CLONE).strength(10.0F, 20.0F).sounds(BlockSoundGroup.GLASS).breakByHand(false));
    public static final Block CASCADIC_BONE = new PillarBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BONE).strength(1.5F, 12.0F).breakByHand(false));
    public static final Block CASCADIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE).strength(3.0F, 3.0F).sounds(BlockSoundGroup.STONE).breakByHand(false));

    public static final Block NEPHRYLL_BLOCK = new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).strength(7.5F, 17.0F).sounds(BlockSoundGroup.METAL).breakByHand(false));

    //    machine blocks
    public static final Block MACHINE_CHASSIS = new Block(FabricBlockSettings.of(METAL_CLONE).hardness(5.0F).sounds(BlockSoundGroup.NETHERITE).nonOpaque().breakByHand(false));
    public static final Block CASCADIC_HEATER = new CascadicHeaterBlock(FabricBlockSettings.of(METAL_CLONE).breakByHand(false).nonOpaque().sounds(BlockSoundGroup.NETHERITE).hardness(5.0F));
    public static final Block CASCADIC_COOLER = new CascadicCoolerBlock(FabricBlockSettings.of(METAL_CLONE).breakByHand(false).nonOpaque().sounds(BlockSoundGroup.NETHERITE).hardness(5.0F));

    public static final Block STARSHIP_CONSTRUCTION_BLOCK = new StarshipConstructionBlock(FabricBlockSettings.of(METAL_CLONE).breakByHand(false).nonOpaque().sounds(BlockSoundGroup.NETHERITE).strength(1.5F, 6.0F));
//    public static final Block STARSHIP_HELM = new StarshipHelmBlock(FabricBlockSettings.of(METAL_CLONE).nonOpaque().breakByHand(false).sounds(BlockSoundGroup.NETHERITE).strength(1.5F, 6.0F));

    //    natural blocks
    public static final Block ANCIENT_ICE = new IceBlock(FabricBlockSettings.of(Material.ICE).breakByHand(true).hardness(2.0F).sounds(BlockSoundGroup.GLASS));
    public static final Block LIMESTONE = new Block(FabricBlockSettings.of(Material.STONE).breakByHand(false).strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE));
    public static final Block SHALE = new Block(FabricBlockSettings.of(Material.STONE).breakByHand(false).strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE));
    public static final Block SLATE = new Block(FabricBlockSettings.of(Material.STONE).breakByHand(false).strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE));
    public static final Block PUMICE = new Block(FabricBlockSettings.of(Material.STONE).breakByHand(false).strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE));
    public static final Block IRON_RICH_BASALT = new Block(FabricBlockSettings.of(Material.STONE).breakByHand(false).strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE));
    public static final Block FRESH_BLACKSTONE = new Block(FabricBlockSettings.of(Material.STONE).breakByHand(false).strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE));
    public static final Block MAGMATIC_FRESH_BLACKSTONE = new MagmaBlock(FabricBlockSettings.of(Material.STONE).breakByHand(false).strength(1.0F, 4.5F).sounds(BlockSoundGroup.STONE).lightLevel(3).luminance((state) -> 3));
    public static final Block REGOLITH = new Block(FabricBlockSettings.of(Material.STONE));

    //    produced blocks
    public static final Block POLISHED_LIMESTONE = new Block(FabricBlockSettings.of(Material.STONE).breakByHand(false).strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE));
    public static final Block IRON_RICH_BASALT_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).breakByHand(false).strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE));
    public static final Block POLISHED_SHALE = new Block(FabricBlockSettings.of(Material.STONE).breakByHand(false).strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE));
    public static final Block POLISHED_SLATE = new Block(FabricBlockSettings.of(Material.STONE).breakByHand(false).strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE));
    public static final Block POLISHED_PUMICE = new Block(FabricBlockSettings.of(Material.STONE).breakByHand(false).strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE));

    public static final Block PURPLE_MYCELIUM = new Block(FabricBlockSettings.of(Material.SOIL).breakByHand(false).hardness(0.1F).sounds(BlockSoundGroup.GRASS).velocityMultiplier(.75F));

    //    plants
    public static final Block GIANT_LEAVES = new Block(FabricBlockSettings.of(Material.LEAVES).breakByHand(false).strength(0.2F).sounds(BlockSoundGroup.GRASS));
    public static final Block GIANT_STEM = new Block(FabricBlockSettings.of(Material.WOOD).breakByHand(false).strength(0.2F).sounds(BlockSoundGroup.STEM));

    public static final Block RED_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block CYAN_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block BLUE_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block WHITE_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block BLACK_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block YELLOW_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block MAGENTA_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block LAVENDER_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block PINK_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block PURPLE_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block PEACH_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block MINT_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));
    public static final Block ORANGE_PETAL = new Block(FabricBlockSettings.of(Material.WOOL).strength(0.2F).sounds(BlockSoundGroup.WOOL));


    public static void register() {

//        ore blocks
        Registry.register(Registry.BLOCK, AstralScience.id("cascadium_block"), CASCADIUM_BLOCK);
        Registry.register(Registry.ITEM, AstralScience.id("cascadium_block"), new BlockItem(CASCADIUM_BLOCK, new Item.Settings().rarity(Rarity.UNCOMMON)));
        Registry.register(Registry.BLOCK, AstralScience.id("cascadium_ore"), CASCADIUM_ORE);
        Registry.register(Registry.ITEM, AstralScience.id("cascadium_ore"), new BlockItem(CASCADIUM_ORE, new Item.Settings().rarity(Rarity.UNCOMMON)));
        Registry.register(Registry.BLOCK, AstralScience.id("cascadic_bone"), CASCADIC_BONE);
        Registry.register(Registry.ITEM, AstralScience.id("cascadic_bone"), new BlockItem(CASCADIC_BONE, new Item.Settings().rarity(Rarity.UNCOMMON)));

        Registry.register(Registry.BLOCK, AstralScience.id("nephryll_block"), NEPHRYLL_BLOCK);
        Registry.register(Registry.ITEM, AstralScience.id("nephryll_block"), new BlockItem(NEPHRYLL_BLOCK, new Item.Settings().rarity(Rarity.UNCOMMON)));
//        machine blocks
        Registry.register(Registry.BLOCK, AstralScience.id("machine_chassis"), MACHINE_CHASSIS);
        Registry.register(Registry.ITEM, AstralScience.id("machine_chassis"), new BlockItem(MACHINE_CHASSIS, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("cascadic_heater"), CASCADIC_HEATER);
        Registry.register(Registry.ITEM, AstralScience.id("cascadic_heater"), new BlockItem(CASCADIC_HEATER, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("cascadic_cooler"), CASCADIC_COOLER);
        Registry.register(Registry.ITEM, AstralScience.id("cascadic_cooler"), new BlockItem(CASCADIC_COOLER, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("starship_construction_block"), STARSHIP_CONSTRUCTION_BLOCK);
        Registry.register(Registry.ITEM, AstralScience.id("starship_construction_block"), new BlockItem(STARSHIP_CONSTRUCTION_BLOCK, new Item.Settings().rarity(Rarity.COMMON)));
//        Registry.register(Registry.BLOCK, AstralScience.id("starship_helm"), STARSHIP_HELM);
//        Registry.register(Registry.ITEM, AstralScience.id("starship_helm"), new BlockItem(STARSHIP_HELM, new Item.Settings().rarity(Rarity.COMMON)));
//        natural blocks
        Registry.register(Registry.BLOCK, AstralScience.id("ancient_ice"), ANCIENT_ICE);
        Registry.register(Registry.ITEM, AstralScience.id("ancient_ice"), new BlockItem(ANCIENT_ICE, new Item.Settings()));
//        rocks
        Registry.register(Registry.BLOCK, AstralScience.id("limestone"), LIMESTONE);
        Registry.register(Registry.ITEM, AstralScience.id("limestone"), new BlockItem(LIMESTONE, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("shale"), SHALE);
        Registry.register(Registry.ITEM, AstralScience.id("shale"), new BlockItem(SHALE, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("slate"), SLATE);
        Registry.register(Registry.ITEM, AstralScience.id("slate"), new BlockItem(SLATE, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("pumice"), PUMICE);
        Registry.register(Registry.ITEM, AstralScience.id("pumice"), new BlockItem(PUMICE, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("iron_rich_basalt"), IRON_RICH_BASALT);
        Registry.register(Registry.ITEM, AstralScience.id("iron_rich_basalt"), new BlockItem(IRON_RICH_BASALT, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("fresh_blackstone"), FRESH_BLACKSTONE);
        Registry.register(Registry.ITEM, AstralScience.id("fresh_blackstone"), new BlockItem(FRESH_BLACKSTONE, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("magmatic_fresh_blackstone"), MAGMATIC_FRESH_BLACKSTONE);
        Registry.register(Registry.ITEM, AstralScience.id("magmatic_fresh_blackstone"), new BlockItem(MAGMATIC_FRESH_BLACKSTONE, new Item.Settings()));
        Registry.register(Registry.BLOCK, new Identifier(AstralScience.MOD_ID, "regolith"), REGOLITH);
        Registry.register(Registry.ITEM, new Identifier(AstralScience.MOD_ID, "regolith"), new BlockItem(REGOLITH, new Item.Settings()));
//        produced blocks
        Registry.register(Registry.BLOCK, AstralScience.id("polished_limestone"), POLISHED_LIMESTONE);
        Registry.register(Registry.ITEM, AstralScience.id("polished_limestone"), new BlockItem(POLISHED_LIMESTONE, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("polished_slate"), POLISHED_SLATE);
        Registry.register(Registry.ITEM, AstralScience.id("polished_slate"), new BlockItem(POLISHED_SLATE, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("polished_shale"), POLISHED_SHALE);
        Registry.register(Registry.ITEM, AstralScience.id("polished_shale"), new BlockItem(POLISHED_SHALE, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("polished_pumice"), POLISHED_PUMICE);
        Registry.register(Registry.ITEM, AstralScience.id("polished_pumice"), new BlockItem(POLISHED_PUMICE, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("iron_rich_basalt_bricks"), IRON_RICH_BASALT_BRICKS);
        Registry.register(Registry.ITEM, AstralScience.id("iron_rich_basalt_bricks"), new BlockItem(IRON_RICH_BASALT_BRICKS, new Item.Settings()));

        Registry.register(Registry.BLOCK, AstralScience.id("purple_mycelium"), PURPLE_MYCELIUM);
        Registry.register(Registry.ITEM, AstralScience.id("purple_mycelium"), new BlockItem(PURPLE_MYCELIUM, new Item.Settings()));

//        plants
        Registry.register(Registry.BLOCK, AstralScience.id("giant_leaves"), GIANT_LEAVES);
        Registry.register(Registry.ITEM, AstralScience.id("giant_leaves"), new BlockItem(GIANT_LEAVES, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("giant_stem"), GIANT_STEM);
        Registry.register(Registry.ITEM, AstralScience.id("giant_stem"), new BlockItem(GIANT_STEM, new Item.Settings()));

        Registry.register(Registry.BLOCK, AstralScience.id("red_petal"), RED_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("red_petal"), new BlockItem(RED_PETAL, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("cyan_petal"), CYAN_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("cyan_petal"), new BlockItem(CYAN_PETAL, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("yellow_petal"), YELLOW_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("yellow_petal"), new BlockItem(YELLOW_PETAL, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("black_petal"), BLACK_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("black_petal"), new BlockItem(BLACK_PETAL, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("white_petal"), WHITE_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("white_petal"), new BlockItem(WHITE_PETAL, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("blue_petal"), BLUE_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("blue_petal"), new BlockItem(BLUE_PETAL, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("magenta_petal"), MAGENTA_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("magenta_petal"), new BlockItem(MAGENTA_PETAL, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("pink_petal"), PINK_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("pink_petal"), new BlockItem(PINK_PETAL, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("peach_petal"), PEACH_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("peach_petal"), new BlockItem(PEACH_PETAL, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("purple_petal"), PURPLE_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("purple_petal"), new BlockItem(PURPLE_PETAL, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("mint_petal"), MINT_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("mint_petal"), new BlockItem(MINT_PETAL, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("lavender_petal"), LAVENDER_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("lavender_petal"), new BlockItem(LAVENDER_PETAL, new Item.Settings()));
        Registry.register(Registry.BLOCK, AstralScience.id("orange_petal"), ORANGE_PETAL);
        Registry.register(Registry.ITEM, AstralScience.id("orange_petal"), new BlockItem(ORANGE_PETAL, new Item.Settings()));


//        block entities
        AstralScience.CASCADIC_COOLER = Registry.register(Registry.BLOCK_ENTITY_TYPE, "ntrstlr:cascadic_cooler", FabricBlockEntityTypeBuilder.create(CascadicCoolerBlockEntity::new, CASCADIC_COOLER).build());
        AstralScience.CASCADIC_HEATER = Registry.register(Registry.BLOCK_ENTITY_TYPE, "ntrstlr:cascadic_heater", FabricBlockEntityTypeBuilder.create(CascadicHeaterBlockEntity::new, CASCADIC_HEATER).build());
//        AstralScience.STARSHIP_HELM = Registry.register(Registry.BLOCK_ENTITY_TYPE, "ntrstlr:starship_helm", FabricBlockEntityTypeBuilder.create(StarshipHelmBlockEntity::new, STARSHIP_HELM).build());
    }

}
