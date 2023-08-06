package com.miir.astralscience;

import com.miir.astralscience.client.render.AstralSkyEffects;
import com.miir.astralscience.client.render.Render;
import com.miir.astralscience.util.AstralText;
import com.miir.astralscience.world.dimension.AstralDimensions;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
//this class sounds like the name of a "utility" client
public class AstralClient implements ClientModInitializer {

    public static Identifier renderAsFloorBody(String path) {
//        if (path.equals("sylene")) return
        return AstralScience.id("textures/environment/body/" + path + ".png");
    }
    public static Identifier renderClouds(String path) {
        return AstralScience.id("textures/environment/clouds/" + path + ".png");
    }
    public static Identifier renderAtmos(String path) {
        return AstralScience.id("textures/environment/atmosphere/" + path + ".png");
    }
    public static Identifier renderAsMoon(String path) {
        return AstralScience.id("textures/environment/phases/" + path + ".png");
    }

    public static final Identifier SKYBOX_UP = AstralScience.id("textures/environment/1skybox.png");
    public static final Identifier SKYBOX_DOWN = AstralScience.id("textures/environment/2skybox.png");
    public static final Identifier SKYBOX_LEFT = AstralScience.id("textures/environment/3skybox.png");
    public static final Identifier SKYBOX_RIGHT = AstralScience.id("textures/environment/4skybox.png");
    public static final Identifier SKYBOX_FRONT = AstralScience.id("textures/environment/5skybox.png");
    public static final Identifier SKYBOX_BACK = AstralScience.id("textures/environment/6skybox.png");

    public static final Identifier SUN_ORBIT = AstralScience.id("textures/environment/orbit_sun.png");
    public static final Identifier RED_DWARF = AstralScience.id("textures/environment/red_dwarf.png");

    public static final Identifier MOON_ORBIT = AstralScience.id("textures/environment/orbit_moon_phases.png");
    public static final Identifier REENTRY_MOON = AstralScience.id("textures/environment/reentry_moon_phases.png");

    public static final Object2ObjectArrayMap<String, Identifier> SUN_TEXTURES = new Object2ObjectArrayMap<>(
            new String[]{
                    "psidon",},
            new Identifier[]{
                    RED_DWARF}
    );

    public static boolean hasClouds(World world) {
        String worldname = world.getRegistryKey().getValue().getPath();
        if (AstralDimensions.isOrbit(world)) {
            worldname = AstralText.deorbitify(worldname);
        }
        return switch (worldname) {
            case "halyus", "ermis", "sylene", "aere", "iris", "hydes" -> false;
            case "phosphor", "overworld", "zu", "kronos", "ouran", "psidon", "cyri", "omeia" -> true;
            default -> true;
        };
    }

    public static boolean isLuminescent(World world) {
        if (AstralDimensions.isOrbit(world)) {
            return switch (AstralText.deorbitify(world.getRegistryKey().getValue().getPath())) {
                case "cyri", "halyus" -> true;
                default -> false;
            };
        }
        return false;
    }

    public static Identifier getSun(ClientWorld world) {
        return SUN_TEXTURES.getOrDefault(world.getRegistryKey().getValue().getPath(), SUN_ORBIT);
    }

    public static Identifier getMoon(ClientWorld world) {
        boolean orbit = AstralDimensions.isOrbit(world);
            if (orbit) {
                switch (world.getRegistryKey().getValue().getPath()) {
                    case "overworld_orbit":
                        return MOON_ORBIT;
                    case "sylene_orbit":
                        return renderAsMoon("overworld");
                }

            } else {
                // no if statement here for later expansion
                switch (world.getRegistryKey().getValue().getPath()) {
                    case "sylene":
                        return renderAsMoon("overworld");
                    case "psidon":
                        return renderAsFloorBody("ouran");
                }
            }
        return renderAsMoon("none");
    }

    public static boolean shouldMultiplyStars() {
        return true;
    }

    public static int multiplyStars() {
        return 10;
    }



    @Override
    public void onInitializeClient() {
//        ClientNetworking.register();
        Render.register();
        DimensionEffects.BY_IDENTIFIER.putAll(AstralSkyEffects.BY_IDENTIFIER);
        AstralScience.LOGGER.info("Successfully initialized Astral Science version " + AstralScience.VERSION + " in client mode!");
        DimensionRenderingRegistry.registerSkyRenderer(
                RegistryKey.of(RegistryKeys.WORLD, AstralScience.id("overworld_orbit")),
                Render::renderPermaNightSky);
        DimensionRenderingRegistry.registerSkyRenderer(
                RegistryKey.of(RegistryKeys.WORLD, AstralScience.id("psidon")),
                Render::renderPermaNightSky);
    }
}
