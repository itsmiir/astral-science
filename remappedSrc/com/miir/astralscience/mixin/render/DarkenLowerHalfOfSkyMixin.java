package com.miir.astralscience.mixin.render;

import com.miir.astralscience.Config;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DimensionEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Environment(EnvType.CLIENT)
@Mixin(DimensionEffects.Overworld.class)
public class DarkenLowerHalfOfSkyMixin {
    @ModifyArgs(
            method = "adjustFogColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/Vec3d;multiply(DDD)Lnet/minecraft/util/math/Vec3d;"
            )
    )
    private void darkenSkyHalf(Args args) {
        double yPos = MinecraftClient.getInstance().getCameraEntity().getY();
        if (yPos >= Config.ATMOSPHERIC_CULL_HEIGHT || !AstralDimensions.hasAtmosphere(MinecraftClient.getInstance().world)) {
            args.set(0, 0D);
            args.set(1, 0D);
            args.set(2, 0D);
        } else if (yPos >= Config.ATMOSPHERIC_FOG_HEIGHT && yPos < Config.ATMOSPHERIC_CULL_HEIGHT) {
            double multiplier = 1 - ((yPos - Config.ATMOSPHERIC_FOG_HEIGHT) / (Config.ATMOSPHERIC_CULL_HEIGHT - Config.ATMOSPHERIC_FOG_HEIGHT));
            args.set(0, (double)args.get(0) * multiplier);
            args.set(1, (double)args.get(1) * multiplier);
            args.set(2, (double)args.get(2) * multiplier);
        }
    }
}
