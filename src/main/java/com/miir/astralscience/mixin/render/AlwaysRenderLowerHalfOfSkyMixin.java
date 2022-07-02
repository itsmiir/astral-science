package com.miir.astralscience.mixin.render;

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
public class AlwaysRenderLowerHalfOfSkyMixin {
    @Shadow private ClientWorld world;

    @ModifyVariable(
//            print = true,
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    target = "Lnet/minecraft/client/world/ClientWorld$Properties;getSkyDarknessHeight(Lnet/minecraft/world/HeightLimitView;)D",
                    value = "INVOKE",
                    shift = At.Shift.BY,
                    by = 3
            ),
            ordinal = 0,
            index = 9
    )
    private double forceFullSkyRender(double original) {
        if (AstralDimensions.isOrbit(this.world)) {
            return 1.0D;
        }
        return original;
    }
}
