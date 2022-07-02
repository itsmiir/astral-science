package com.miir.astralscience.mixin;

import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class DeepFreezeMixin {
    @Inject(
            at = @At("HEAD"),
            method = "tick"
    )
    public void mixin(CallbackInfo ci) {
        if (!(((LivingEntity) (Object) this).world == null)) {
            if (!((LivingEntity) (Object) this).world.isClient && !((LivingEntity) (Object) this).isDead()) {
                if (AstralDimensions.isSuperCold(((LivingEntity) (Object) this).world, ((LivingEntity) (Object) this).getBlockPos()) && AstralDimensions.canDeepFreeze((LivingEntity) (Object) this)) {
                    ((LivingEntity) (Object) this).setFrozenTicks(400);
                }
            }
            if (!((LivingEntity) (Object) this).world.isClient && ((LivingEntity) (Object) this).age % 40 == 0 && ((LivingEntity) (Object) this).getFrozenTicks() > 300 && AstralDimensions.canDeepFreeze(((LivingEntity) (Object) this))) {
                ((LivingEntity) (Object) this).damage(DamageSource.FREEZE, (float) 1);
            }
//        if (((LivingEntity)(Object)this) instanceof PlayerEntity) {
//            ((PlayerEntity) (Object) this).sendMessage(Text.of(String.valueOf(((LivingEntity) (Object) this).getFrozenTicks())), true);
        }
    }
}
