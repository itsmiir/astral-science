package com.miir.astralscience.mixin.gravity;

import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ParrotEntity.class)
public abstract class ParrotGravityMixin extends TameableShoulderEntity {

    protected ParrotGravityMixin(EntityType<? extends TameableShoulderEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(
            method = "flapWings",
            at = @At(
                    target = "Lnet/minecraft/entity/passive/ParrotEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
                    value = "INVOKE"
            )
    )
    private void parrotGravity(ParrotEntity self, Vec3d velocity) {
        if (AstralDimensions.isOrbit(self.world)) {
            self.setVelocity(self.getVelocity());
        }
    }
}
