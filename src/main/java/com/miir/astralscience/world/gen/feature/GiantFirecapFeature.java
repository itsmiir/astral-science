package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.tag.AstralTags;
import com.miir.astralscience.world.BlockArray;
import com.miir.astralscience.world.gen.stateprovider.FirecapCanopyProvider;
import com.miir.astralscience.world.gen.stateprovider.FirecapDecorationProvider;
import com.miir.astralscience.world.gen.stateprovider.SimpleStateProvider;
import net.minecraft.block.Blocks;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class GiantFirecapFeature extends AbstractBranchingPlantFeature {
    public static float VINE_CHANCE = 0.95F;

    public GiantFirecapFeature() {
        super(1, 15, 10, 0.25F, 3, true, 0.5F, 0.15F, new SimpleStateProvider(AstralBlocks.FIRECAP_HYPHAE.getDefaultState()), new FirecapCanopyProvider(), new FirecapDecorationProvider(), AstralTags.GIANT_FIRECAP_REPLACEABLE, true);
    }

    @Override
    protected BlockPos start(FeatureContext<DefaultFeatureConfig> context) {
        return AstralFeatures.findNextFloor(context.getWorld(), context.getOrigin(), 100);
    }

    @Override
    protected BlockArray buildCanopy(FeatureContext<DefaultFeatureConfig> context, BlockPos tip, BlockArray stem) {
        Random random = context.getRandom();
        int x = tip.getX();
        int y = tip.getY() + 1;
        int z = tip.getZ();
        int r = 5;
        BlockArray canopy = new BlockArray();
        for (int j = 0; j < r; j++) {
            for (int k = 0; k < r; k++) {
                canopy.add(new BlockPos(tip.getX() - 2 + k, y, tip.getZ() - 2 + j), 1);
            }
        }
        canopy.remove(new BlockPos(x - 2, y, z - 2));
        canopy.remove(new BlockPos(x - 2, y, z + 2));
        canopy.remove(new BlockPos(x + 2, y, z - 2));
        canopy.remove(new BlockPos(x + 2, y, z + 2));
        int c = random.nextInt(2) + 3;
        for (int j = 0; j < c; j++) {
            canopy.add(new BlockPos(x, y + 1 + j, z));
        }

        for (int k = 0; k < c - 2 + random.nextInt(2); k++) {
            canopy.add(new BlockPos(x - 1, y + 1 + k, z));
        }
        for (int k = 0; k < c - 2 + random.nextInt(2); k++) {
            canopy.add(new BlockPos(x + 1, y + 1 + k, z));
        }
        for (int k = 0; k < c - 2 + random.nextInt(2); k++) {
            canopy.add(new BlockPos(x, y + 1 + k, z - 1));
        }
        for (int k = 0; k < c - 2 + random.nextInt(2); k++) {
            canopy.add(new BlockPos(x, y + 1 + k, z + 1));
        }

        for (int k = 0; k < c - 2 + random.nextInt(2); k++) {
            canopy.add(new BlockPos(x - 1, y + 1 + k, z - 1));
        }
        for (int k = 0; k < c - 2 + random.nextInt(2); k++) {
            canopy.add(new BlockPos(x - 1, y + 1 + k, z + 1));
        }
        for (int k = 0; k < c - 2 + random.nextInt(2); k++) {
            canopy.add(new BlockPos(x + 1, y + 1 + k, z - 1));
        }
        for (int k = 0; k < c - 2 + random.nextInt(2); k++) {
            canopy.add(new BlockPos(x + 1, y + 1 + k, z + 1));
        }
        return canopy;
    }

    @Override
    protected void decorate(FeatureContext<DefaultFeatureConfig> context, BlockStateProvider decorationProvider, BlockArray stem, BlockArray canopy) {
        for (BlockPos pos :
                stem) {
            BlockPos test = new BlockPos(pos.toImmutable());
            if (context.getRandom().nextFloat() < VINE_CHANCE) {
                Direction direction = Direction.random(context.getRandom());
                BlockPos offset = test.offset(direction);
                int i = 0;
                while (!context.getWorld().getBlockState(offset).isOf(Blocks.AIR) && i < 24) {
//                    ^^ epic loop exit (doubles as a random way to getFlags slightly wacky trees lol)

//                    future em here. not sure what that^ means. leaving it for posterity.
                    direction = Direction.random(context.getRandom());
                    offset = test.offset(direction);
                    i++;
                }
                if (context.getWorld().getBlockState(offset).isOf(Blocks.AIR)) {
                    try {
                        context.getWorld().setBlockState(offset, (((MultifaceGrowthBlock) AstralBlocks.BLUEMOSS).withDirection(context.getWorld().getBlockState(offset), context.getWorld(), offset, direction.getOpposite())), 3);
                    } catch (Exception ignored) {
                        return;
                    }
                }
            }
        }
    }
}
