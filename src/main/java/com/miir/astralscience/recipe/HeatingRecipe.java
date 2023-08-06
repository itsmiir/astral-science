package com.miir.astralscience.recipe;

import com.miir.astralscience.block.AstralBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.util.Identifier;

public class HeatingRecipe extends AbstractCookingRecipe {
    public HeatingRecipe(Identifier id, String group, Ingredient input, ItemStack output, float experience, int cookTime) {
        super(Recipe.HEATING, id, group, CookingRecipeCategory.BLOCKS, input, output, experience, cookTime);
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getRecipeKindIcon() {
        return new ItemStack(AstralBlocks.CASCADIC_HEATER);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HeatingRecipeSerializer.INSTANCE;
    }
    public Ingredient getInput() {
        return this.input;
    }

}
