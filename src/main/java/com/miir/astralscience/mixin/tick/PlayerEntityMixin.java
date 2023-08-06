package com.miir.astralscience.mixin.tick;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract void sendMessage(Text message, boolean actionBar);

    @Shadow public abstract void stopFallFlying();

    @Inject(method = "tick", at = @At("TAIL"))
    private void astralTicks(CallbackInfo ci) {
        World world = this.getWorld();
        if (!AstralDimensions.hasAtmosphere(world, false)) {
            if (this.isFallFlying()) {
                this.stopFallFlying();
            }
            if (AstralScience.isSuffocating(this, world)) {
                this.sendMessage(Text.translatable("message.astralscience.suffocating"), true);
            }
        }
    }
}
