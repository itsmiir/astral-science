package com.miir.astralscience.recipe;

import com.miir.astralscience.block.AstralBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;

public class CoolingRecipe extends AbstractCookingRecipe {
    public CoolingRecipe(Identifier id, String group, Ingredient input, ItemStack output, float experience, int cookTime) {
        super(Recipe.COOLING, id, group, input, output, experience, cookTime);
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getRecipeKindIcon() {
        return new ItemStack(AstralBlocks.CASCADIC_COOLER);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CoolingRecipeSerializer.INSTANCE;
    }

    public Ingredient getInput() {
        return this.input;
    }
}
