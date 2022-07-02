package com.miir.astralscience.screen;

import com.miir.astralscience.recipe.Recipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.PropertyDelegate;

public class CascadicHeaterScreenHandler extends AbstractProcessorScreenHandler {
    public CascadicHeaterScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(AstralScreens.CASCADIC_HEATER_SCREEN_HANDLER, Recipe.HEATING, RecipeBookCategory.FURNACE, syncId, playerInventory);
    }

    public CascadicHeaterScreenHandler(int i, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(AstralScreens.CASCADIC_HEATER_SCREEN_HANDLER, i, Recipe.HEATING, RecipeBookCategory.FURNACE, playerInventory, inventory, propertyDelegate);
    }
}