package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.tag.AstralTags;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class FirecapPatchFeature extends Feature<DefaultFeatureConfig> {
    public FirecapPatchFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        try {
            StructureWorldAccess access = context.getWorld();
            Random random = context.getRandom();
            BlockPos pos = context.getOrigin();
            pos = pos.add(random.nextInt(15), random.nextInt(15), random.nextInt(15));
//            pos = AstralFeatures.findNextFloor(access, pos);
            if (!pos.equals(BlockPos.ORIGIN) && pos.getY() < 100) {
                if (isOnSnowOrIce(access, pos)) {
                    access.setBlockState(pos.add(0, 2, 0), AstralBlocks.FIRECAP.getDefaultState(), 3);
                }
                for (int i = 0; i < 20; i++) {
                    BlockPos pos1 = pos.add(random.nextInt(5), random.nextInt(5), random.nextInt(5));
                    if (isOnSnowOrIce(access, pos1)) {
                        access.setBlockState(pos1.add(0, 2, 0), AstralBlocks.FIRECAP.getDefaultState(), 3);
                    }
                    BlockPos pos2 = pos.subtract(new Vec3i(random.nextInt(5), random.nextInt(5), random.nextInt(5)));
                    if (isOnSnowOrIce(access, pos2)) {
                        access.setBlockState(pos2.add(0, 2, 0), AstralBlocks.FIRECAP.getDefaultState(), 3);
                    }
                }
                return true;
            }
        } catch (RuntimeException ignored) {
//            do not want to deal with problems
        }
        return false;
    }
    public static boolean isOnSnowOrIce(StructureWorldAccess access, BlockPos pos) {
        return (access.getBlockState(pos.subtract(new Vec3i(0, -1, 0))).isIn(BlockTags.SNOW) ||
                access.getBlockState(pos.subtract(new Vec3i(0, -1, 0))).isOf(Blocks.PACKED_ICE)) &&
                (access.getBlockState(pos.add(0, 2, 0)).isIn(AstralTags.AIR));
    }
}
