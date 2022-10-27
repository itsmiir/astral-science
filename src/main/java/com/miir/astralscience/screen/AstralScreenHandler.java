package com.miir.astralscience.screen;

import com.miir.astralscience.client.gui.screen.StarshipHelmFuelSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class AstralScreenHandler extends ScreenHandler {
    public Inventory inventory;
    protected AstralScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    protected void addPlayerInventory(PlayerInventory playerInventory) {
        addPlayerInventory(playerInventory, 8, 84);
    }

    protected void addPlayerInventory(PlayerInventory playerInventory, int x, int y) {
        int l;
        for(l = 0; l < 3; ++l) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + l * 9 + 9, x + k * 18, y + l * 18));
            }
        }
        for(l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, x + l * 18, y + 58));
        }
    }

    protected void addSlotArray(Inventory inventory, int rows, int columns, int x, int y) {
        int index = 0;
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                this.addSlot(new StarshipHelmFuelSlot(inventory, index, x + 18 * j, y + 18 * i));
                index++;
            }
        }
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }
}
