package com.miir.astralscience.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class CrescentWandScreenHandler extends ScreenHandler {
    public CrescentWandScreenHandler(int syncId, PlayerInventory inventory) {
        super(AstralScreens.CRESCENT_WAND_SCREEN_HANDLER, syncId);
    }
    public CrescentWandScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack stack) {
        super(AstralScreens.CRESCENT_WAND_SCREEN_HANDLER, syncId);
//        ItemStack stack = playerInventory.player.getStackInHand(playerInventory.player.getActiveHand());
//        if (stack.getItem().equals(AstralItems.CRESCENT_WAND)) {
//        SimpleInventory wandInv = CrescentWandItem.getInventory(stack);
//        wandInv = new SimpleInventory(3);
//        this.addSlot(new Slot(wandInv, 0, 56, 51));
//        this.addSlot(new Slot(wandInv, 1, 79, 58));
//        this.addSlot(new Slot(wandInv, 2, 102, 51));
        int k;
        for (k = 0; k < 3; ++k) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for (k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }
//    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
