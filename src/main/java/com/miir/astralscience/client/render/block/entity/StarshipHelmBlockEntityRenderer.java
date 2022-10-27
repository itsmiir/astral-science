package com.miir.astralscience.client.render.block.entity;

import com.miir.astralscience.AstralClient;
import com.miir.astralscience.block.entity.StarshipHelmBlockEntity;
import com.miir.astralscience.world.dimension.AstralDimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class StarshipHelmBlockEntityRenderer implements BlockEntityRenderer<StarshipHelmBlockEntity> {
    public static final int RENDER_RANGE = StarshipHelmBlockEntity.RANGE;
    private static final int MAX_DELTA = 50;
    private int delta;
    public StarshipHelmBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        delta = 0;
    }
    @Override
    public void render(StarshipHelmBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ClientWorld world = (ClientWorld) entity.getWorld();
        RegistryKey<World> key = world.getRegistryKey();
        Vec3d origin = Vec3d.ofCenter(entity.getPos());
        double x = origin.x;
        double y = origin.y;
        double z = origin.z;
        if (world.isPlayerInRange(x, y, z, RENDER_RANGE) || delta > 0) {
            if (delta < MAX_DELTA) delta++;
            if (!world.isPlayerInRange(x, y, z, RENDER_RANGE)) delta -= 2;
            float timeDelta = (((float) delta)/MAX_DELTA);
            Vec3d relative = origin.subtract(MinecraftClient.getInstance().cameraEntity.getCameraPosVec(tickDelta));
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            matrices.translate(relative.x, relative.y, relative.z);
            for (RegistryKey<World> destination : AstralDimensions.VISITABLE_DIMENSIONS) {
                if (AstralDimensions.isVisibleFromHere(destination, key)) {
                    Vec2f angles = AstralDimensions.getRelativeDirection(destination, world);
                    matrices.push();
//                    AstralScience.LOGGER.info("ping" + destination.getValue().getPath());
                    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(angles.y));
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(angles.x));
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(world.getSkyAngle(tickDelta) * 360.0f));
                    RenderSystem.enableTexture();
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShader(GameRenderer::getPositionTexLightmapColorShader);
                    RenderSystem.setShaderTexture(0, AstralClient.renderBody(destination.getValue().getPath()));
                    RenderSystem.setShaderColor(1, 1, 1, 1);
                    Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_LIGHT_COLOR);
                    float l = 1f;
                    float yaw = MinecraftClient.getInstance().getCameraEntity().getYaw(tickDelta);
                    float pitch = MinecraftClient.getInstance().getCameraEntity().getPitch(tickDelta);
//                    if (MinecraftClient.getInstance().cameraEntity.raycast());
                    float k = 5f * l;
                    float yOffset = StarshipHelmBlockEntity.DRAW_DISTANCE*.75f + StarshipHelmBlockEntity.DRAW_DISTANCE*.25f * timeDelta;
                    int lightmapCoords = WorldRenderer.getLightmapCoordinates(world, entity.getPos().up());
//                    int color = 0x99ccffcc;
                    int color = 0x00FFFFFF | (((int)(0x99*timeDelta)) << 24);
//                    AstralScience.LOGGER.info(Integer.toHexString((((int)(255*(((float) delta)/MAX_DELTA)))) << 6));
                    bufferBuilder.vertex(positionMatrix, -k, yOffset, -k).texture(0.0f, 0.0f).light(lightmapCoords).color(color).next();
                    bufferBuilder.vertex(positionMatrix, k, yOffset, -k).texture(1.0f, 0.0f).light(lightmapCoords).color(color).next();
                    bufferBuilder.vertex(positionMatrix, k, yOffset, k).texture(1.0f, 1.0f).light(lightmapCoords).color(color).next();
                    bufferBuilder.vertex(positionMatrix, -k, yOffset, k).texture(0.0f, 1.0f).light(lightmapCoords).color(color).next();
                    BufferRenderer.drawWithShader(bufferBuilder.end());
                    matrices.pop();
                }
            }
        }
        else {
            delta = 0;
        }
    }

    @Override
    public boolean rendersOutsideBoundingBox(StarshipHelmBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean isInRenderDistance(StarshipHelmBlockEntity blockEntity, Vec3d pos) {
        return BlockEntityRenderer.super.isInRenderDistance(blockEntity, pos);
    }
}
