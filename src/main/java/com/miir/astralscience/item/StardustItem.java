package com.miir.astralscience.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StardustItem extends Item {
    public StardustItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return false;
    }
}
