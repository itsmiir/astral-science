package com.miir.astralscience.client.render;

import com.miir.astralscience.AstralClient;
import com.miir.astralscience.AstralScience;
import com.miir.astralscience.Config;
import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.util.AstralText;
import com.miir.astralscience.world.dimension.AstralDimensions;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public class Render {



    public static void register() {



//        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.GHOST_VINES_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.GHOST_VINES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.FIRECAP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.FROSTFUR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.BLUEMOSS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.ANGLER_KELP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.ANGLER_KELP_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.NEPHRUM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.SPEAR_FERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AstralBlocks.BRAMBLEWOOD_DOOR, RenderLayer.getCutout());

        WorldRenderEvents.START.register(context -> {
            Camera camera = context.camera();
            double cullHeight = Config.ATMOSPHERIC_CULL_HEIGHT;
            MatrixStack matrices = context.matrixStack();
            double cameraHeight = camera.getPos().y;
            float tickDelta = context.tickDelta();
            World world = context.world();


            if (AstralDimensions.isOrbit(world)) {
                float angle = world.getSkyAngle(tickDelta);
                float lightLevel = ((Math.abs(angle - .5F)) % .5F) * 2;
                if (world.getTimeOfDay() % 24000 == 6000) {
                    lightLevel = 1;
                }
                drawFloorBody(matrices, lightLevel, cameraHeight + Config.ORBIT_DROP_HEIGHT + 64, 1.0F, tickDelta);
            } else if (
                    cameraHeight >= Config.ATMOSPHERIC_FOG_HEIGHT && ((AstralDimensions.isAstralDimension(world)) ||
                            world.getRegistryKey().equals(World.OVERWORLD))) {
                world.getTimeOfDay();
                float opacity = 1.0F;
                if (cullHeight > cameraHeight && cameraHeight > Config.ATMOSPHERIC_FOG_HEIGHT) {
                    opacity = (float) ((cameraHeight - Config.ATMOSPHERIC_FOG_HEIGHT) / (cullHeight - Config.ATMOSPHERIC_FOG_HEIGHT));
                }
                opacity = MathHelper.clamp(opacity, 0, 1);
                if (opacity > 0) {
                    Render.drawStarfield(matrices, opacity);
                }
            }
        });
        WorldRenderEvents.AFTER_SETUP.register(context -> {
            Camera camera = context.camera();
            double cullHeight = Config.ATMOSPHERIC_CULL_HEIGHT;
            double fogHeight = Config.ATMOSPHERIC_FOG_HEIGHT;
            MatrixStack matrices = context.matrixStack();
            double cameraHeight = camera.getPos().y;
            float tickDelta = context.tickDelta();
            World world = context.world();


            if (AstralDimensions.isOrbit(world)) {
                float angle = world.getSkyAngle(tickDelta);
                float lightLevel = ((Math.abs(angle - .5F)) % .5F) * 2;
                if (world.getTimeOfDay() % 24000 == 6000) {
                    lightLevel = 1;
                }
                drawFloorBody(matrices, lightLevel, cameraHeight + Config.ORBIT_DROP_HEIGHT + 64, 1.0F, tickDelta);
            } else if (
                    cameraHeight >= fogHeight/2 && ((AstralDimensions.isAstralDimension(world)) ||
                            world.getRegistryKey().equals(World.OVERWORLD))) {
                float angle = world.getSkyAngle(tickDelta);
                float lightLevel = ((Math.abs(angle - .5F)) % .5F) * 2;
                if (world.getTimeOfDay() % 24000 == 6000) {
                    lightLevel = 1;
                }
                float opacity = 1.0F;
                if (fogHeight > cameraHeight) opacity = 0;
                if (cullHeight > cameraHeight && cameraHeight > Config.ATMOSPHERIC_FOG_HEIGHT) {
                    opacity = (float) ((cameraHeight - Config.ATMOSPHERIC_FOG_HEIGHT) / (cullHeight - Config.ATMOSPHERIC_FOG_HEIGHT));
                }
                opacity = MathHelper.clamp(opacity, 0, 1);
                if (opacity >= 0) {
                    drawFloorBody(matrices, lightLevel, cameraHeight, opacity, tickDelta);
                }
            }
        });
    }

    public static void drawStarfield(MatrixStack matrices, float opacity) {
        try {
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            float d = 50;
            for (int i = 0; i < 6; ++i) {
                matrices.push();
                if (i == 0) {
                    RenderSystem.setShaderTexture(0, AstralClient.SKYBOX_LEFT);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
                }

                if (i == 1) {
                    RenderSystem.setShaderTexture(0, AstralClient.SKYBOX_RIGHT);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
                }

                if (i == 2) {
                    RenderSystem.setShaderTexture(0, AstralClient.SKYBOX_UP);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));
                }

                if (i == 3) {
                    RenderSystem.setShaderTexture(0, AstralClient.SKYBOX_FRONT);
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
                }

                if (i == 4) {
                    RenderSystem.setShaderTexture(0, AstralClient.SKYBOX_BACK);
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90.0F));
                }
                if (i == 5) {
                    RenderSystem.setShaderTexture(0, AstralClient.SKYBOX_DOWN);
                }
                Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                int alpha = (int)(0xFF * (opacity*.9+.1));
                bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
                bufferBuilder.vertex(matrix4f, -d, -d, -d).texture(0.0F, 0.0F).color(255, 255, 255, alpha).next();
                bufferBuilder.vertex(matrix4f, -d, -d, d).texture(0.0F, 1.0F).color(255, 255, 255, alpha).next();
                bufferBuilder.vertex(matrix4f, d, -d, d).texture(1.0F, 1.0F).color(255, 255, 255, alpha).next();
                bufferBuilder.vertex(matrix4f, d, -d, -d).texture(1.0F, 0.0F).color(255, 255, 255, alpha).next();
                tessellator.draw();
                matrices.pop();
            }
            RenderSystem.enableDepthTest();
        } catch (NullPointerException e) {
            AstralScience.LOGGER.warn("Error rendering cosmic background!");
            e.printStackTrace();
        }
    }

    public static void drawFloorBody(MatrixStack matrices, float light, double playerHeight, float opacity, double tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        RegistryKey<World> key = world.getRegistryKey();
        Entity cameraEntity = client.getCameraEntity();
        int alpha = (int) (0xFF * (opacity*.9+.1));
        int planetSize = Config.PLANET_SIZE;
        double cullHeight = Config.ATMOSPHERIC_CULL_HEIGHT;
        double fogHeight = Config.ATMOSPHERIC_FOG_HEIGHT;
        if (playerHeight < fogHeight) {
            alpha = 0;
        }
        float h = (float) playerHeight / Config.ORBIT_DROP_HEIGHT;
        assert world != null;
        assert cameraEntity != null;
        int lightmapCoords = WorldRenderer.getLightmapCoordinates(world, cameraEntity.getBlockPos());
        int l;
        if (AstralClient.isLuminescent(world)) {
            l = 240;
        } else {
            l = (int) (200 * light);
        }
        float height = 12.5f/2;
        String renderPath = AstralText.deorbitify(key.getValue().getPath());
        Identifier planet = AstralClient.renderAsFloorBody(renderPath);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        if (world.getRegistryKey().equals(World.OVERWORLD) && (cameraEntity.getPos().y > fogHeight/2 && cameraEntity.getPos().y < cullHeight)) {
//            mask to hide the transition between real terrain and the rendered earth image
//                BlockPos location = new BlockPos(cameraEntity.getBlockPos().getX(), 128, cameraEntity.getBlockPos().getZ());
//            float[] color = world.getDimensionEffects().getFogColorOverride(world.getSkyAngle((float) tickDelta), (float) tickDelta);
//            if (color == null) {
//                color = new float[] {0, 0, 0};
//            }
            Vec3i clr = new Vec3i((int) (RenderSystem.getShaderFogColor()[0] * 255), (int) (RenderSystem.getShaderFogColor()[1] * 255), (int) (RenderSystem.getShaderFogColor()[2] * 255));
            int r = clr.getX();//(int)color[0]*255;
            int g = clr.getY();//(int)color[1]*255;
            int b = clr.getZ();//(int)color[2]*255;
            RenderSystem.setShaderTexture(0, AstralClient.renderAtmos(key.getValue().getPath()));
            RenderSystem.setShader(GameRenderer::getPositionTexLightmapColorProgram);
            Matrix4f positionMatrix1 = matrices.peek().getPositionMatrix();
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_LIGHT_COLOR);
            float hMask = -height * h-1;
            bufferBuilder.vertex(positionMatrix1, -planetSize, hMask, -planetSize).texture(0.0F, 0.0F).light(lightmapCoords).color(r, g, b, 255).next();
            bufferBuilder.vertex(positionMatrix1, -planetSize, hMask, planetSize).texture(0.0F, 1.0F).light(lightmapCoords).color(r, g, b, 255).next();
            bufferBuilder.vertex(positionMatrix1, planetSize, hMask, planetSize).texture(1.0F, 1.0F).light(lightmapCoords).color(r, g, b, 255).next();
            bufferBuilder.vertex(positionMatrix1, planetSize, hMask, -planetSize).texture(1.0F, 0.0F).light(lightmapCoords).color(r, g, b, 255).next();
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        }

        // draw body
        RenderSystem.setShader(GameRenderer::getPositionTexLightmapColorProgram);
        RenderSystem.setShaderTexture(0, planet);
        Matrix4f positionMatrix = matrices.peek().getPositionMatrix();

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_LIGHT_COLOR);
        bufferBuilder.vertex(positionMatrix, -planetSize, -height * h, -planetSize).texture(0.0F, 0.0F).light(lightmapCoords).color(l + 10, l + 10, l + 10, alpha).next();
        bufferBuilder.vertex(positionMatrix, -planetSize, -height * h, planetSize).texture(0.0F, 1.0F).light(lightmapCoords).color(l + 10, l + 10, l + 10, alpha).next();
        bufferBuilder.vertex(positionMatrix, planetSize, -height * h, planetSize).texture(1.0F, 1.0F).light(lightmapCoords).color(l + 10, l + 10, l + 10, alpha).next();
        bufferBuilder.vertex(positionMatrix, planetSize, -height * h, -planetSize).texture(1.0F, 0.0F).light(lightmapCoords).color(l + 10, l + 10, l + 10, alpha).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

        // draw clouds
        if (AstralClient.hasClouds(world)) {
            Identifier clouds = AstralClient.renderClouds(AstralText.deorbitify(key.getValue().getPath()));
            float cloudHeight = (float) (-height * (h - (fogHeight / ((float) Config.ORBIT_DROP_HEIGHT))));
            RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
            RenderSystem.setShaderTexture(0, clouds);
            Matrix4f matrix4f2 = matrices.peek().getPositionMatrix();
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            bufferBuilder.vertex(matrix4f2, -planetSize, cloudHeight, -planetSize).texture(0.0F, 0.0F).color((int) (200 * light) + 50, (int) (200 * light) + 50, (int) (200 * light) + 50, (int) (128 * opacity)).next();
            bufferBuilder.vertex(matrix4f2, -planetSize, cloudHeight, planetSize).texture(0.0F, 1.0F).color((int) (200 * light) + 50, (int) (200 * light) + 50, (int) (200 * light) + 50, (int) (128 * opacity)).next();
            bufferBuilder.vertex(matrix4f2, planetSize, cloudHeight, planetSize).texture(1.0F, 1.0F).color((int) (200 * light) + 50, (int) (200 * light) + 50, (int) (200 * light) + 50, (int) (128 * opacity)).next();
            bufferBuilder.vertex(matrix4f2, planetSize, cloudHeight, -planetSize).texture(1.0F, 0.0F).color((int) (200 * light) + 50, (int) (200 * light) + 50, (int) (200 * light) + 50, (int) (128 * opacity)).next();
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        }
        if (AstralDimensions.hasAtmosphere(world, true)) {
            double r = 1;
            double g = 1;
            double b = 1;
            Identifier atmos = AstralClient.renderAtmos(AstralText.deorbitify(key.getValue().getPath()));
            RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
            RenderSystem.setShaderTexture(0, atmos);
            Matrix4f matrix4f2 = matrices.peek().getPositionMatrix();
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            float atmosphereHeight = (float) (-height * (h - (fogHeight / ((float) Config.ORBIT_DROP_HEIGHT)))) - 1;
            bufferBuilder.vertex(matrix4f2, -planetSize, atmosphereHeight, -planetSize).texture(0.0F, 0.0F).color((int) (180 * light * r) + 20, (int) (200 * light * g) + 20, (int) (220 * light * b) + 20, (int) (64 * opacity)).next();
            bufferBuilder.vertex(matrix4f2, -planetSize, atmosphereHeight, planetSize).texture(0.0F, 1.0F).color((int) (180 * light * r) + 20, (int) (200 * light * g) + 20, (int) (220 * light * b) + 20, (int) (64 * opacity)).next();
            bufferBuilder.vertex(matrix4f2, planetSize, atmosphereHeight, planetSize).texture(1.0F, 1.0F).color((int) (180 * light * r) + 20, (int) (200 * light * g) + 20, (int) (220 * light * b) + 20, (int) (64 * opacity)).next();
            bufferBuilder.vertex(matrix4f2, planetSize, atmosphereHeight, -planetSize).texture(1.0F, 0.0F).color((int) (180 * light * r) + 20, (int) (200 * light * g) + 20, (int) (220 * light * b) + 20, (int) (64 * opacity)).next();
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        }
        RenderSystem.depthMask(true);
    }

    public static void renderPermaNightSky(WorldRenderContext context) {
        Camera camera = context.camera();
        ClientWorld world = context.world();
        float tickDelta = context.tickDelta();
        MatrixStack matrices = context.matrixStack();
        float q;
        float p;
        float o;
        int m;
        float k;
        float i;
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        if (cameraSubmersionType == CameraSubmersionType.POWDER_SNOW || cameraSubmersionType == CameraSubmersionType.LAVA) {
            return;
        }
        Vec3d vec3d = /*Vec3d.ZERO; */world.getSkyColor(context.camera().getPos(), tickDelta);
        float f = (float) vec3d.x;
        float g = (float) vec3d.y;
        float h = (float) vec3d.z;
        BackgroundRenderer.setFogBlack();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(f, g, h, 1.0f);
        VertexBuffer.unbind();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        float[] fs = world.getDimensionEffects().getFogColorOverride(world.getSkyAngle(tickDelta), tickDelta);
        if (fs != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorProgram);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0f));
            i = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) < 0.0f ? 180.0f : 0.0f;
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0f));
            float j = fs[0];
            k = fs[1];
            float l = fs[2];
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
            bufferBuilder.vertex(matrix4f, 0.0f, 100.0f, 0.0f).color(j, k, l, fs[3]).next();
            for (int n = 0; n <= 16; ++n) {
                o = (float) n * ((float) Math.PI * 2) / 16.0f;
                p = MathHelper.sin(o);
                q = MathHelper.cos(o);
                bufferBuilder.vertex(matrix4f, p * 120.0f, q * 120.0f, -q * 40.0f * fs[3]).color(fs[0], fs[1], fs[2], 0.0f).next();
            }
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
            matrices.pop();
        }
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        matrices.push();
        i = 1.0f - world.getRainGradient(tickDelta);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, i);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0f));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(world.getSkyAngle(tickDelta) * 360.0f));
        Matrix4f matrix4f2 = matrices.peek().getPositionMatrix();
        k = 30.0f;
        long time = context.world().getTimeOfDay();
        boolean hasAtmosphere = AstralDimensions.hasAtmosphere(context.world(), false);
        if (!hasAtmosphere || time > 12000) {
            float opacity = hasAtmosphere ?
                    MathHelper.clamp((6000 - Math.abs(time - 18000)) / 6000f,0,1) :
                    1;
            drawStarfield(matrices, opacity);
        }
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, AstralClient.getSun(world));
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix4f2, -k, 100.0f, -k).texture(0.0f, 0.0f).next();
        bufferBuilder.vertex(matrix4f2, k, 100.0f, -k).texture(1.0f, 0.0f).next();
        bufferBuilder.vertex(matrix4f2, k, 100.0f, k).texture(1.0f, 1.0f).next();
        bufferBuilder.vertex(matrix4f2, -k, 100.0f, k).texture(0.0f, 1.0f).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        k = 20.0f;
        RenderSystem.setShaderTexture(0, AstralClient.getMoon(world));
        int r = world.getMoonPhase();
        int s = r % 4;
        m = r / 4 % 2;
        float t = (float) (s) / 4.0f;
        o = (float) (m) / 2.0f;
        p = (float) (s + 1) / 4.0f;
        q = (float) (m + 1) / 2.0f;
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix4f2, -k, -100.0f, k).texture(p, q).next();
        bufferBuilder.vertex(matrix4f2, k, -100.0f, k).texture(t, q).next();
        bufferBuilder.vertex(matrix4f2, k, -100.0f, -k).texture(t, o).next();
        bufferBuilder.vertex(matrix4f2, -k, -100.0f, -k).texture(p, o).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        matrices.pop();
        RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 1.0f);
        if (world.getDimensionEffects().isAlternateSkyColor()) {
            RenderSystem.setShaderColor(f * 0.2f + 0.04f, g * 0.2f + 0.04f, h * 0.6f + 0.1f, 1.0f);
        } else {
            RenderSystem.setShaderColor(f, g, h, 1.0f);
        }
        RenderSystem.depthMask(true);
    }
}
