package com.miir.astralscience.client.gui.screen.ingame;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.screen.CascadicCoolerScreenHandler;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.FurnaceRecipeBookScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CascadicCoolerScreen extends AbstractProcessorScreen<CascadicCoolerScreenHandler> {
    private static final Identifier TEXTURE = AstralScience.id("textures/gui/container/cascadic_cooler.png");

    public CascadicCoolerScreen(CascadicCoolerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new FurnaceRecipeBookScreen(), inventory, title, TEXTURE);
    }
}
