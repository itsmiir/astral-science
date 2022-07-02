package com.miir.astralscience.client.render;

import com.miir.astralscience.AstralScience;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public abstract class AstralScienceSkyEffects extends SkyProperties {

    public static final Object2ObjectMap<Identifier, ? extends AstralScienceSkyEffects> BY_IDENTIFIER = Util.make(new Object2ObjectArrayMap(), (object2ObjectArrayMap) ->
            object2ObjectArrayMap.put(AstralScience.id("airless"), new Airless()));

    public AstralScienceSkyEffects(float cloudsHeight, boolean alternateSkyColor, SkyType skyType, boolean brightenLighting, boolean darkened) {
        super(cloudsHeight, alternateSkyColor, skyType, brightenLighting, darkened);
    }

    public static SkyProperties getOrReturnOriginal(DimensionType dimensionType, SkyProperties original) {
        if (BY_IDENTIFIER.containsKey(dimensionType.getSkyProperties())) {
            return BY_IDENTIFIER.get(dimensionType.getSkyProperties());
        } else {
            return original;
        }
     }

    //        TODO: SkyProperties.java ln 86 contains all the vanilla sky effects- extend from there
    @Environment(EnvType.CLIENT)
    public static class Rogue extends AstralScienceSkyEffects {

        public Rogue() {
                super(200.0F, true, SkyType.NONE, false, true);
        }


        public Vec3d adjustSkyColor(Vec3d color, float sunHeight) {
            return color.multiply(sunHeight * 0.94F + 0.06F, sunHeight * 0.94F + 0.06F, sunHeight * 0.91F + 0.09F);
        }

        @Override
        public boolean useThickFog(int camX, int camY) {
            return true;
        }

        public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
            return color;
        }
    }
    @Environment(EnvType.CLIENT)
    public static class Airless extends AstralScienceSkyEffects {
        public Airless() {
            super(-Float.NaN, true, SkyType.NORMAL, false, true);
        }

        @Override
        public boolean isDarkened() {
            return true;
        }

        @Override
        public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
//            return color;
            return Vec3d.ZERO;
        }

        @Override
        @Nullable
        public float[] getFogColorOverride(float skyAngle, float tickDelta) {
            return new float[]{0f, 0f, 0f, 0f};
        }

        @Override
        public boolean useThickFog(int camX, int camY) {
            return false;
        }

        @Override
        public SkyType getSkyType() {
            return SkyType.NORMAL;
        }

        @Override
        public boolean isAlternateSkyColor() {
            return true;
        }

        @Override
        public float getCloudsHeight() {
            return Float.NaN;
        }

    }
}