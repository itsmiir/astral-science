package com.miir.astralscience.mixin.tick;

import com.miir.astralscience.world.dimension.AstralDimensions;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayerEntity.class)
public class PreventElytraOpeningMixin extends PlayerEntity {

    public PreventElytraOpeningMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    @Inject(
            at = @At("TAIL"),
            method = "tickMovement",
            locals = LocalCapture.PRINT
    )

//    @ModifyVariable(
//            method = "tickMovement",
//            at = @At(
//                    value = "INVOKE_ASSIGN",
//                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;",
//                    shift = At.Shift.AFTER
//            ),
//            index = 1
//    )
    private void mixin(CallbackInfo ci) {
        if (this.world != null) {
            if (AstralDimensions.hasAtmosphere(this.world)) {
//                return new ItemStack(Blocks.AIR);
            }
        }
//        return original;
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
