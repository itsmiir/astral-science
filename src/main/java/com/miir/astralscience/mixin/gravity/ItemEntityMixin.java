package com.miir.astralscience.mixin.gravity;

import com.miir.astralscience.Config;
import com.miir.astralscience.tag.AstralTags;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ItemEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
                    ordinal = 0
            )
    )
    private void setItemGravity(ItemEntity self, Vec3d velocity) {
        double multiplier = 1.0D;
        if (this.getWorld() != null &! ((ItemEntity)(Object)this).getStack().isIn(AstralTags.GRAVITY_AGNOSTIC) && AstralDimensions.isAstralDimension(this.getWorld())) {
            double gConstant = Config.GRAVITY_OMEIA;
            if (AstralDimensions.isOrbit(this.getWorld())) {
                gConstant = Config.GRAVITY_ORBIT;
            }
            double gravity = 1 / gConstant;
            multiplier = AstralDimensions.gravityCalc(gravity);
        }
        self.setVelocity(self.getVelocity().add(0.0D, -0.04D * (multiplier), 0.0D));
    }
}
