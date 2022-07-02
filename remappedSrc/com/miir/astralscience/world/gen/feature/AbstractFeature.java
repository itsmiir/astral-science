package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.tag.AstralTags;
import net.minecraft.block.Block;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFeature extends Feature<DefaultFeatureConfig> {
    public AbstractFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public abstract boolean generate(FeatureContext<DefaultFeatureConfig> context);

    public static boolean isInWater(BlockView world, BlockPos pos) {
        return world.getFluidState(pos).isIn(FluidTags.WATER);
    }

    public static boolean canPlaceAt(FeatureContext<? extends FeatureConfig> context, BlockPos pos, @Nullable TagKey<Block> replaceable) {
        if (replaceable != null) {
            return context.getWorld().getBlockState(pos).isIn(replaceable);
        }
        return !context.getWorld().getBlockState(pos).isIn(AstralTags.INDESTRUCTIBLE);
    }
}
