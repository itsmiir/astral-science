package com.miir.astralscience.mixin.render;

import com.miir.astralscience.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class ReentryFogMixin {
    @Shadow private ClientWorld world;

    @Shadow private double lastCameraY;

    @ModifyVariable(
//            print = true,
            method = "render",
            at = @At(
                    target = "Lnet/minecraft/client/gui/hud/BossBarHud;shouldThickenFog()Z",
                    shift = At.Shift.AFTER,
                    value = "INVOKE_ASSIGN"
            ),
            index = 19,
            ordinal = 1
    )
    private boolean thickenFog(boolean original) {
        if (MinecraftClient.getInstance().cameraEntity.getPos().y >= Config.ATMOSPHERIC_FOG_HEIGHT) {
            return true;
        }
        return original;
    }
//    @ModifyArg(
//            method = "renderLightSky",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/render/WorldRenderer;method_34550(Lnet/minecraft/client/render/BufferBuilder;F)V"
//            )
//    )
//    private boolean keepSkyDark(boolean original) {
//        if (this.world != null) {
//            if ((AstralScience.isAstralDimension(this.world) || this.world.getRegistryKey().equals(World.OVERWORLD)) && !AstralScience.isOrbit(this.world)) {
//                if (this.lastCameraY > Config.ATMOSPHERIC_FOG_HEIGHT) {
//                    return false;
//                }
//            }
//        }
//        return original;
//    }
}
