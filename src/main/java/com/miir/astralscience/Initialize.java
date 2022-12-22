package com.miir.astralscience;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.entity.InteractableHologramEntity;
import com.miir.astralscience.entity.effect.AstralStatusEffects;
import com.miir.astralscience.item.AstralItems;
import com.miir.astralscience.magic.rune.AstralRunes;
import com.miir.astralscience.recipe.Recipe;
import com.miir.astralscience.sound.AstralSounds;
import com.miir.astralscience.tag.AstralTags;
import com.miir.astralscience.world.gen.AstralWorldgen;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.World;

public class Initialize {
    public static void register() {
//        tags should be registered first because some other features rely on them
        AstralTags.register();

        Recipe.register();

//        blocks & item
        AstralBlocks.register();
        AstralItems.register();

        InteractableHologramEntity.HOLOGRAM = Registry.register(
                Registries.ENTITY_TYPE, AstralScience.id("hologram"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<InteractableHologramEntity> type, World world) -> new InteractableHologramEntity(world))
                        .dimensions(EntityDimensions.changing(10, 10))
                        .trackRangeBlocks(200)
                        .trackedUpdateRate(10)
                        .build());
//        worldgen
        AstralWorldgen.register();
//        AstralFeatures.register();

//        sounds
        AstralSounds.register();

//        magic
        AstralRunes.register();
        AstralStatusEffects.register();
    }
}
