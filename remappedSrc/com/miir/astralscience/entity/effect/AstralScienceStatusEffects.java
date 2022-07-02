package com.miir.astralscience.entity.effect;

import com.miir.astralscience.AstralScience;
import net.minecraft.util.registry.Registry;

public class AstralScienceStatusEffects {

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, AstralScience.id("grounded"), AstralScience.GROUNDED);
    }
}
