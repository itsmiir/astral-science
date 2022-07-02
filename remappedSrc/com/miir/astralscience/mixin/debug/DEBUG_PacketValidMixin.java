package com.miir.astralscience.mixin.debug;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ServerPlayNetworkHandler.class)
public class DEBUG_PacketValidMixin {
    /**
     * Interestingly enough, this method returns true if the packet is invalid.
     * @author Miir
     * @reason I accidentally teleported myself to an invalid location. Oops
     */
    @Overwrite
    private static boolean validatePlayerMove(PlayerMoveC2SPacket packet) {
        return false;
    }
}
