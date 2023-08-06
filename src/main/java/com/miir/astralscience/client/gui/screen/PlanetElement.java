//package com.miir.astralscience.client.gui.screen;
//
//import com.miir.astralscience.client.gui.screen.ingame.SystemScreen;
//import com.miir.interstellar.dimension.PlanetInfo;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.minecraft.client.gui.screen.AstralScreens;
//import net.minecraft.util.Identifier;
//import net.minecraft.util.math.Vec2f;
//
//@Environment(EnvType.CLIENT)
//public class PlanetElement extends ScreenElement {
//    public int radius;
//    public PlanetElement(AstralScreens screen, Identifier id, Vec2f pos, Vec2f size, int random, int radius) {
//        super(screen, id, pos, size, random);
//        this.radius = radius;
//    }
//
//    @Override
//    public void activate() {
//        if (this.screen instanceof SystemScreen) {
//            SystemScreen systemScreen = (SystemScreen)this.screen;
//            systemScreen.parent.getScreenHandler().getHelm().setDestination(PlanetInfo.getPlanetInfo(this.id));
//        }
//    }
//}
