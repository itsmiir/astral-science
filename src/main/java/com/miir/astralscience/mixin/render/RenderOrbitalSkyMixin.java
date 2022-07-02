package com.miir.astralscience.mixin.render;

import com.miir.astralscience.client.render.AstralSkyEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
@Mixin(ClientWorld.class)
public class RenderOrbitalSkyMixin {
    @Mutable
    @Shadow @Final private DimensionEffects dimensionEffects;
    @Inject(
            at = @At(value = "INVOKE_ASSIGN", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/render/DimensionEffects;byDimensionType(Lnet/minecraft/world/dimension/DimensionType;)Lnet/minecraft/client/render/DimensionEffects;"),
            method = "<init>"
    )
    private void mixin(ClientPlayNetworkHandler networkHandler, ClientWorld.Properties properties, RegistryKey<World> key, RegistryEntry<DimensionType> dimensionTypeEntry, int loadDistance, int simulationDistance, Supplier profiler, WorldRenderer worldRenderer, boolean debugWorld, long seed, CallbackInfo ci) {
        dimensionEffects  = AstralSkyEffects.getOrReturnOriginal(dimensionTypeEntry.value(), dimensionEffects);
        }
    }

