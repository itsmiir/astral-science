package com.miir.astralscience.mixin;

import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.world.SaveProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Shadow @Final protected SaveProperties saveProperties;

    @Inject(method = "createWorlds", at = @At("TAIL"))
    private void astralscience$swipeSeed(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) {
        AstralDimensions.SEED = this.saveProperties.getGeneratorOptions().getSeed();
    }
}
