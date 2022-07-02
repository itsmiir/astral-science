package com.miir.astralscience.block;

import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.KelpPlantBlock;

public class AnglerKelpPlantBlock extends KelpPlantBlock {
    protected AnglerKelpPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) AstralBlocks.ANGLER_KELP;
    }
}
