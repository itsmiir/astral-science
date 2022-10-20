package com.miir.astralscience.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.miir.astralscience.AstralClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class StarMixin {
    @ModifyExpressionValue(
            method = "Lnet/minecraft/client/render/WorldRenderer;renderStars(Lnet/minecraft/client/render/BufferBuilder;)Lnet/minecraft/client/render/BufferBuilder$BuiltBuffer;",
            at = @At(value = "CONSTANT", args = {"intValue=1500"}))
    private int multiplyStars(int original) {
        return AstralClient.shouldMultiplyStars() ? original * AstralClient.multiplyStars() : original;
    }
}
