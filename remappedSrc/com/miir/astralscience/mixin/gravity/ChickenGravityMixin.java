package com.miir.astralscience.mixin.gravity;

import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChickenEntity.class)
public class ChickenGravityMixin extends AnimalEntity {
    protected ChickenGravityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
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
        if (AstralDimensions.isOrbit(self.world)) {
            self.setVelocity(self.getVelocity());
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
