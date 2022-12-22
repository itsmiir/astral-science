package com.miir.astralscience.client.render;

import com.miir.astralscience.AstralScience;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public abstract class AstralSkyEffects extends DimensionEffects {

    public static final Object2ObjectArrayMap<Identifier, ? extends AstralSkyEffects> BY_IDENTIFIER = Util.make(new Object2ObjectArrayMap<>(), (map) -> {
            map.put(AstralScience.id("orbit"), new Airless());
            map.put(AstralScience.id("cyri"), new Cyri());
        map.put(AstralScience.id("phosphor"), new Phosphor());
        map.put(AstralScience.id("psidon"), new Psidon());

    });

    public AstralSkyEffects(float cloudsHeight, boolean alternateSkyColor, SkyType skyType, boolean brightenLighting, boolean darkened) {
        super(cloudsHeight, alternateSkyColor, skyType, brightenLighting, darkened);
    }

    public static DimensionEffects getOrReturnOriginal(DimensionType dimensionType, DimensionEffects original) {
        if (BY_IDENTIFIER.containsKey(dimensionType.effects())) {
            return BY_IDENTIFIER.get(dimensionType.effects());
        } else {
            return original;
        }
     }

     @Environment(EnvType.CLIENT)
     public static class Cyri extends AstralSkyEffects {
         public Cyri() {
             super(Float.NaN, true, SkyType.NORMAL, false, true);
         }

         @Override
         public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
             return color.multiply((double)(sunHeight * 0.4F + 0.09F), (double)(sunHeight * 0.4F + 0.09F), (double)(sunHeight * 0.91F + 0.06F));
         }

         @Override
         public boolean useThickFog(int camX, int camY) {
             return false;
         }
     }

    @Environment(EnvType.CLIENT)
    public static class Phosphor extends AstralSkyEffects {
        public Phosphor() {
            super(Float.NaN, true, SkyType.NORMAL, false, true);
        }

        @Override
        public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
            return color.multiply(sunHeight * 0.94F + 0.06F, (double)(sunHeight * 0.94F + 0.06F), sunHeight * 0.91F + 0.09F);
        }

        @Override
        public boolean useThickFog(int camX, int camY) {
            return true;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Psidon extends AstralSkyEffects {
        public Psidon() {
            super(-Float.NaN, true, SkyType.NORMAL, false, true);
        }

        @Override
        public boolean isDarkened() {
            return true;
        }

        public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
//            return color.multiply(sunHeight * 0.91f + 0.09f, sunHeight * 0.91f + 0.09f, sunHeight * 0.91f + 0.09f);
            return color.multiply(sunHeight * sunHeight, sunHeight * sunHeight, sunHeight * sunHeight);
//            return color;
        }

        @Override
        @Nullable
        public float[] getFogColorOverride(float skyAngle, float tickDelta) {
//            return new float[]{0f, 0f, 0f, 0f};
            return null;
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

    @Environment(EnvType.CLIENT)
    public static class Airless extends AstralSkyEffects {
        public Airless() {
            super(-Float.NaN, true, SkyType.NORMAL, false, true);
        }

        @Override
        public boolean isDarkened() {
            return true;
        }

        @Override
        public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
            double d;
            double e;
            double f;
            d = 0D;
            e = 0D;
            f = 0D;
            return new Vec3d(d, e, f);
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
