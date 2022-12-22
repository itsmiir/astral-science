package com.miir.astralscience.entity.effect;

import com.miir.astralscience.AstralScience;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class AstralStatusEffects {

    //    status effects
    public static final StatusEffect GROUNDED = new GroundedStatusEffect();

    public static void register() {
        Registry.register(Registries.STATUS_EFFECT, AstralScience.id("grounded"), GROUNDED);
    }
}
