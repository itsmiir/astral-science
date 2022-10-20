package com.miir.astralscience.mixin.tick;

import com.miir.astralscience.entity.effect.AstralStatusEffects;
import com.miir.astralscience.item.AstralItems;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class AstralEntityTickMixin {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);
    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Inject(method = "tick", at = @At("TAIL"))
    private void updateNephryllBoots(CallbackInfo ci) {
        ItemStack stack = this.getEquippedStack(EquipmentSlot.FEET);
        if (stack.getItem().equals(AstralItems.NEPHRYLL_BOOTS)) {
            this.addStatusEffect(new StatusEffectInstance(AstralStatusEffects.GROUNDED, 2, 0, false, false, false));
        }
    }
    @Inject(at = @At("HEAD"), method = "tick")
    public void updateDeepFreeze(CallbackInfo ci) {
        if (!(((LivingEntity) (Object) this).world == null)) {
            if (!((LivingEntity) (Object) this).world.isClient && !((LivingEntity) (Object) this).isDead()) {
                if (AstralDimensions.isSuperCold(((LivingEntity) (Object) this).world, ((LivingEntity) (Object) this).getBlockPos()) && AstralDimensions.canDeepFreeze((LivingEntity) (Object) this)) {
                    ((LivingEntity) (Object) this).setFrozenTicks(400);
                }
            }
            if (!((LivingEntity) (Object) this).world.isClient && ((LivingEntity) (Object) this).age % 40 == 0 && ((LivingEntity) (Object) this).getFrozenTicks() > 300 && AstralDimensions.canDeepFreeze(((LivingEntity) (Object) this))) {
                ((LivingEntity) (Object) this).damage(DamageSource.FREEZE, (float) 1);
            }
        }
    }
}
