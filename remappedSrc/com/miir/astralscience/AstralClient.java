package com.miir.astralscience;

import com.miir.astralscience.util.Text;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
//this class sounds like the name of a "utility" clientside mod
public abstract class AstralClient implements ClientModInitializer {

    public static Identifier renderBody(String path) {
        return AstralScience.id("textures/environment/body/" + path + ".png");
    }
    public static Identifier renderClouds(String path) {
        return AstralScience.id("textures/environment/clouds/" + path + ".png");
    }
    public static Identifier renderAtmos(String path) {
        return AstralScience.id("textures/environment/atmosphere/" + path + ".png");
    }
    public static Identifier renderMoon(String path) {
        return AstralScience.id("textures/environment/phases/" + path + ".png");
    }
    public static final Identifier COSMIC_BACKGROUND = AstralScience.id("textures/environment/galactic_background.png");

//    public static final Identifier SKYBOX_UP = AstralScience.id("textures/environment/skybox_up.png");
//    public static final Identifier SKYBOX_DOWN = AstralScience.id("textures/environment/skybox_down.png");
//    public static final Identifier SKYBOX_LEFT = AstralScience.id("textures/environment/skybox_left.png");
//    public static final Identifier SKYBOX_RIGHT = AstralScience.id("textures/environment/skybox_right.png");
//    public static final Identifier SKYBOX_FRONT = AstralScience.id("textures/environment/skybox_front.png");
//    public static final Identifier SKYBOX_BACK = AstralScience.id("textures/environment/skybox_back.png");

    public static final Identifier SKYBOX_UP = AstralScience.id("textures/environment/1skybox.png");
    public static final Identifier SKYBOX_DOWN = AstralScience.id("textures/environment/2skybox.png");
    public static final Identifier SKYBOX_LEFT = AstralScience.id("textures/environment/3skybox.png");
    public static final Identifier SKYBOX_RIGHT = AstralScience.id("textures/environment/4skybox.png");
    public static final Identifier SKYBOX_FRONT = AstralScience.id("textures/environment/5skybox.png");
    public static final Identifier SKYBOX_BACK = AstralScience.id("textures/environment/6skybox.png");

    public static final Identifier SUN_ORBIT = AstralScience.id("textures/environment/orbit_sun.png");
    public static final Identifier MOON_ORBIT = AstralScience.id("textures/environment/orbit_moon_phases.png");
    public static final Identifier REENTRY_MOON = AstralScience.id("textures/environment/reentry_moon_phases.png");

    public static Vec2f LAST_MAP_FOCUS;
    public static float LAST_MAP_ZOOM;

    public static boolean hasClouds(World world) {
        String worldname = world.getRegistryKey().getValue().getPath();
        if (AstralDimensions.isOrbit(world)) {
            worldname = Text.deorbitify(worldname);
        }
        switch (worldname) {
            case "halyus":
            case "ermis":
            case "sylene":
            case "aere":
            case "iris":
            case "hydes":
                return false;
            case "phosphor":
            case "overworld":
            case "zu":
            case "kronos":
            case "ouran":
            case "psidon":
            case "cyri":
            case "omeia":
            default:
                return true;
        }
    }

    public static boolean isLuminescent(World world) {
        if (AstralDimensions.isOrbit(world)) {
            switch (Text.deorbitify(world.getRegistryKey().getValue().getPath())) {
                case "cyri":
                case "halyus":
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    public static Identifier getMoon(ClientWorld world, boolean orbit) {
            if (orbit) {
                switch (world.getRegistryKey().getValue().getPath()) {
                    case "overworld_orbit":
                        return MOON_ORBIT;
                    case "sylene_orbit":
                        return renderMoon("overworld");

                }

            } else {
                switch (world.getRegistryKey().getValue().getPath()) {
                    case "sylene":
                        return renderMoon("overworld");

                }
            }
        return renderMoon("none");
    }

    @Override
    public void onInitializeClient() {
//        ClientNetworking.register();
//        Render.register();
//        AstralScreens.register();
        LAST_MAP_FOCUS = new Vec2f(0f, 0f);
        LAST_MAP_ZOOM = 0;
        AstralScience.LOGGER.info("Successfully initialized Miir's Interstellar version " + AstralScience.VERSION + " in client mode!");
    }
}
