package com.miir.astralscience.mixin.tick;

import com.miir.astralscience.entity.effect.AstralStatusEffects;
import com.miir.astralscience.item.AstralItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class UpdateNephryllBootsMixin {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Inject(method = "tick", at = @At("TAIL"))
    private void updateNephryllBoots(CallbackInfo ci) {
        ItemStack stack = this.getEquippedStack(EquipmentSlot.FEET);
        if (stack.getItem().equals(AstralItems.NEPHRYLL_BOOTS)) {
            ((PlayerEntity)(Object)this).addStatusEffect(new StatusEffectInstance(AstralStatusEffects.GROUNDED, 2, 0, false, false, false));
        }
    }
}
