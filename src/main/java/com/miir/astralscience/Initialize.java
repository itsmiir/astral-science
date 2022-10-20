package com.miir.astralscience;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.client.render.Render;
import com.miir.astralscience.entity.effect.AstralStatusEffects;
import com.miir.astralscience.item.AstralItems;
import com.miir.astralscience.magic.rune.AstralRunes;
import com.miir.astralscience.recipe.Recipe;
import com.miir.astralscience.screen.AstralScreens;
import com.miir.astralscience.sound.AstralSounds;
import com.miir.astralscience.world.gen.Worldgen;
import com.miir.astralscience.world.gen.feature.AstralFeatures;
import com.miir.astralscience.tag.AstralTags;

public class Initialize {
    public static void register() {
//        tags should be registered first because some other features rely on them
        AstralTags.register();

        Recipe.register();
        AstralScreens.register();

//        blocks & items
        AstralBlocks.register();
        AstralItems.register();

//        worldgen
        Worldgen.register();
//        AstralFeatures.register();

//        sounds
        AstralSounds.register();

//        magic
        AstralRunes.register();
        AstralStatusEffects.register();
    }
}
