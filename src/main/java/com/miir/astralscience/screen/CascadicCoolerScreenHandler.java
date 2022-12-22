package com.miir.astralscience.screen;

import com.miir.astralscience.recipe.Recipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.PropertyDelegate;

public class CascadicCoolerScreenHandler extends AbstractProcessorScreenHandler {
    public CascadicCoolerScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(AstralScreens.CASCADIC_COOLER_SCREEN_HANDLER, Recipe.COOLING, RecipeBookCategory.FURNACE, syncId, playerInventory);
    }

    public CascadicCoolerScreenHandler(int i, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(AstralScreens.CASCADIC_COOLER_SCREEN_HANDLER, i, Recipe.COOLING, RecipeBookCategory.FURNACE, playerInventory, inventory, propertyDelegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }
}
