package com.miir.astralscience;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.block.entity.CascadicCoolerBlockEntity;
import com.miir.astralscience.block.entity.CascadicHeaterBlockEntity;
import com.miir.astralscience.item.AstralItems;
import com.miir.astralscience.tag.AstralTags;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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

    public static final ItemGroup ASTRAL_SCIENCE = FabricItemGroupBuilder.create(
            id("astralscience"))
            .icon(() -> new ItemStack(AstralItems.RECORDER))
            .appendItems(stacks -> {
//tools
                stacks.add(new ItemStack(AstralItems.RECORDER));
                stacks.add(new ItemStack(AstralItems.SCREWDRIVER));
                stacks.add(new ItemStack(AstralItems.GPS));
                stacks.add(new ItemStack(AstralItems.GALACTIC_MAP));

//                stacks.add(new ItemStack(AstralItems.FLIGHT_HELMET));
                stacks.add(new ItemStack(AstralItems.NEPHRYLL_BOOTS));
//items
                stacks.add(new ItemStack(AstralItems.GRAPHITE_ROD));
                stacks.add(new ItemStack(AstralItems.STARDUST));
                stacks.add(new ItemStack(AstralItems.CASCADIUM_SHARD));
                stacks.add(new ItemStack(AstralItems.NEPHRYLL_POWDER));
                stacks.add(new ItemStack(AstralItems.NEPHRYLL_PEARL));
                stacks.add(new ItemStack(AstralItems.SPEAR_SEED));
                stacks.add(new ItemStack(AstralItems.LIGHT_COMPOSITE_SHEET));
                stacks.add(new ItemStack(AstralItems.THERMAL_COMPOSITE_SHEET));
                stacks.add(new ItemStack(AstralItems.HEAVY_COMPOSITE_PLATE));

//blocks
                stacks.add(new ItemStack(AstralBlocks.CASCADIC_BONE));
                stacks.add(new ItemStack(AstralBlocks.CASCADIUM_BLOCK));
                stacks.add(new ItemStack(AstralBlocks.NEPHRYLL_BLOCK));
                stacks.add(new ItemStack(AstralBlocks.ANCIENT_ICE));

                stacks.add(new ItemStack(AstralBlocks.FRESH_BLACKSTONE));
                stacks.add(new ItemStack(AstralBlocks.MAGMATIC_FRESH_BLACKSTONE));
                stacks.add(new ItemStack(AstralBlocks.LIMESTONE));
                stacks.add(new ItemStack(AstralBlocks.POLISHED_LIMESTONE));
                stacks.add(new ItemStack(AstralBlocks.SLATE));
                stacks.add(new ItemStack(AstralBlocks.POLISHED_SLATE));
                stacks.add(new ItemStack(AstralBlocks.SHALE));
                stacks.add(new ItemStack(AstralBlocks.POLISHED_SHALE));
                stacks.add(new ItemStack(AstralBlocks.PUMICE));
                stacks.add(new ItemStack(AstralBlocks.POLISHED_PUMICE));
                stacks.add(new ItemStack(AstralBlocks.IRON_RICH_BASALT));
                stacks.add(new ItemStack(AstralBlocks.IRON_RICH_BASALT_BRICKS));
                stacks.add(new ItemStack(AstralBlocks.REGOLITH));
                stacks.add(new ItemStack(AstralBlocks.SYLIUM));
                stacks.add(new ItemStack(AstralBlocks.PSIONIC_SAND));


//machines
                stacks.add(new ItemStack(AstralBlocks.MACHINE_CHASSIS));
//                stacks.add(new ItemStack(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK));
                stacks.add(new ItemStack(AstralBlocks.STARSHIP_HELM));
                stacks.add(new ItemStack(AstralBlocks.CASCADIC_COOLER));
                stacks.add(new ItemStack(AstralBlocks.CASCADIC_HEATER));
//plants
                stacks.add(new ItemStack(AstralBlocks.FROST_MYCELIUM));
                stacks.add(new ItemStack(AstralBlocks.GIANT_STEM));
                stacks.add(new ItemStack(AstralBlocks.GIANT_LEAVES));
                stacks.add(new ItemStack(AstralBlocks.GHOST_VINES));
                stacks.add(new ItemStack(AstralBlocks.FIRECAP));
                stacks.add(new ItemStack(AstralBlocks.FIRECAP_GILLS));
                stacks.add(new ItemStack(AstralBlocks.FIRECAP_SCALES));
                stacks.add(new ItemStack(AstralBlocks.FIRECAP_HYPHAE));
                stacks.add(new ItemStack(AstralBlocks.FROSTFUR));

                stacks.add(new ItemStack(AstralBlocks.BRAMBLEWOOD_LOG));
                stacks.add(new ItemStack(AstralBlocks.BRAMBLEWOOD_PLANKS));
                stacks.add(new ItemStack(AstralBlocks.WORMWOOD_LOG));

//petals
                stacks.add(new ItemStack(AstralBlocks.RED_PETAL));
                stacks.add(new ItemStack(AstralBlocks.ORANGE_PETAL));
                stacks.add(new ItemStack(AstralBlocks.YELLOW_PETAL));
                stacks.add(new ItemStack(AstralBlocks.MINT_PETAL));
                stacks.add(new ItemStack(AstralBlocks.CYAN_PETAL));
                stacks.add(new ItemStack(AstralBlocks.BLUE_PETAL));
                stacks.add(new ItemStack(AstralBlocks.PEACH_PETAL));
                stacks.add(new ItemStack(AstralBlocks.PINK_PETAL));
                stacks.add(new ItemStack(AstralBlocks.LAVENDER_PETAL));
                stacks.add(new ItemStack(AstralBlocks.MAGENTA_PETAL));
                stacks.add(new ItemStack(AstralBlocks.PURPLE_PETAL));
                stacks.add(new ItemStack(AstralBlocks.WHITE_PETAL));
                stacks.add(new ItemStack(AstralBlocks.BLACK_PETAL));
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
