package com.miir.astralscience.mixin.tick;

import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class UpdateOxygenMixin extends Entity {
    @Shadow public abstract void sendMessage(Text message, boolean actionBar);

    public UpdateOxygenMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void updateOxygen(CallbackInfo ci) {
        World world = this.world;
        Biome biome = this.getEntityWorld().getBiome(this.getBlockPos()).value();
        if (AstralDimensions.isOrbit(world)) {
            this.sendMessage(Text.translatable("message.astralscience.suffocating"), true);
        }
    }
}
