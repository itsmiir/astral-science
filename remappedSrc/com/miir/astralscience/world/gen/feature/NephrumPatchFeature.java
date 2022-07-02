package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.block.NephrumBlock;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class NephrumPatchFeature extends Feature<DefaultFeatureConfig> {
    public NephrumPatchFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        try {
            StructureWorldAccess access = context.getWorld();
            Random random = context.getRandom();
            BlockPos pos = context.getOrigin();
            pos = pos.add(random.nextInt(15), random.nextInt(15), random.nextInt(15));
            pos = AstralFeatures.findNextFloor(access, pos);
            if (!pos.equals(BlockPos.ORIGIN) && pos.getY() < 200) {
                if (isOnGrass(access, pos.toImmutable())) {
                    context.getWorld().setBlockState(pos.add(0, 2, 0), AstralBlocks.NEPHRUM.getDefaultState().with(NephrumBlock.AGE, random.nextInt(NephrumBlock.MAX_AGE + 1)), 3);
                }
                for (int i = 0; i < 20; i++) {
                    BlockPos pos1 = pos.add(random.nextInt(5), random.nextInt(5), random.nextInt(5));
                    if (isOnGrass(access, pos1.toImmutable())) {
                        context.getWorld().setBlockState(pos1.add(0, 2, 0), AstralBlocks.NEPHRUM.getDefaultState().with(NephrumBlock.AGE, random.nextInt(NephrumBlock.MAX_AGE + 1)), 3);
                    }
                    BlockPos pos2 = pos.subtract(new Vec3i(random.nextInt(5), random.nextInt(5), random.nextInt(5)));
                    if (isOnGrass(access, pos2.toImmutable())) {
                        context.getWorld().setBlockState(pos2.add(0, 2, 0), AstralBlocks.NEPHRUM.getDefaultState().with(NephrumBlock.AGE, random.nextInt(NephrumBlock.MAX_AGE + 1)), 3);
                    }
                }
                return true;
            }
        } catch (RuntimeException ignored) {
        }
        return false;
    }
    private boolean isOnGrass(StructureWorldAccess access, BlockPos pos) {
        return (access.getBlockState(pos.subtract(new Vec3i(0, -1, 0))).isOf(Blocks.GRASS_BLOCK) &&
                (access.getBlockState(pos.add(0, 2, 0)).isOf(Blocks.CAVE_AIR) || access.getBlockState(pos.add(0, 2, 0)).isOf(Blocks.AIR)));
    }
}
