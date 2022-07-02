package com.miir.astralscience.mixin.render;

import com.miir.astralscience.Config;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class CullAtmosphereMixin {
    @Shadow private ClientWorld world;

    @Shadow private double lastCameraY;

    /**
     * This method should be called if the world has no atmosphere. It makes the stars brighter. Should be used in
     * conjunction with a SkyEffects object to remove atmospheric Rayleigh scattering.
     */
    @ModifyVariable(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    shift = At.Shift.BY,
                    by = 4,
                    target = "Lnet/minecraft/client/world/ClientWorld;method_23787(F)F"
            ),
            ordinal = 10,
            index = 1
    )
    private float cullAtmosphere(float original) {
        boolean shouldCull = AstralDimensions.isOrbit(this.world);
        if (shouldCull) {
            return 1.0F;
        } else if (this.lastCameraY >= Config.ATMOSPHERIC_CULL_HEIGHT && !(AstralDimensions.isOrbit(world))) {
            return (float) ((this.lastCameraY -  Config.ATMOSPHERIC_FOG_HEIGHT) / (Config.ATMOSPHERIC_CULL_HEIGHT - Config.ATMOSPHERIC_FOG_HEIGHT)) * original;
//            return 1.0F;
        }
        return original;
    }
}
