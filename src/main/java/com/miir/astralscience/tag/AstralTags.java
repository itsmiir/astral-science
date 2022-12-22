package com.miir.astralscience.tag;

import com.miir.astralscience.AstralScience;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;

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
    public static TagKey<DimensionOptions> HAS_ATMOSPHERE;

    public static void register() {
        INDESTRUCTIBLE = TagKey.of(RegistryKeys.BLOCK, new Identifier("c", "indestructible"));
        AIR = TagKey.of(RegistryKeys.BLOCK, new Identifier("c", "air"));
        CARVABLE = TagKey.of(RegistryKeys.BLOCK, AstralScience.id("carvable"));
        PETALS = TagKey.of(RegistryKeys.BLOCK, AstralScience.id("petals"));
        GIANT_PLANT_REPLACEABLE = TagKey.of(RegistryKeys.BLOCK, AstralScience.id("giant_plant_replaceable"));
        SCREWABLE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, AstralScience.id("screwable_blocks"));
        UNSTARSHIPPABLE = TagKey.of(RegistryKeys.BLOCK, AstralScience.id("unstarshippable"));
        STARSHIP_REPLACEABLE = TagKey.of(RegistryKeys.BLOCK, AstralScience.id("starship_replaceable"));
        DEEP_COLD = TagKey.of(RegistryKeys.BLOCK, AstralScience.id("deep_cold"));
        BASE_STONE_SYLENE = TagKey.of(RegistryKeys.BLOCK, AstralScience.id("base_stone_sylene"));
        GIANT_FIRECAP_REPLACEABLE = TagKey.of(RegistryKeys.BLOCK, AstralScience.id("giant_firecap_replaceable"));
        NEPHRUM_GROWABLES = TagKey.of(RegistryKeys.BLOCK, AstralScience.id("nephrum_growables"));

        CASCADIC_FUEL = TagKey.of(RegistryKeys.ITEM, AstralScience.id("cascadic_fuel"));
        GRAVITY_AGNOSTIC = TagKey.of(RegistryKeys.ITEM, AstralScience.id("gravity_agnostic"));
        WARP_FUEL = TagKey.of(RegistryKeys.ITEM, AstralScience.id("warp_fuel"));

        DEEP_FREEZE_IMMUNE = TagKey.of(RegistryKeys.ENTITY_TYPE, AstralScience.id("deep_freeze_immune"));
        ANAEROBIC = TagKey.of(RegistryKeys.ENTITY_TYPE, AstralScience.id("anaerobic"));

        HAS_ATMOSPHERE = TagKey.of(RegistryKeys.DIMENSION, AstralScience.id("has_atmosphere"));
    }
}
