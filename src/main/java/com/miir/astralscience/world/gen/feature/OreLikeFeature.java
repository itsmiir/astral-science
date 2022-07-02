package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.world.BlockArray;
import com.miir.astralscience.world.gen.stateprovider.SimpleStateProvider;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import org.jetbrains.annotations.Nullable;

public class OreLikeFeature extends AbstractFeature {
    //    because screw the built-in one
    private final int minY;
    private final int maxY;
    private final int size;
    @Nullable
    private final TagKey<Block> overwriteable;
    private final BlockState oreBlock;

    public OreLikeFeature(Codec<DefaultFeatureConfig> configCodec, int minY, int maxY, int size, @Nullable TagKey<Block> replaceableBlocks, BlockState oreBlock) {
        super();
        this.maxY = maxY;
        this.minY = minY;
        this.overwriteable = replaceableBlocks;
        this.size = size;
        this.oreBlock = oreBlock;
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos pos = new BlockPos(context.getOrigin().getX() + random.nextInt(15), this.minY + random.nextInt(this.maxY + 1 - this.minY), context.getOrigin().getZ() + random.nextInt(15));
        BlockArray blocks = makeBlob(context, pos);
        blocks.build(context, new SimpleStateProvider(oreBlock));
        return true;
    }

    protected BlockArray makeBlob(FeatureContext<? extends FeatureConfig> context, BlockPos startPos) {
        float r = 0.5f;
        BlockArray array = new BlockArray();
        BlockPos pos = startPos;
        BlockPos posNeg = startPos;
        if (isReplaceable(context, startPos)) {
            array.add(startPos);
        }
        for (BlockPos pos1 :
                BlockPos.iterateOutwards(startPos, (context.getRandom().nextInt(this.size) * 2) + 2, (context.getRandom().nextInt(this.size) * 2) + 2, (context.getRandom().nextInt(this.size) * 2) + 2)) {
            array.add(pos1.toImmutable());
            if (array.size() >= context.getRandom().nextInt(this.size * 3) + 3) {
                break;
            }
        }
//        for (int i = 0; i < AstralMath.cbrt(size); i++) {
//            for (int j = 0; j < AstralMath.cbrt(size); j++) {
//                for (int k = 0; k < AstralMath.cbrt(size); k++) {
//                    if (context.getRandom().nextFloat() < AstralMath.pow(r, k) && isReplaceable(context, pos.add(1, 0, 0))) {
//                        array.add(pos.add(1, 0, 0));
//                        pos = pos.add(1, 0, 0);
//                    }
//                    if (context.getRandom().nextFloat() < AstralMath.pow(r, k) && isReplaceable(context, posNeg.subtract(new Vec3i(1, 0, 0)))) {
//                        array.add(posNeg.subtract(new Vec3i(1, 0, 0)));
//                        posNeg = posNeg.subtract(new Vec3i(1, 0, 0));
//                    }
//                }
//
//                if (context.getRandom().nextFloat() < AstralMath.pow(r, j) && isReplaceable(context, pos.add(0, 1, 0))) {
//                    array.add(pos.add(0, 1, 0));
//                    pos = pos.add(0, 1, 0);
//                }
//                if (context.getRandom().nextFloat() < AstralMath.pow(r, j) && isReplaceable(context, posNeg.subtract(new Vec3i(0, 1, 0)))) {
//                    array.add(posNeg.subtract(new Vec3i(0, 1, 0)));
//                    posNeg = posNeg.subtract(new Vec3i(0, 1, 0));
//                }
//            }
//
//            if (context.getRandom().nextFloat() < AstralMath.pow(r, i) && isReplaceable(context, pos.add(0, 0, 1))) {
//                array.add(pos.add(0, 0, 1));
//                pos = pos.add(0, 0, 1);
//            }
//            if (context.getRandom().nextFloat() < AstralMath.pow(r, i) && isReplaceable(context, posNeg.subtract(new Vec3i(0, 0, 1)))) {
//                array.add(posNeg.subtract(new Vec3i(0, 0, 1)));
//                posNeg = posNeg.subtract(new Vec3i(0, 0, 1));
//            }
//        }
        return array;
    }

    protected void makeVein() {

    }

    protected boolean isReplaceable(FeatureContext<? extends FeatureConfig> context, BlockPos pos) {
        if (this.overwriteable == null) {
            return true;
        } else {
            return context.getWorld().getBlockState(pos).isIn(this.overwriteable);
        }
    }
}
