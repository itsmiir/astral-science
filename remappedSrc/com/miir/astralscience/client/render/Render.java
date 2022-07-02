package com.miir.astralscience.client.render;

import com.miir.astralscience.block.AstralBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class Render {
    public static void register() {
//        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.GHOST_VINES_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.GHOST_VINES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.FIRECAP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.FROSTFUR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.BLUEMOSS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.ANGLER_KELP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.ANGLER_KELP_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.NEPHRUM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.SPEAR_FERN, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.BRAMBLEWOOD_DOOR, RenderLayer.getCutout());

    }
}
