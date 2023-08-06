package com.miir.astralscience.mixin.gravity;

import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity {
    protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(
            method = "tickMovement()V",
            at = @At(
                    target = "Lnet/minecraft/entity/passive/ChickenEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
                    value = "INVOKE"
            )
    )
    private void parrotGravity(ChickenEntity self, Vec3d velocity) {
        if (AstralDimensions.isOrbit(self.getWorld())) {
            self.setVelocity(self.getVelocity());
        }
    }
}
