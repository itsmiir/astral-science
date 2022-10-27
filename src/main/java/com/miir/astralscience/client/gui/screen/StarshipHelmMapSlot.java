package com.miir.astralscience.client.gui.screen;

import com.miir.astralscience.item.AstralItems;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class StarshipHelmMapSlot extends Slot {
    public StarshipHelmMapSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isOf(AstralItems.GALACTIC_MAP);
    }
}
