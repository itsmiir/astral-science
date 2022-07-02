package com.miir.astralscience.mixin;

import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Structure.class)
public interface StructureAccessor {
//    not sure why this was here lol
//    @Accessor("STRUCTURE_TO_GENERATION_STEP")
//    static Map<StructureFeature<?>, GenerationStep.Feature> getStructToGenStep() {
//        return null;
//    }
//
//    @Accessor("STRUCTURE_TO_GENERATION_STEP")
//    void setStructToGenStep(Map<StructureFeature<?>, GenerationStep.Feature> value);
}
