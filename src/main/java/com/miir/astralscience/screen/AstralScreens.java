package com.miir.astralscience.screen;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.client.gui.screen.ingame.CascadicCoolerScreen;
import com.miir.astralscience.client.gui.screen.ingame.CascadicHeaterScreen;
import com.miir.astralscience.client.gui.screen.ingame.CrescentWandScreen;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerType;


public abstract class AstralScreens {
    //    screens
    public static ScreenHandlerType<CascadicCoolerScreenHandler> CASCADIC_COOLER_SCREEN_HANDLER;
    public static ScreenHandlerType<CascadicHeaterScreenHandler> CASCADIC_HEATER_SCREEN_HANDLER;
    public static ScreenHandlerType<CrescentWandScreenHandler> CRESCENT_WAND_SCREEN_HANDLER;

    public static void register() {
//        cascadic cooler
        CASCADIC_COOLER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(AstralScience.id("cascadic_cooler"), CascadicCoolerScreenHandler::new);
        ScreenRegistry.register(CASCADIC_COOLER_SCREEN_HANDLER, CascadicCoolerScreen::new);
//        cascadic heater
        CASCADIC_HEATER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(AstralScience.id("cascadic_heater"), CascadicHeaterScreenHandler::new);
        ScreenRegistry.register(CASCADIC_HEATER_SCREEN_HANDLER, CascadicHeaterScreen::new);

        CRESCENT_WAND_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(AstralScience.id("crescent_wand"), CrescentWandScreenHandler::new);
        ScreenRegistry.register(CRESCENT_WAND_SCREEN_HANDLER, CrescentWandScreen::new);
    }
}
