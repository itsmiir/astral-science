package com.miir.astralscience.client.gui.screen;

import com.miir.astralscience.tag.AstralTags;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class StarshipHelmFuelSlot extends Slot {
    public StarshipHelmFuelSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isIn(AstralTags.WARP_FUEL);
    }
}
