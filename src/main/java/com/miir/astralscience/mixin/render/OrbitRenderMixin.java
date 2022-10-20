package com.miir.astralscience.mixin.render;


import com.miir.astralscience.AstralClient;
import com.miir.astralscience.AstralScience;
import com.miir.astralscience.Config;
import com.miir.astralscience.client.render.Render;
import com.miir.astralscience.util.Text;
import com.miir.astralscience.world.dimension.AstralDimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class OrbitRenderMixin {
    @Shadow private ClientWorld world;
    @Shadow private double lastCameraY;
    @Mutable @Shadow @Final private static Identifier SUN;
    @Mutable @Shadow @Final private static Identifier MOON_PHASES;

    @ModifyVariable(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(shift = At.Shift.AFTER, target = "Lnet/minecraft/client/world/ClientWorld;getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;", value = "INVOKE_ASSIGN"),
            index = 8
    )
    private Vec3d darkenSky(Vec3d original) {
        if (world.getRegistryKey().equals(World.OVERWORLD) || AstralDimensions.isAstralDimension(world)) {
            assert MinecraftClient.getInstance().cameraEntity != null;
            double y = MinecraftClient.getInstance().cameraEntity.getY();
            if (y > Config.ATMOSPHERIC_FOG_HEIGHT && y < Config.ATMOSPHERIC_CULL_HEIGHT) {
                return original.multiply(1-(y - Config.ATMOSPHERIC_FOG_HEIGHT) / (Config.ATMOSPHERIC_CULL_HEIGHT - Config.ATMOSPHERIC_FOG_HEIGHT));
            } else if (y > Config.ATMOSPHERIC_CULL_HEIGHT) {
                return Vec3d.ZERO;
            }
        }
        return original;
    }
    @Inject(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
//"TAIL"
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            )
    )
    private void drawStarfield(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        // lerp opacity between y=CULL_HEIGHT and y=FOG_HEIGHT
        float opacity = (float) ((camera.getPos().y - Config.ATMOSPHERIC_FOG_HEIGHT) / (Config.ATMOSPHERIC_CULL_HEIGHT - Config.ATMOSPHERIC_FOG_HEIGHT));
        opacity = MathHelper.clamp(opacity, 0, 1);
        if (opacity > 0) {
            Render.drawStarfield(matrices, opacity);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
        }
    }
//
    @Inject(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = {@At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                    ordinal = 1,
                    shift = At.Shift.BEFORE,
                    remap = false)
            }
    )
    private void renderOrbitalSun(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        if (camera.getPos().y >= Config.ATMOSPHERIC_FOG_HEIGHT) {
            SUN = AstralClient.SUN_ORBIT;
        }
    }
    @Inject(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = {
                    @At(
                            value = "INVOKE",
                            target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                            ordinal = 1,
                            shift = At.Shift.BEFORE,
                            remap = false)
            }
    )
    private void renderOrbitalMoon(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        double y = camera.getPos().y;
//        if (y >= Config.ATMOSPHERIC_CULL_HEIGHT) {
//            MOON_PHASES = AstralClient.MOON_ORBIT;
//        } else
            if (y >= Config.ATMOSPHERIC_FOG_HEIGHT) {
            MOON_PHASES = AstralClient.REENTRY_MOON;
        }
    }

    @ModifyVariable(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;setFogBlack()V",
                    shift = At.Shift.BEFORE,
                    value = "INVOKE"
            ),
            ordinal = 1,
            index = 4
    )
    private float atmosphereGradientR(float original) {
        if (!AstralDimensions.hasAtmosphere(this.world, false)) {
            return 0;
        } else  if (!AstralDimensions.isOrbit(this.world)) {
            double cameraY = this.lastCameraY;
            if (cameraY >= Config.ATMOSPHERIC_FOG_HEIGHT) {
                return (1 - ((float) ((this.lastCameraY -  Config.ATMOSPHERIC_FOG_HEIGHT) / (Config.ATMOSPHERIC_CULL_HEIGHT - Config.ATMOSPHERIC_FOG_HEIGHT)))) * original;
            }
        }
        return original;
    }
    @ModifyVariable(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;setFogBlack()V",
                    shift = At.Shift.BEFORE,
                    value = "INVOKE"
            ),
            ordinal = 2,
            index = 5
    )
    private float atmosphereGradientG(float original) {
        if (!AstralDimensions.hasAtmosphere(this.world, false)) {
            return 0;
        } else if (!AstralDimensions.isOrbit(this.world)) {
            double cameraY = this.lastCameraY;
            if (cameraY >= Config.ATMOSPHERIC_FOG_HEIGHT) {
                return (1 - ((float) ((this.lastCameraY -  Config.ATMOSPHERIC_FOG_HEIGHT) / (Config.ATMOSPHERIC_CULL_HEIGHT - Config.ATMOSPHERIC_FOG_HEIGHT)))) * original;
            }
        }
        return original;
    }
    @ModifyVariable(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;setFogBlack()V",
                    shift = At.Shift.BEFORE,
                    value = "INVOKE"
            ),
            ordinal = 3,
            index = 6
    )
    private float atmosphereGradientB(float original) {
        if (!AstralDimensions.hasAtmosphere(this.world, false)) {
            return 0;
        } else if (!AstralDimensions.isOrbit(this.world)) {
            double cameraY = this.lastCameraY;
            if (cameraY >= Config.ATMOSPHERIC_FOG_HEIGHT) {
                return (1 - ((float) ((this.lastCameraY -  Config.ATMOSPHERIC_FOG_HEIGHT) / (Config.ATMOSPHERIC_CULL_HEIGHT - Config.ATMOSPHERIC_FOG_HEIGHT)))) * original;
            }
        }
        return original;
    }
}
