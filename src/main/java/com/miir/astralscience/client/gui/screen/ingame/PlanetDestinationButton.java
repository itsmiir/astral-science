//package com.miir.astralscience.client.gui.screen.ingame;
//
//import com.miir.interstellar.Interstellar;
//import com.miir.interstellar.dimension.PlanetInfo;
//import com.miir.interstellar.generation.NameGen;
//import net.minecraft.client.gui.screen.AstralScreens;
//import net.minecraft.text.Text;
//import net.minecraft.util.Identifier;
//
//public class PlanetDestinationButton extends InterstellarTexturedButton {
//    public static final Identifier ICONS = Interstellar.id("textures/gui/planet_icons.png");
//
//    public PlanetDestinationButton(int x, int y, int u, int v, int width, int height, String pathOrbital, AstralScreens parent) {
//        super(x, y, width, height, u, v, 16, ICONS,
//                (button) -> {
//                    assert parent instanceof StarshipHelmScreen;
//                    ((StarshipHelmScreen) parent).getScreenHandler().getHelm().setDestination(PlanetInfo.getPlanetInfo(NameGen.deorbitify(pathOrbital)));
//                    Interstellar.LOGGER.info("Set destination to " + NameGen.fromId(NameGen.deorbitify(pathOrbital)) + "!");
//                },
//                Text.of(NameGen.fromId(pathOrbital)),
//                parent);
//    }
//}
