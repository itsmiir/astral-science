package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.tag.AstralTags;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFeature<FC extends FeatureConfig> extends Feature {
    public AbstractFeature(Codec<FC> codec) {
        super(codec);
    }

    public static boolean isInWater(BlockView world, BlockPos pos) {
        return world.getFluidState(pos).isIn(FluidTags.WATER);
    }

    protected static boolean cannotPlaceAt(FeatureContext<? extends FeatureConfig> context, BlockPos pos, @Nullable TagKey<Block> replaceable) {
        if (replaceable != null) {
            return !context.getWorld().getBlockState(pos).isIn(replaceable);
        }
        return context.getWorld().getBlockState(pos).isIn(AstralTags.INDESTRUCTIBLE);
    }
}
