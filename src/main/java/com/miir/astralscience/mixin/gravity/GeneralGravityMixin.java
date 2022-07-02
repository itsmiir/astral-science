package com.miir.astralscience.mixin.gravity;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.Config;
import com.miir.astralscience.entity.effect.AstralStatusEffects;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;


@Mixin(LivingEntity.class)
public abstract class GeneralGravityMixin extends Entity {

    @Shadow public abstract Iterable<ItemStack> getArmorItems();

    @Shadow @Final private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

    private GeneralGravityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    /**
    * Changes the gravitational attraction for any given body, by changing how fast cycle velocity is
    */
//    travel(Lnet/minecraft/util/math/Vec3d;)V
    @ModifyVariable(method = "travel",
                    at = @At(
                        value = "INVOKE_ASSIGN",
                        target = "Lnet/minecraft/entity/LivingEntity;hasNoGravity()Z",
                        ordinal = 1
                    ),
            index = 2
//            indices: double d (gravity constant) = 2
            )
    private double LivingEntityMixin(double original) {
        if (this.world != null) {
            if (this.world.getRegistryKey().getValue().getNamespace().equals(AstralScience.MOD_ID)) {
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