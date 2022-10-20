package com.miir.astralscience.mixin.tick;

import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class PreventElytraMixin {
    @Shadow public ServerPlayerEntity player;

    @Inject(
            at = @At(
                    "TAIL"
            ),
            method = "onClientCommand(Lnet/minecraft/network/packet/c2s/play/ClientCommandC2SPacket;)V"
    )
    public void mixin(ClientCommandC2SPacket packet, CallbackInfo ci) {
        if (this.player.world != null) {
            if (!AstralDimensions.hasAtmosphere(this.player.world, false)) {
                this.player.stopFallFlying();
            }
        }
    }
}
