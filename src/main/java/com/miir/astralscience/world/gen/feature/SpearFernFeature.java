package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.tag.AstralTags;
import com.mojang.serialization.Codec;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import static com.miir.astralscience.block.SpearFernBlock.SECTION;
import static net.minecraft.block.Block.NOTIFY_LISTENERS;

public class SpearFernFeature extends AbstractFeature<DefaultFeatureConfig> {
    public SpearFernFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext context) {
        try {
            StructureWorldAccess access = context.getWorld();
            BlockPos pos = context.getOrigin();
            pos = AstralFeatures.findNextFloor(access, pos);
            if (!pos.equals(BlockPos.ORIGIN) && pos.getY() < 100) {
                if (canPlaceAt(access, pos)) {
                    access.setBlockState(pos.up(0), AstralBlocks.SPEAR_FERN.getDefaultState(), 3);
                    access.setBlockState(pos.up(1), AstralBlocks.SPEAR_FERN.getDefaultState().with(SECTION, 1), NOTIFY_LISTENERS);
                    access.setBlockState(pos.up(2), AstralBlocks.SPEAR_FERN.getDefaultState().with(SECTION, 2), NOTIFY_LISTENERS);
                }
                return true;
            }
        } catch (RuntimeException ignored) {
        }
        return false;
    }
    public static boolean canPlaceAt(StructureWorldAccess access, BlockPos pos) {
        return ((
                        access.getBlockState(pos.down()).isIn(BlockTags.DIRT) ||
                        access.getBlockState(pos.down()).isIn(BlockTags.SNOW)))
                && (access.getBlockState(pos.up(0)).isIn(AstralTags.AIR)
                && access.getBlockState(pos.up(1)).isIn(AstralTags.AIR)
                && access.getBlockState(pos.up(2)).isIn(AstralTags.AIR)
                );
    }
}
