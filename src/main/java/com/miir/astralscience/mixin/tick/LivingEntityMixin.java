package com.miir.astralscience.mixin.tick;

import com.miir.astralscience.Config;
import com.miir.astralscience.entity.effect.AstralStatusEffects;
import com.miir.astralscience.item.AstralItems;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

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
        if (!(this.world == null)) {
            if (!(this.world.isClient && !((LivingEntity) (Object) this).isDead())) {
                if (AstralDimensions.isSuperCold(this.world, this.getBlockPos()) && AstralDimensions.canDeepFreeze((LivingEntity) (Object) this)) {
                    this.setFrozenTicks(400);
                }
            }
            if (!(this.world.isClient && (this.age % 40 == 0 && (this.getFrozenTicks() > 300 && AstralDimensions.canDeepFreeze(((LivingEntity)(Object)this)))))) {
                this.damage(DamageSource.FREEZE, 1);
            }
        }
    }
    @Shadow public abstract Iterable<ItemStack> getArmorItems();

    @Shadow @Final
    private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;
    /**
     * Changes the gravitational attraction for any given body, by changing how fast cycle velocity is
     */
//    travel(Lnet/minecraft/util/math/Vec3d;)V
    @ModifyVariable(method = "travel", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/LivingEntity;hasNoGravity()Z", ordinal = 1), index = 2
//            indices: double d (gravity constant) = 2
    )
    private double gravity(double original) {
        if (this.world != null) {
            if (AstralDimensions.isAstralDimension(this.world)) {
                try {
                    if (!(this.activeStatusEffects.containsKey(AstralStatusEffects.GROUNDED))) {
//        orbital dimensions have a set gravity
                        if (AstralDimensions.isOrbit(world)) {
                            return original / Config.GRAVITY_ORBIT;
                        }
                        double gravity = Config.GRAVITY_OMEIA;
//        gravity to multiplier: -2E-05x^4 + 0.0015x^3 - 0.0479x^2 + 1.0053x + 0.2765
//        TODO: manipulate variable d to change gravity
                        return original * gravity / 9.8;
                    }

                } catch (NullPointerException e) {
                    return original;
                }
            }
        }
        return original;
    }
}
