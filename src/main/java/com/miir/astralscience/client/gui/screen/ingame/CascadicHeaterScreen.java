package com.miir.astralscience.client.gui.screen.ingame;


import com.miir.astralscience.screen.CascadicHeaterScreenHandler;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.FurnaceRecipeBookScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CascadicHeaterScreen extends AbstractProcessorScreen<CascadicHeaterScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("minecraft:textures/gui/container/furnace.png");

    public CascadicHeaterScreen(CascadicHeaterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new FurnaceRecipeBookScreen(), inventory, title, TEXTURE);
    }
}
