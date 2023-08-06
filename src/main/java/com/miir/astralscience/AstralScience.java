package com.miir.astralscience;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.item.AstralItems;
import com.miir.astralscience.tag.AstralTags;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// made (finally) by miir
public class AstralScience implements ModInitializer {

    public static final String MOD_ID = "astralscience";
    public static final String ORBIT_SUFFIX = "_orbit";
    public static final Logger LOGGER = LogManager.getLogger("Astral Science");
    public static final String VERSION = "0.0.2";
    public static final BlockState STARSHIP_VOID = Blocks.AIR.getDefaultState();
    public static final BlockPos INVALID = new BlockPos(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static final ItemGroup ASTRAL_SCIENCE = FabricItemGroup.builder()
            .displayName(Text.translatable(id("group").toTranslationKey()))
            .icon(() -> new ItemStack(AstralItems.RECORDER))
            .entries((context, entries) -> {
//tools
                entries.add(new ItemStack(AstralItems.RECORDER));
//                entries.add(new ItemStack(AstralItems.SCREWDRIVER));
                entries.add(new ItemStack(AstralItems.GPS));
                entries.add(new ItemStack(AstralItems.GALACTIC_MAP));

//                entries.add(new ItemStack(AstralItems.FLIGHT_HELMET));
                entries.add(new ItemStack(AstralItems.NEPHRYLL_BOOTS));
//item
//                entries.add(new ItemStack(AstralItems.GRAPHITE_ROD));
                entries.add(new ItemStack(AstralItems.STARDUST));
                entries.add(new ItemStack(AstralItems.CASCADIUM_SHARD));
                entries.add(new ItemStack(AstralItems.NEPHRYLL_POWDER));
                entries.add(new ItemStack(AstralItems.NEPHRYLL_PEARL));
                entries.add(new ItemStack(AstralItems.SPEAR_SEED));
//                entries.add(new ItemStack(AstralItems.LIGHT_COMPOSITE_SHEET));
//                entries.add(new ItemStack(AstralItems.THERMAL_COMPOSITE_SHEET));
//                entries.add(new ItemStack(AstralItems.HEAVY_COMPOSITE_PLATE));

//blocks
                entries.add(new ItemStack(AstralBlocks.CASCADIC_BONE));
                entries.add(new ItemStack(AstralBlocks.CASCADIUM_BLOCK));
                entries.add(new ItemStack(AstralBlocks.NEPHRYLL_BLOCK));
                entries.add(new ItemStack(AstralBlocks.ANCIENT_ICE));

                entries.add(new ItemStack(AstralBlocks.FRESH_BLACKSTONE));
                entries.add(new ItemStack(AstralBlocks.MAGMATIC_FRESH_BLACKSTONE));
                entries.add(new ItemStack(AstralBlocks.LIMESTONE));
                entries.add(new ItemStack(AstralBlocks.POLISHED_LIMESTONE));
                entries.add(new ItemStack(AstralBlocks.SLATE));
                entries.add(new ItemStack(AstralBlocks.POLISHED_SLATE));
                entries.add(new ItemStack(AstralBlocks.SHALE));
                entries.add(new ItemStack(AstralBlocks.POLISHED_SHALE));
                entries.add(new ItemStack(AstralBlocks.PUMICE));
                entries.add(new ItemStack(AstralBlocks.POLISHED_PUMICE));
                entries.add(new ItemStack(AstralBlocks.IRON_RICH_BASALT));
                entries.add(new ItemStack(AstralBlocks.IRON_RICH_BASALT_BRICKS));
                entries.add(new ItemStack(AstralBlocks.REGOLITH));
                entries.add(new ItemStack(AstralBlocks.SYLIUM));
                entries.add(new ItemStack(AstralBlocks.PSIONIC_SAND));


//machines
                entries.add(new ItemStack(AstralBlocks.MACHINE_CHASSIS));
//                entries.add(new ItemStack(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK));
                entries.add(new ItemStack(AstralBlocks.STARSHIP_HELM));
                entries.add(new ItemStack(AstralBlocks.CASCADIC_COOLER));
                entries.add(new ItemStack(AstralBlocks.CASCADIC_HEATER));
//plants
                entries.add(new ItemStack(AstralBlocks.FROST_MYCELIUM));
                entries.add(new ItemStack(AstralBlocks.GIANT_STEM));
                entries.add(new ItemStack(AstralBlocks.GIANT_LEAVES));
                entries.add(new ItemStack(AstralBlocks.GHOST_VINES));
                entries.add(new ItemStack(AstralBlocks.FIRECAP));
                entries.add(new ItemStack(AstralBlocks.FIRECAP_GILLS));
                entries.add(new ItemStack(AstralBlocks.FIRECAP_SCALES));
                entries.add(new ItemStack(AstralBlocks.FIRECAP_HYPHAE));
                entries.add(new ItemStack(AstralBlocks.FROSTFUR));

                entries.add(new ItemStack(AstralBlocks.BRAMBLEWOOD_LOG));
                entries.add(new ItemStack(AstralBlocks.BRAMBLEWOOD_PLANKS));
                entries.add(new ItemStack(AstralBlocks.WORMWOOD_LOG));

//petals
                entries.add(new ItemStack(AstralBlocks.RED_PETAL));
                entries.add(new ItemStack(AstralBlocks.ORANGE_PETAL));
                entries.add(new ItemStack(AstralBlocks.YELLOW_PETAL));
                entries.add(new ItemStack(AstralBlocks.MINT_PETAL));
                entries.add(new ItemStack(AstralBlocks.CYAN_PETAL));
                entries.add(new ItemStack(AstralBlocks.BLUE_PETAL));
                entries.add(new ItemStack(AstralBlocks.PEACH_PETAL));
                entries.add(new ItemStack(AstralBlocks.PINK_PETAL));
                entries.add(new ItemStack(AstralBlocks.LAVENDER_PETAL));
                entries.add(new ItemStack(AstralBlocks.MAGENTA_PETAL));
                entries.add(new ItemStack(AstralBlocks.PURPLE_PETAL));
                entries.add(new ItemStack(AstralBlocks.WHITE_PETAL));
                entries.add(new ItemStack(AstralBlocks.BLACK_PETAL));
            })
            .build();

    public static boolean isSuffocating(LivingEntity entity, World world) {
        return (!entity.getType().isIn(AstralTags.ANAEROBIC)) && (!hasAir(world, entity.getPos())) || false;
    }

    private static boolean hasAir(World world, Vec3d pos) {
        return AstralDimensions.hasAtmosphere(world, false);
    }


    @Override
    public void onInitialize() {
        Initialize.register();
    }
}
