package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.world.BlockArray;
import com.miir.astralscience.world.gen.stateprovider.AdvancedBlockStateProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

import java.util.function.Predicate;

public class CupLilyFeature extends AbstractFeature {
    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        net.minecraft.util.math.random.Random random = context.getRandom();
        BlockPos start = findWaterSurface(context);
        int i = random.nextInt(4);
        int r = i + 8;
        BlockArray array = BlockArray.circle(start, r, 1);
        for (BlockPos pos :
                array) {
            if (!(world.getBlockState(pos).isOf(Blocks.WATER) || world.getBlockState(pos).isOf(Blocks.AIR))) {
                return false;
            }
        }
        Direction dir = Direction.NORTH;
        for (int j = 0; j <= random.nextInt(4); j++) {
            dir = dir.rotateYClockwise();
        }
        BlockPos pos;
        BlockPos pos2 = start;
//        cut out the little lily piece
        pos = start.offset(dir, 2);
        array.remove(BlockArray.cuboid(start, pos));
        switch (i) {
            case 3:
                pos2 = pos2.offset(dir);
                pos = pos.offset(dir);
                array.remove(BlockArray.cuboid(start, pos));
            case 2:
                array.remove(BlockArray.cuboid(pos2.offset(dir, 7).offset(dir.rotateYClockwise(), 3), pos2.offset(dir, 8).offset(dir.rotateYCounterclockwise(), 3)));
            case 0:
                pos2 = pos.offset(dir, 2).offset(dir.rotateYCounterclockwise());
                pos = pos.offset(dir).offset(dir.rotateYClockwise());
                array.remove(BlockArray.cuboid(pos, pos2));
                pos2 = pos2.offset(dir, 2).offset(dir.rotateYCounterclockwise());
                pos = pos.offset(dir, 2).offset(dir.rotateYClockwise());
                array.remove(BlockArray.cuboid(pos, pos2));
                break;
            case 1:
                pos2 = start.offset(dir, 2);
                pos = pos2.offset(dir).offset(dir.rotateYClockwise());
                pos2 = pos2.offset(dir, 2).offset(dir.rotateYCounterclockwise());
                array.remove(BlockArray.cuboid(pos, pos2));
                pos = pos2.offset(dir).offset(dir.rotateYClockwise(), 3);
                pos2 = pos2.offset(dir).offset(dir.rotateYCounterclockwise());
                array.remove(BlockArray.cuboid(pos, pos2));
                pos = pos.offset(dir).offset(dir.rotateYClockwise());
                pos2 = pos2.offset(dir, 2).offset(dir.rotateYCounterclockwise());
                array.remove(BlockArray.cuboid(pos, pos2));
                break;
            default:
                break;
        }
        BlockArray water = flood(start, context.getRandom());
        BlockArray petals = new BlockArray();
        petals.add(generatePetal(Direction.NORTH, start.up()));
        petals.add(generatePetal(Direction.WEST, start.up()));
        petals.add(generatePetal(Direction.EAST, start.up()));
        petals.add(generatePetal(Direction.SOUTH, start.up()));
        petals.add(generatePetal(start.up(), Direction.NORTH, Direction.WEST));
        petals.add(generatePetal(start.up(), Direction.WEST, Direction.SOUTH));
        petals.add(generatePetal(start.up(), Direction.SOUTH, Direction.EAST));
        petals.add(generatePetal(start.up(), Direction.EAST, Direction.NORTH));


        if (!(isClear(array, context) && isClear(petals, context))) {
            return false;
        }
        array.build(context, new SimpleBlockStateProvider(Blocks.GREEN_CONCRETE.getDefaultState()));
        water.build(context, new CupLilyDecorationProvider());
        petals.build(context, new CupLilyPetalProvider(random.nextFloat()));
        return true;
    }

    private boolean isClear(BlockArray array, FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        for (BlockPos pos :
                array) {
            if (!(world.getBlockState(pos).isOf(Blocks.AIR) || world.getFluidState(pos).isIn(FluidTags.WATER))) {
                return false;
            }
        }
        return true;
    }

    public static BlockPos findWaterSurface(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos pos = context.getOrigin().east(context.getRandom().nextInt(15)).south(context.getRandom().nextInt(15));
        BlockPos testPos = new BlockPos(pos.getX(), context.getWorld().getDimension().height(), pos.getZ());
        while (!context.getWorld().getBlockState(testPos).isOf(Blocks.WATER)
                && testPos.getY() > context.getWorld().getBottomY()) {
            testPos = testPos.down();
        }
        if (context.getWorld().getBlockState(testPos).isOf(Blocks.WATER)) {
            return testPos;
        }
        return context.getOrigin();
    }

    private BlockArray generatePetal(Direction forward, BlockPos center) {
        BlockPos pos = center;
//        structure block? jigsaw? wake up bro it's 2012 time to build features manually
        BlockArray petal = new BlockArray();
        Direction left = forward.rotateYCounterclockwise();
        Direction right = forward.rotateYClockwise();
        petal
                .add(pos.offset(forward))
                .add(pos.offset(forward, 2))
                .add(pos.offset(forward, 2).offset(left))
                .add(pos.offset(forward, 2).offset(right));
        pos = pos.up().offset(forward, 3);
        petal
                .add(pos)
                .add(pos.offset(right))
                .add(pos.offset(left))
                .add(pos.offset(forward))
                .add(pos.offset(forward).offset(left))
                .add(pos.offset(forward).offset(right));
        pos = pos.up().offset(forward);
        petal
                .add(pos.offset(right, 2))
                .add(pos.offset(left, 2))
                .add(pos.offset(forward))
                .add(pos.offset(forward).offset(left))
                .add(pos.offset(forward).offset(right));
        pos = pos.up().offset(forward);
        petal
                .add(pos.offset(forward))
                .add(pos.offset(forward).offset(left))
                .add(pos.offset(forward).offset(right))
                .add(pos.offset(left, 2))
                .add(pos.offset(right, 2));
        pos = pos.up();
        petal
                .add(pos.offset(forward, 2))
                .add(pos.offset(forward).offset(left))
                .add(pos.offset(forward).offset(right))
                .add(pos.offset(left, 2))
                .add(pos.offset(right, 2));
        pos = pos.up();
        petal
                .add(pos.offset(forward, 2))
                .add(pos.offset(forward).offset(left))
                .add(pos.offset(forward).offset(right))
                .add(pos.offset(left, 2))
                .add(pos.offset(right, 2));
        pos = pos.up().offset(forward);
        petal
                .add(pos.offset(left, 2))
                .add(pos.offset(right, 2))
                .add(pos.offset(forward))
                .add(pos.offset(forward).offset(left))
                .add(pos.offset(forward).offset(right))
                .add(pos.offset(forward, 2))
                .add(pos.offset(forward, 3));
        pos = pos.up().offset(forward);
        petal
                .add(pos.offset(right, 2))
                .add(pos.offset(left, 2))
                .add(pos.offset(forward).offset(left, 2))
                .add(pos.offset(forward).offset(right, 2))
                .add(pos.offset(forward).offset(left))
                .add(pos.offset(forward).offset(right))
                .add(pos.offset(forward, 2).offset(left))
                .add(pos.offset(forward, 2).offset(right));
        pos = pos.down().offset(forward, 3);
        petal
                .add(pos.offset(left))
                .add(pos.offset(right))
                .add(pos.down());
        petal.setFlags(1);
        petal.add(pos.down(2));
        return petal;
    }
    private BlockArray generatePetal(BlockPos center, Direction forward, Direction left) {
        BlockArray petal = new BlockArray();
        BlockPos pos = center;

        petal.add(pos.offset(left).offset(forward));
        pos = pos.up().offset(left, 2).offset(forward, 2);
        petal
                .add(pos)
                .add(pos.offset(forward))
                .add(pos.offset(left))
                .add(pos.offset(left).offset(forward));
        pos = pos.up().offset(left).offset(forward);
        petal
                .add(pos.offset(forward))
                .add(pos.offset(left));
        pos = pos.up();
        petal
                .add(pos.offset(left).offset(forward))
                .add(pos.offset(forward, 2))
                .add(pos.offset(left, 2));
        pos = pos.up();
        petal
                .add(pos.offset(left).offset(forward))
                .add(pos.offset(forward, 2))
                .add(pos.offset(left, 2));
        pos = pos.up();
        petal
                .add(pos.offset(left, 2).offset(forward))
                .add(pos.offset(left).offset(forward, 2))
                .add(pos.offset(forward, 2))
                .add(pos.offset(left, 2));
        pos = pos.up();
        petal
                .add(pos.offset(left, 3))
                .add(pos.offset(forward, 3))
                .add(pos.offset(forward, 3).offset(left, 1))
                .add(pos.offset(forward, 2).offset(left, 2))
                .add(pos.offset(forward, 1).offset(left, 3))
                .add(pos.offset(left, 3).offset(forward, 2))
                .add(pos.offset(left, 3).offset(forward, 3))
                .add(pos.offset(left, 2).offset(forward, 3))
                .add(pos.offset(left, 1).offset(forward, 3))

                .add(pos.offset(left, 4).offset(forward, 3))
                .add(pos.offset(left, 3).offset(forward, 4))
                .add(pos.offset(left, 4).offset(forward, 4))
                .add(pos.offset(left, 4).offset(forward, 5))
                .add(pos.offset(left, 5).offset(forward, 4))
                .add(pos.offset(left, 2).offset(forward, 4))
                .add(pos.offset(left, 4).offset(forward, 2))
                .add(pos.offset(left, 5).offset(forward, 5).down());
        pos = pos.up();
        petal
                .add(pos.offset(forward, 3))
                .add(pos.offset(left, 3))
                .add(pos.offset(left, 1).offset(forward, 4))
                .add(pos.offset(left, 4).offset(forward, 1))
                .add(pos.offset(left, 4).offset(forward, 2))
                .add(pos.offset(left, 2).offset(forward, 4))
                .add(pos.offset(left, 5).offset(forward, 3))
                .add(pos.offset(left, 3).offset(forward, 5));
        petal.setFlags(1);
        petal.add(pos.offset(left, 5).offset(forward, 5).down(3));

        return petal;
    }

    private BlockArray flood(BlockPos center, net.minecraft.util.math.random.Random random) {
        BlockArray water = new BlockArray();
        water
                .add(center.up())
                .add(BlockArray.circle(center.up(2), 4, 0))
                .add(BlockArray.circle(center.up(3), 6, 0))
                .add(BlockArray.circle(center.up(4), 7, 0))
                .add(BlockArray.circle(center.up(5), 7, 0))
                .add(center.up(5).offset(Direction.NORTH, 6))
                .add(center.up(5).offset(Direction.SOUTH, 6))
                .add(center.up(5).offset(Direction.EAST, 6))
                .add(center.up(5).offset(Direction.WEST, 6));
        BlockArray pads = water.getHighest().offset(0, 1, 0);
        pads.retainIf((Predicate<BlockPos>) (pos) -> random.nextFloat() < 0.13f);
        pads.setFlags(1);
        water.add(pads);
        return water;
    }

    private static class CupLilyPetalProvider extends AdvancedBlockStateProvider {
        private CupLilyPetalProvider(float f) {
            this.f = f;
        }
        private static final BlockState PURPLE = AstralBlocks.PURPLE_PETAL.getDefaultState();
        private static final BlockState PINK = AstralBlocks.PINK_PETAL.getDefaultState();
        private static final BlockState WHITE = AstralBlocks.WHITE_PETAL.getDefaultState();
        private static final BlockState PEACH = AstralBlocks.PEACH_PETAL.getDefaultState();
        private static final BlockState BLACK = AstralBlocks.BLACK_PETAL.getDefaultState();
        private final float f;

        @Override
        public BlockState getBlockState(Random random, BlockPos pos, BlockArray array) {
            BlockState petal = PINK;
            if (f >= 0.6) {
                petal = WHITE;
                if (f >= 0.8) {
                    petal = PURPLE;
                    if (f >= 0.95) {
                        petal = PEACH;
                        if (f >= 0.99) {
                            petal = BLACK;
                        }
                    }
                }
            }
            int flag = array.getFlags(pos);
            if (flag == 0) {
                return Blocks.SEA_LANTERN.getDefaultState();
            } else return petal;
        }

        @Override
        public BlockState getBlockState(net.minecraft.util.math.random.Random random, BlockPos pos) {
            return null;
        }
    }
    private static class CupLilyDecorationProvider extends AdvancedBlockStateProvider {

        @Override
        public BlockState getBlockState(net.minecraft.util.math.random.Random random, BlockPos pos, BlockArray array) {
            if (array.getFlags(pos) == 1) {
                return Blocks.LILY_PAD.getDefaultState();
            } else return Blocks.WATER.getDefaultState();
        }
    }
}
