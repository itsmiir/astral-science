package com.miir.astralscience.mixin;

import com.google.common.collect.ImmutableSet;
import com.miir.astralscience.block.AstralBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.carver.Carver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(Carver.class)
public class MarkBlocksAsCarvableMixin {
    @Shadow protected Set<Block> alwaysCarvableBlocks;

    @Inject(method = "<init>(Lcom/mojang/serialization/Codec;)V", at = @At(value = "TAIL"))
    private void CarverMixin(CallbackInfo ci) {
        this.alwaysCarvableBlocks = ImmutableSet.of(

                AstralBlocks.LIMESTONE,
                AstralBlocks.PUMICE,
                AstralBlocks.SHALE,
                AstralBlocks.SLATE,
                AstralBlocks.REGOLITH,
                AstralBlocks.IRON_RICH_BASALT,
                AstralBlocks.FRESH_BLACKSTONE,
                AstralBlocks.MAGMATIC_FRESH_BLACKSTONE,
                AstralBlocks.ANCIENT_ICE,

                Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.BLACKSTONE,
                Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.MYCELIUM,
                Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA,
                Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE
//                Blocks.SNOW, Blocks.SNOW_BLOCK, Blocks.PACKED_ICE, Blocks.POWDER_SNOW
        );
    }
}

