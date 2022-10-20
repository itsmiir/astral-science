package com.miir.astralscience.tag;

import com.miir.astralscience.AstralScience;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AstralTags {
    public static TagKey<Block> AIR;
    public static TagKey<Block> CARVABLE;
    public static TagKey<Block> SCREWABLE_BLOCKS;
    public static TagKey<Block> UNSTARSHIPPABLE;
    public static TagKey<Block> STARSHIP_REPLACEABLE;
    public static TagKey<Item> GRAVITY_AGNOSTIC;
    public static TagKey<Item> WARP_FUEL;
    public static TagKey<Item> CASCADIC_FUEL;
    public static TagKey<Block> DEEP_COLD;
    public static TagKey<EntityType<?>> DEEP_FREEZE_IMMUNE;
    public static TagKey<EntityType<?>> ANAEROBIC;
    public static TagKey<Block> GIANT_FIRECAP_REPLACEABLE;
    public static TagKey<Block> BASE_STONE_SYLENE;
    public static TagKey<Block> INDESTRUCTIBLE;
    public static TagKey<Block> NEPHRUM_GROWABLES;
    public static TagKey<Block> GIANT_PLANT_REPLACEABLE;
    public static TagKey<Block> PETALS;

    public static void register() {
        INDESTRUCTIBLE = TagKey.of(Registry.BLOCK_KEY, new Identifier("c", "indestructible"));
        AIR = TagKey.of(Registry.BLOCK_KEY, new Identifier("c", "air"));
        CARVABLE = TagKey.of(Registry.BLOCK_KEY, AstralScience.id("carvable"));
        PETALS = TagKey.of(Registry.BLOCK_KEY, AstralScience.id("petals"));
        GIANT_PLANT_REPLACEABLE = TagKey.of(Registry.BLOCK_KEY, AstralScience.id("giant_plant_replaceable"));
        SCREWABLE_BLOCKS = TagKey.of(Registry.BLOCK_KEY, AstralScience.id("screwable_blocks"));
        UNSTARSHIPPABLE = TagKey.of(Registry.BLOCK_KEY, AstralScience.id("unstarshippable"));
        STARSHIP_REPLACEABLE = TagKey.of(Registry.BLOCK_KEY, AstralScience.id("starship_replaceable"));
        DEEP_COLD = TagKey.of(Registry.BLOCK_KEY, AstralScience.id("deep_cold"));
        BASE_STONE_SYLENE = TagKey.of(Registry.BLOCK_KEY, AstralScience.id("base_stone_sylene"));
        GIANT_FIRECAP_REPLACEABLE = TagKey.of(Registry.BLOCK_KEY, AstralScience.id("giant_firecap_replaceable"));
        NEPHRUM_GROWABLES = TagKey.of(Registry.BLOCK_KEY, AstralScience.id("nephrum_growables"));

        CASCADIC_FUEL = TagKey.of(Registry.ITEM_KEY, AstralScience.id("cascadic_fuel"));
        GRAVITY_AGNOSTIC = TagKey.of(Registry.ITEM_KEY, AstralScience.id("gravity_agnostic"));
        WARP_FUEL = TagKey.of(Registry.ITEM_KEY, AstralScience.id("warp_fuel"));

        DEEP_FREEZE_IMMUNE = TagKey.of(Registry.ENTITY_TYPE_KEY, AstralScience.id("deep_freeze_immune"));
        ANAEROBIC = TagKey.of(Registry.ENTITY_TYPE_KEY, AstralScience.id("anaerobic"));
    }
}
