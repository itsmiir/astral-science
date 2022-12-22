package com.miir.astralscience.mixin;

import com.miir.astralscience.tag.AstralTags;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Carver.class)
public abstract class CarverMixin<C extends CarverConfig> {

    @Inject(method = "canAlwaysCarveBlock", at = @At(value = "HEAD"), cancellable = true)
    private void setBlocksAsCarvable(C config, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.isIn(AstralTags.CARVABLE)) {
            cir.setReturnValue(true);
        }
    }
}
