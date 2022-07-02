package com.miir.astralscience.block;

import net.minecraft.block.Block;
import net.minecraft.block.KelpBlock;

public class AnglerKelpBlock extends KelpBlock {
    public AnglerKelpBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected Block getPlant() {
        return AstralBlocks.ANGLER_KELP_PLANT;
    }
}
