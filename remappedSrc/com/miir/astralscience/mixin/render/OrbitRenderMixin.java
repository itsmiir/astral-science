package com.miir.astralscience.mixin.render;


import com.miir.astralscience.AstralClient;
import com.miir.astralscience.AstralScience;
import com.miir.astralscience.Config;
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
//    to be honest i have no idea how most of this works. it's mainly just stuff adapted from WorldRenderer#renderEndSky

    @Shadow private ClientWorld world;
    @Shadow private double lastCameraY;
    @Mutable
    @Shadow @Final private static Identifier SUN;
    @Mutable
    @Shadow @Final private static Identifier MOON_PHASES;
    @Shadow @Final private MinecraftClient client;
    private float f;
    private float g;
    private float h;
    private TextureManager textureManager;

        public void renderFloorBody(MatrixStack matrices, float light, float tickDelta, double playerHeight, float opacity, boolean inAtmos) {
        float h = (float)playerHeight / 10;
        int l;
        if (AstralClient.isLuminescent(world)) {
            l = 240;
        } else {
            l = (int)(200 * light);
        }
        float height = 100;
        int alpha = (int)(255 * opacity);
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.enableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexLightmapColorShader);
        Matrix4f matrix4f = matrices.peek().getModel();
        String renderPath = Text.deorbitify(this.world.getRegistryKey().getValue().getPath());
        Identifier texture = AstralClient.renderBody(renderPath);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_LIGHT_COLOR);
        bufferBuilder.vertex(matrix4f, -height, -height - h, -height).texture(0.0F, 0.0F).light(WorldRenderer.getLightmapCoordinates(world, client.cameraEntity.getBlockPos())).color(l + 10, l + 10, l + 10, alpha).next();
        bufferBuilder.vertex(matrix4f, -height, -height - h, height).texture(0.0F, 1.0F).light(WorldRenderer.getLightmapCoordinates(world, client.cameraEntity.getBlockPos())).color(l + 10, l + 10, l + 10, alpha).next();
        bufferBuilder.vertex(matrix4f, height, -height- h, height).texture(1.0F, 1.0F).light(WorldRenderer.getLightmapCoordinates(world, client.cameraEntity.getBlockPos())).color(l + 10, l + 10, l + 10, alpha).next();
        bufferBuilder.vertex(matrix4f, height, -height - h, -height).texture(1.0F, 0.0F).light(WorldRenderer.getLightmapCoordinates(world, client.cameraEntity.getBlockPos())).color(l + 10, l + 10, l + 10, alpha).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        if (false) {
//            todo implement this
            BlockPos location = new BlockPos(client.cameraEntity.getBlockPos().getX(), 128, client.cameraEntity.getBlockPos().getZ());
            Vec3i clr = new Vec3i(RenderSystem.getShaderFogColor()[0], RenderSystem.getShaderFogColor()[1], RenderSystem.getShaderFogColor()[2]);
            RenderSystem.setShaderTexture(0, AstralClient.renderAtmos(this.world.getRegistryKey().getValue().getPath()));
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_LIGHT_COLOR);
            bufferBuilder.vertex(matrix4f, -height + 1, -height - h + 1, -height + 1).texture(0.0F, 0.0F).light(WorldRenderer.getLightmapCoordinates(world, client.cameraEntity.getBlockPos())).color(clr.getX(), clr.getY(), clr.getZ(), 255 - alpha).next();
            bufferBuilder.vertex(matrix4f, -height + 1, -height - h + 1, height + 1).texture(0.0F, 1.0F).light(WorldRenderer.getLightmapCoordinates(world, client.cameraEntity.getBlockPos())).color(clr.getX(), clr.getY(), clr.getZ(), 255 - alpha).next();
            bufferBuilder.vertex(matrix4f, height + 1, -height- h + 1, height + 1).texture(1.0F, 1.0F).light(WorldRenderer.getLightmapCoordinates(world, client.cameraEntity.getBlockPos())).color(clr.getX(), clr.getY(), clr.getZ(), 255 - alpha).next();
            bufferBuilder.vertex(matrix4f, height + 1, -height - h + 1, -height + 1).texture(1.0F, 0.0F).light(WorldRenderer.getLightmapCoordinates(world, client.cameraEntity.getBlockPos())).color(clr.getX(), clr.getY(), clr.getZ(), 255 - alpha).next();
            bufferBuilder.end();
            BufferRenderer.draw(bufferBuilder);
        }
        this.renderCloudSheet(matrices, light, h, opacity);
        this.renderAtmosphere(matrices, light, h, opacity);
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }

    protected void renderCloudSheet(MatrixStack matrices, float light, float h, float opacity) {
        if (AstralClient.hasClouds(this.world)) {
            Identifier clouds = AstralClient.renderClouds(Text.deorbitify(this.world.getRegistryKey().getValue().getPath()));
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.setShaderTexture(0, clouds);
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            RenderSystem.enableBlend();
            RenderSystem.enableTexture();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(false);
            Matrix4f matrix4f2 = matrices.peek().getModel();
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            bufferBuilder.vertex(matrix4f2, -100.0F, -97.0F - h, -100.0F).texture(0.0F, 0.0F).color((int) (200 * light) + 50, (int) (200 * light) + 50, (int) (200 * light) + 50, (int) (128 * opacity)).next();
            bufferBuilder.vertex(matrix4f2, -100.0F, -97.0F - h, 100.0F).texture(0.0F, 1.0F).color((int) (200 * light) + 50, (int) (200 * light) + 50, (int) (200 * light) + 50, (int) (128 * opacity)).next();
            bufferBuilder.vertex(matrix4f2, 100.0F, -97.0F - h, 100.0F).texture(1.0F, 1.0F).color((int) (200 * light) + 50, (int) (200 * light) + 50, (int) (200 * light) + 50, (int) (128 * opacity)).next();
            bufferBuilder.vertex(matrix4f2, 100.0F, -97.0F - h, -100.0F).texture(1.0F, 0.0F).color((int) (200 * light) + 50, (int) (200 * light) + 50, (int) (200 * light) + 50, (int) (128 * opacity)).next();
            bufferBuilder.end();
            BufferRenderer.draw(bufferBuilder);
        }
    }

    protected void renderAtmosphere(MatrixStack matrices, float light, float h, float opacity) {
        if (AstralDimensions.hasAtmosphere(this.world)) {
//            double r = color.getX();
//            double g = color.getY();
//            double b = color.getZ();
            double r = 255;
            double g = 255;
            double b = 255;
            Identifier atmos = AstralClient.renderAtmos(Text.deorbitify(this.world.getRegistryKey().getValue().getPath()));
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.setShaderTexture(0, atmos);
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            RenderSystem.enableBlend();
            RenderSystem.enableTexture();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(false);
            Matrix4f matrix4f2 = matrices.peek().getModel();
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            bufferBuilder.vertex(matrix4f2, -100.0F, -95.0F - h, -100.0F).texture(0.0F, 0.0F).color((int) (180 * light * r) + 20, (int) (200 * light * g) + 20, (int) (220 * light * b) + 20, (int) (64 * opacity)).next();
            bufferBuilder.vertex(matrix4f2, -100.0F, -95.0F - h, 100.0F).texture(0.0F, 1.0F).color((int) (180 * light * r) + 20, (int) (200 * light * g) + 20, (int) (220 * light * b) + 20, (int) (64 * opacity)).next();
            bufferBuilder.vertex(matrix4f2, 100.0F, -95.0F - h, 100.0F).texture(1.0F, 1.0F).color((int) (180 * light * r) + 20, (int) (200 * light * g) + 20, (int) (220 * light * b) + 20, (int) (64 * opacity)).next();
            bufferBuilder.vertex(matrix4f2, 100.0F, -95.0F - h, -100.0F).texture(1.0F, 0.0F).color((int) (180 * light * r) + 20, (int) (200 * light * g) + 20, (int) (220 * light * b) + 20, (int) (64 * opacity)).next();
            bufferBuilder.end();
            BufferRenderer.draw(bufferBuilder);
        }
    }

    protected void renderCosmicBackground(MatrixStack matrices, float opacity) {
        try {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
//            RenderSystem.setShaderTexture(0, AstralClient.renderBody("cyri"));
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();

            for (int i = 0; i < 6; ++i) {
                matrices.push();
                if (i == 0) {
                    RenderSystem.setShaderTexture(0, AstralClient.SKYBOX_LEFT);
                    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
                }

                if (i == 1) {
                    RenderSystem.setShaderTexture(0, AstralClient.SKYBOX_RIGHT);
                    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
                }

                if (i == 2) {
                    RenderSystem.setShaderTexture(0, AstralClient.SKYBOX_UP);
                    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
                }

                if (i == 3) {
                    RenderSystem.setShaderTexture(0, AstralClient.SKYBOX_FRONT);
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
                }

                if (i == 4) {
                    RenderSystem.setShaderTexture(0, AstralClient.SKYBOX_BACK);
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-90.0F));
                }
                if (i == 0) {
                    RenderSystem.setShaderTexture(0, AstralClient.SKYBOX_DOWN);
                }
                Matrix4f matrix4f = matrices.peek().getModel();
                bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
                bufferBuilder.vertex(matrix4f, -150.0F, -150.0F, -150.0F).texture(0.0F, 0.0F).color(255, 255, 255, (int) (128 * opacity)).next();
                bufferBuilder.vertex(matrix4f, -150.0F, -150.0F, 150.0F).texture(0.0F, 1.0F).color(255, 255, 255, (int) (128 * opacity)).next();
                bufferBuilder.vertex(matrix4f, 150.0F, -150.0F, 150.0F).texture(1.0F, 1.0F).color(255, 255, 255, (int) (128 * opacity)).next();
                bufferBuilder.vertex(matrix4f, 150.0F, -150.0F, -150.0F).texture(1.0F, 0.0F).color(255, 255, 255, (int) (128 * opacity)).next();
                tessellator.draw();
                matrices.pop();
            }
            RenderSystem.depthMask(true);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
//            RenderSystem.enableAlphaTest();
        } catch (NullPointerException e) {
            AstralScience.LOGGER.warn("Error rendering cosmic background!");
            e.printStackTrace();
        }
    }

    @Inject(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLjava/lang/Runnable;)V",
//            at = @At(
//                    value = "INVOKE",
//                    shift = At.Shift.AFTER,
//                    target = "Lnet/minecraft/client/render/BufferRenderer;draw(Lnet/minecraft/client/render/BufferBuilder;)V",
//                    ordinal = 2
//            )
            at = @At("TAIL")
    )
    private void AddFloorCelestialBodiesMixin(MatrixStack matrices, Matrix4f matrix4f, float tickDelta, Runnable runnable, CallbackInfo ci) {
        if (AstralDimensions.isOrbit(world)) {
            double playerHeight = this.lastCameraY;
            RenderSystem.enableTexture();
            float angle = this.world.getSkyAngle(tickDelta);
            float lightLevel = ((Math.abs(angle - .5F)) % .5F) * 2;
            if (world.getTimeOfDay() % 24000 == 6000) {
                lightLevel = 1;
            }
             this.renderFloorBody(matrices, lightLevel, tickDelta, playerHeight, 1.0F, false);
        } else if (this.lastCameraY >= Config.ATMOSPHERIC_FOG_HEIGHT && ((AstralDimensions.isAstralDimension(world)) || world.getRegistryKey().equals(World.OVERWORLD)) && !AstralDimensions.isOrbit(this.world)) {
            double playerHeight = (this.lastCameraY - Config.ORBIT_DROP_HEIGHT) / 2 - 64;
            BlockPos location = new BlockPos(client.cameraEntity.getBlockPos().getX(), 128, client.cameraEntity.getBlockPos().getZ());
//            Vec3i clr = hexToRGB(Integer.toHexString((world.getBiome(location).getFogColor())));
//            System.out.println(clr);
            RenderSystem.enableTexture();
            float angle = this.world.getSkyAngle(tickDelta);
            float lightLevel = ((Math.abs(angle - .5F)) % .5F) * 2;
            if (world.getTimeOfDay() % 24000 == 6000) {
                lightLevel = 1;
            }
//            float opacity = (float) ((this.lastCameraY - AstralClient.ATMOSPHERIC_CULL_HEIGHT) / (Interstellar.ORBIT_DROP_HEIGHT - AstralClient.ATMOSPHERIC_CULL_HEIGHT));
            float opacity = 1.0F;
            if (Config.ATMOSPHERIC_CULL_HEIGHT > this.lastCameraY && this.lastCameraY > Config.ATMOSPHERIC_FOG_HEIGHT) {
                opacity = (float)((this.lastCameraY - Config.ATMOSPHERIC_FOG_HEIGHT) / (Config.ATMOSPHERIC_CULL_HEIGHT - Config.ATMOSPHERIC_FOG_HEIGHT));
            }
            this.renderFloorBody(matrices, lightLevel, tickDelta, playerHeight, opacity, true);
        }
    }

    @Inject(
            method = "renderSky",
            at = @At(

                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack$Entry;getModel()Lnet/minecraft/util/math/Matrix4f;",
                    ordinal = 2,
                    shift = At.Shift.AFTER
            )
    )
    private void renderStarfield(MatrixStack matrices, Matrix4f matrix4f, float f, Runnable runnable, CallbackInfo ci) {
        if (AstralDimensions.isOrbit(this.world) || this.lastCameraY >= Config.ATMOSPHERIC_CULL_HEIGHT || !AstralDimensions.hasAtmosphere(this.world)) {
            this.renderCosmicBackground(matrices, 1);
        } else if (this.lastCameraY >= Config.ATMOSPHERIC_FOG_HEIGHT) {
            double y = this.lastCameraY;
            float opacity = (float) ((y - Config.ATMOSPHERIC_FOG_HEIGHT) / (Config.ATMOSPHERIC_CULL_HEIGHT - Config.ATMOSPHERIC_FOG_HEIGHT));
            this.renderCosmicBackground(matrices, opacity);
        }
    }

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(
            method = "renderSky",
            at = {@At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                    ordinal = 1,
                    shift = At.Shift.BEFORE,
                    remap = false
            ),
            @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/class_2960;)V",
                    ordinal = 1,
                    shift = At.Shift.BEFORE,
                    remap = false
            )}
    )
    private void renderOrbitalSun(MatrixStack matrices, Matrix4f matrix4f, float f, Runnable runnable, CallbackInfo ci) {
        if (AstralDimensions.isOrbit(this.world) || !AstralDimensions.hasAtmosphere(this.world)) {
            SUN = AstralClient.SUN_ORBIT;
        } else if (this.lastCameraY >= Config.ATMOSPHERIC_FOG_HEIGHT) {
            SUN = AstralClient.SUN_ORBIT;
        }
    }

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(
            method = "renderSky",
            at = {@At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                    ordinal = 1,
                    shift = At.Shift.BEFORE,
                    remap = false
            ),
                    @At(
                            value = "INVOKE",
                            target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/class_2960;)V",
                            ordinal = 1,
                            shift = At.Shift.BEFORE,
                            remap = false
                    )}
    )
    private void renderOrbitalMoon(MatrixStack matrices, Matrix4f matrix4f, float f, Runnable runnable, CallbackInfo ci) {
        if (this.world.getRegistryKey().getValue().getNamespace().equals(AstralScience.MOD_ID)) {
            MOON_PHASES = AstralClient.getMoon(this.world, AstralDimensions.isOrbit(this.world));
        } else if (this.lastCameraY >= Config.ATMOSPHERIC_CULL_HEIGHT) {
            MOON_PHASES = AstralClient.REENTRY_MOON;
        }
    }


    @ModifyVariable(
//            print = true,
            method = "renderSky",
            at = @At(
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;setFogBlack()V",
                    shift = At.Shift.BEFORE,
                    value = "INVOKE"
            ),
            ordinal = 1,
            index = 4
    )
    private float atmosphereGradientR(float original) {
        if (!AstralDimensions.hasAtmosphere(this.world)) {
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
//            print = true,
            method = "renderSky",
            at = @At(
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;setFogBlack()V",
                    shift = At.Shift.BEFORE,
                    value = "INVOKE"
            ),
            ordinal = 2,
            index = 5
    )
    private float atmosphereGradientG(float original) {
        if (!AstralDimensions.hasAtmosphere(this.world)) {
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
//            print = true,
            method = "renderSky",
            at = @At(
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;setFogBlack()V",
                    shift = At.Shift.BEFORE,
                    value = "INVOKE"
            ),
            ordinal = 3,
            index = 6
    )
    private float atmosphereGradientB(float original) {
        if (!AstralDimensions.hasAtmosphere(this.world)) {
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
