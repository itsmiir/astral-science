package com.miir.astralscience.tag;

import com.miir.astralscience.AstralScience;
import net.fabricmc.fabric.api.tag.TagRegistry;

public class AstralScienceTags {
    public static void register() {
        AstralScience.SCREWABLE_BLOCKS = TagRegistry.block(AstralScience.id("screwable_blocks"));
        AstralScience.UNSTARSHIPPABLE = TagRegistry.block(AstralScience.id("unstarshippable"));
        AstralScience.STARSHIP_REPLACEABLE = TagRegistry.block(AstralScience.id("starship_replaceable"));

        AstralScience.GRAVITY_AGNOSTIC = TagRegistry.item(AstralScience.id("gravity_agnostic"));
        AstralScience.WARP_FUEL = TagRegistry.item(AstralScience.id("warp_fuel"));
    }
}
