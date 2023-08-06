package com.miir.astralscience;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.entity.effect.AstralStatusEffects;
import com.miir.astralscience.item.AstralItems;
import com.miir.astralscience.magic.rune.AstralRunes;
import com.miir.astralscience.recipe.Recipe;
import com.miir.astralscience.sound.AstralSounds;
import com.miir.astralscience.tag.AstralTags;
import com.miir.astralscience.world.gen.AstralWorldgen;

public class Initialize {
    public static void register() {
//        tags should be registered first because some other features rely on them
        AstralTags.register();

        Recipe.register();

//        blocks & item
        AstralBlocks.register();
        AstralItems.register();
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
