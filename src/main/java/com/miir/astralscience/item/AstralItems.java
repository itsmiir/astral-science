package com.miir.astralscience.item;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.material.AstralMaterials;
import com.miir.astralscience.material.NephryllMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class AstralItems {
//    MATERIALS
    public static final Item CASCADIUM_SHARD = new Item(new Item.Settings().maxCount(64).group(ItemGroup.MISC).fireproof().rarity(Rarity.UNCOMMON));
    public static final Item LIGHT_COMPOSITE_SHEET = new Item(new Item.Settings().maxCount(64).rarity(Rarity.COMMON).group(ItemGroup.MATERIALS));
    public static final Item HEAVY_COMPOSITE_PLATE = new Item(new Item.Settings().maxCount(64).rarity(Rarity.COMMON).group(ItemGroup.MATERIALS));
    public static final Item GRAPHITE_ROD = new Item(new Item.Settings().maxCount(64).rarity(Rarity.COMMON).group(ItemGroup.MATERIALS));
    public static final Item THERMAL_COMPOSITE_SHEET = new Item(new Item.Settings().maxCount(64).rarity(Rarity.UNCOMMON).group(ItemGroup.MATERIALS));
    public static final Item DISTILLED_PURPUR = new Item(new Item.Settings().maxCount(64).rarity(Rarity.COMMON).group(ItemGroup.MATERIALS));
    public static final Item MICROCONTROLLER = new Item(new Item.Settings().maxCount(64).rarity(Rarity.COMMON).group(ItemGroup.MATERIALS));
    public static final Item ASTROLABE = new Item(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).group(ItemGroup.TOOLS));
    public static final Item CRESCENT_WAND = new CrescentWandItem(new Item.Settings().maxCount(1).group(ItemGroup.TOOLS).rarity(Rarity.COMMON));

    public static final Item STARDUST = new StardustItem(new Item.Settings().maxCount(64).rarity(Rarity.RARE));
    public static final Item NEPHRYLL_POWDER = new NephryllPowderItem(AstralBlocks.NEPHRUM, new Item.Settings().maxCount(64).rarity(Rarity.UNCOMMON));
    public static final Item NEPHRYLL_PEARL = new Item(new Item.Settings().maxCount(64).rarity(Rarity.UNCOMMON));
    public static final Item SPEAR_SEED = new AliasedBlockItem(AstralBlocks.SPEAR_FERN, new Item.Settings().maxCount(64).rarity(Rarity.COMMON));

//    TOOLS
    public static final Item RECORDER = new RecorderItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
    public static final Item SCREWDRIVER = new ScrewdriverItem(new Item.Settings().maxDamage(560).rarity(Rarity.COMMON).group(ItemGroup.TOOLS));
    public static final Item DIMENSIONAL_COMPASS = new DimensionalCompassItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1));
    public static final Item GPS = new GPSItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).group(ItemGroup.TOOLS));
    public static final Item GALACTIC_MAP = new GalacticMapItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1).group(ItemGroup.TOOLS).recipeRemainder(GPS));

//    ARMOR
//    light composite
//    public static final ArmorMaterial LIGHT_COMPOSITE = new LightCompositeMaterial();
    public static final ArmorMaterial NEPHRYLL = new NephryllMaterial();
//    public static final Item FLIGHT_HELMET = new ArmorItem(AstralMaterials.LIGHT_COMPOSITE, EquipmentSlot.HEAD, new Item.Settings().rarity(Rarity.COMMON).group(ItemGroup.COMBAT));
//    public static final Item FLIGHT_SUIT = new ArmorItem(LIGHT_COMPOSITE, EquipmentSlot.CHEST, new Item.Settings().rarity(Rarity.COMMON));
//    public static final Item FLIGHT_SUIT_PANTS = new ArmorItem(LIGHT_COMPOSITE, EquipmentSlot.LEGS, new Item.Settings().rarity(Rarity.COMMON));
    public static final Item NEPHRYLL_BOOTS = new ArmorItem(NEPHRYLL, EquipmentSlot.FEET, new Item.Settings().rarity(Rarity.UNCOMMON).group(ItemGroup.COMBAT).fireproof());

    public static void register() {
        Registry.register(Registry.ITEM, AstralScience.id("recorder"), RECORDER);
        Registry.register(Registry.ITEM, AstralScience.id("screwdriver"), SCREWDRIVER);
        Registry.register(Registry.ITEM, AstralScience.id("dimensional_compass"), DIMENSIONAL_COMPASS);
        Registry.register(Registry.ITEM, AstralScience.id("gps"), GPS);
        Registry.register(Registry.ITEM, AstralScience.id("galactic_map"), GALACTIC_MAP);
        Registry.register(Registry.ITEM, AstralScience.id("astrolabe"), ASTROLABE);
        Registry.register(Registry.ITEM, AstralScience.id("crescent_wand"), CRESCENT_WAND);

        Registry.register(Registry.ITEM, AstralScience.id("stardust"), STARDUST);
        Registry.register(Registry.ITEM, AstralScience.id("cascadium_shard"), CASCADIUM_SHARD);
        Registry.register(Registry.ITEM, AstralScience.id("distilled_purpur"), DISTILLED_PURPUR);
        Registry.register(Registry.ITEM, AstralScience.id("nephryll_powder"), NEPHRYLL_POWDER);
        Registry.register(Registry.ITEM, AstralScience.id("nephryll_pearl"), NEPHRYLL_PEARL);
        Registry.register(Registry.ITEM, AstralScience.id("spear_seed"), SPEAR_SEED);
        Registry.register(Registry.ITEM, AstralScience.id("microcontroller"), MICROCONTROLLER);

        Registry.register(Registry.ITEM, AstralScience.id("light_composite_sheet"), LIGHT_COMPOSITE_SHEET);
        Registry.register(Registry.ITEM, AstralScience.id("heavy_composite_plate"), HEAVY_COMPOSITE_PLATE);
        Registry.register(Registry.ITEM, AstralScience.id("graphite_rod"), GRAPHITE_ROD);
        Registry.register(Registry.ITEM, AstralScience.id("thermal_composite_sheet"), THERMAL_COMPOSITE_SHEET);

//        Registry.register(Registry.ITEM, AstralScience.id("flight_helmet"), FLIGHT_HELMET);
//        Registry.register(Registry.ITEM, AstralScience.id("flight_suit"), FLIGHT_SUIT);
//        Registry.register(Registry.ITEM, AstralScience.id("flight_suit_pants"), FLIGHT_SUIT_PANTS);
        Registry.register(Registry.ITEM, AstralScience.id("nephryll_boots"), NEPHRYLL_BOOTS);

    }
}
