package com.miir.astralscience.client.gui.screen.ingame;

import com.miir.astralscience.screen.AbstractProcessorScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class CascadicProcessorFuelSlot extends Slot {
    private final AbstractProcessorScreenHandler handler;
    public CascadicProcessorFuelSlot(AbstractProcessorScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }
    public boolean canInsert(ItemStack stack) {
        return this.handler.isFuel(stack);
    }
}
