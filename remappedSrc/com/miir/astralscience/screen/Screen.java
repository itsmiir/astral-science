package com.miir.astralscience.screen;

import com.miir.interstellar.Interstellar;
import com.miir.interstellar.client.gui.screen.ingame.CascadicCoolerScreen;
import com.miir.interstellar.client.gui.screen.ingame.CascadicHeaterScreen;
import com.miir.interstellar.client.gui.screen.ingame.StarshipHelmScreen;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;


public abstract class Screen {
    public static void register() {
//        cascadic cooler
        Interstellar.CASCADIC_COOLER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(Interstellar.id("cascadic_cooler"), CascadicCoolerScreenHandler::new);
        ScreenRegistry.register(Interstellar.CASCADIC_COOLER_SCREEN_HANDLER, CascadicCoolerScreen::new);
//        cascadic heater
        Interstellar.CASCADIC_HEATER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(Interstellar.id("cascadic_heater"), CascadicHeaterScreenHandler::new);
        ScreenRegistry.register(Interstellar.CASCADIC_HEATER_SCREEN_HANDLER, CascadicHeaterScreen::new);
//        starship helm
        Interstellar.STARSHIP_HELM_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(Interstellar.id("starship_helm"), StarshipHelmScreenHandler::new);
        ScreenRegistry.register(Interstellar.STARSHIP_HELM_SCREEN_HANDLER, StarshipHelmScreen::new);
    }
}
