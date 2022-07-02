package com.miir.astralscience.recipe;

import com.miir.astralscience.AstralScience;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.registry.Registry;

public abstract class Recipe {
    //    recipes
    public static RecipeType<CoolingRecipe> COOLING;
    public static RecipeType<HeatingRecipe> HEATING;

    public static void register() {
        Registry.register(Registry.RECIPE_SERIALIZER, CoolingRecipeSerializer.ID, CoolingRecipeSerializer.INSTANCE);
        COOLING = Registry.register(Registry.RECIPE_TYPE, AstralScience.id("cooling"), new RecipeType<CoolingRecipe>() {
            public String toString() {
                return "cooling";
            }
        });
        Registry.register(Registry.RECIPE_SERIALIZER, HeatingRecipeSerializer.ID, HeatingRecipeSerializer.INSTANCE);
        HEATING = Registry.register(Registry.RECIPE_TYPE, AstralScience.id("heating"), new RecipeType<HeatingRecipe>() {
            public String toString() {
                return "heating";
            }
        });
    }
}
