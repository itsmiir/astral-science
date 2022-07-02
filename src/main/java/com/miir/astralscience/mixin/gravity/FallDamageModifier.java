package com.miir.astralscience.mixin.gravity;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.Config;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Entity.class)
public abstract class FallDamageModifier {
    @Shadow public World world;

    @ModifyArg(
            method = "handleFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z"
            ),
            index = 0
    )
            private float mixin(float fallDistance) {
        if (world.getRegistryKey().getValue().getNamespace().equals(AstralScience.MOD_ID)) {
            float gravity = Config.GRAVITY_OMEIA;
            float multiplier = (float) (gravity / 9.81); // hardcoding this because i assume that gEarth will not change, sue me
            return fallDistance * multiplier;
        }
        return fallDistance;
    }
}
