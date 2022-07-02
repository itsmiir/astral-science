package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.world.BlockArray;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class AbstractBranchingPlantFeature extends AbstractFeature {
//    because screw trees

//    future em here, trees are fine, i was having a bit of a moment, sorry ~em

//    todo: properly implement branches
    private ArrayList<TreeTip> tips = new ArrayList<>();
    private BlockArray unbakedMap = new BlockArray();
    private BlockArray roots = new BlockArray();
    private BlockPos origin;
    private final BlockStateProvider trunkProvider;
    private final BlockStateProvider canopyProvider;
    private final BlockStateProvider decorationProvider;
    private final BlockStateProvider rootProvider;

    private final int maxBranches;
    private final int maxTrunkSize;
    private final int avgTrunkSize;
    private final float branchChance;
    private final int minTrunkHeight;
    private final boolean canBend;
    private final float bendChance;
    private final float tallness;
    private final TagKey<Block> replaceable;
    private final boolean adjacentAllowed;

    private final int avgRootSize;
    private final int maxRootSize;
    private final float rootBranchiness;
    public AbstractBranchingPlantFeature(int maxBranches, int maxTrunkSize, int avgTrunkSize, float branchChance, int minTrunkHeight, boolean canBend, float bendChance, float tallness, BlockStateProvider trunkProvider, BlockStateProvider canopyProvider, BlockStateProvider decorationProvider, TagKey<Block> replaceable, boolean adjacentAllowed) {
        this(maxBranches, maxTrunkSize, avgTrunkSize, branchChance, minTrunkHeight, canBend, bendChance, tallness, trunkProvider, canopyProvider, decorationProvider, replaceable, null, 0, 0, 0, adjacentAllowed);
    }

    public AbstractBranchingPlantFeature(int maxBranches, int maxTrunkSize, int avgTrunkSize, float branchChance, int minTrunkHeight, boolean canBend, float bendChance, float tallness, BlockStateProvider trunkProvider, BlockStateProvider canopyProvider, BlockStateProvider decorationProvider, TagKey<Block> replaceable, BlockStateProvider rootProvider, int maxRoots, int avgRoots, float rootBranchiness, boolean adjacentAllowed) {
        super();
        this.trunkProvider = trunkProvider;
        this.canopyProvider = canopyProvider;
        this.decorationProvider = decorationProvider;
        this.rootProvider = rootProvider;
        this.maxBranches = maxBranches;
        this.maxTrunkSize = maxTrunkSize;
        this.avgRootSize = avgRoots;
        this.maxRootSize = maxRoots;
        this.rootBranchiness = rootBranchiness;
        this.avgTrunkSize = avgTrunkSize;
        this.branchChance = branchChance;
        this.minTrunkHeight = minTrunkHeight;
        this.canBend = canBend;
        this.bendChance = bendChance;
        this.tallness = tallness;
        this.replaceable = replaceable;
        this.adjacentAllowed = adjacentAllowed;
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        this.origin = this.start(context).toImmutable();
        if (!this.adjacentAllowed) {
            for (BlockPos pos:
                 BlockArray.surrounding(this.origin, false, true, false)) {
                if (!context.getWorld().getBlockState(pos).isOf(Blocks.AIR)) {
                    return false;
                }
            }
        }
        this.tips = new ArrayList<>();
        net.minecraft.util.math.random.Random random = context.getRandom();
        this.unbakedMap = new BlockArray();
        this.roots = new BlockArray();
        this.tips.add(new TreeTip(this.origin, context.getRandom()));
        this.unbakedMap.add(this.origin);

        for (int i = 0; i < Math.min(random.nextInt(this.avgTrunkSize) * 2 + this.minTrunkHeight, this.maxTrunkSize); i++) {
            this.step(context);
        }
        BlockArray canopy = this.buildCanopy(context);
        for (BlockPos pos :
                this.unbakedMap) {
            if (!canPlaceAt(context, pos, replaceable)) {
                return false;
            }
        }
        this.unbakedMap.build(context, this.trunkProvider);
        BlockArray canopyCopy = canopy.copy();
        for (BlockPos pos :
                canopy) {
            if (!canPlaceAt(context, pos, replaceable)) {
                canopyCopy.remove(pos);
            }
        }
        canopy = canopyCopy.copy();

        canopy.build(context, canopyProvider);
        this.buildRoots(context);
        this.decorate(context, this.decorationProvider, this.unbakedMap, canopy);
        return true;
    }

    protected void buildRoots(FeatureContext<DefaultFeatureConfig> context) {
    }

    protected void decorate(FeatureContext<DefaultFeatureConfig> context, BlockStateProvider decorationProvider, BlockArray stem, BlockArray canopy) {
    }

    protected abstract BlockPos start(FeatureContext<DefaultFeatureConfig> context);

    public boolean step(FeatureContext<DefaultFeatureConfig> context) {
        net.minecraft.util.math.random.Random random = context.getRandom();
        if (this.tips.size() == 1 && this.unbakedMap.size() <= minTrunkHeight) {
            ArrayList<TreeTip> newTips = new ArrayList<>();
            for (TreeTip tip :
                    this.tips) {
                BlockPos newTip = tip.getPos().offset(Direction.UP).toImmutable();
                Direction direction = Direction.random(random);
                if (canBend && random.nextFloat() < bendChance) {
                    while (direction.equals(Direction.DOWN) || direction.equals(Direction.UP)) {
                        direction = Direction.random(random);
                    }
                    newTip = newTip.offset(direction).toImmutable();
                } else {
                    direction = Direction.UP;
                }
                newTips.add(new TreeTip(newTip, direction));
                this.unbakedMap.add(newTip.toImmutable());
            }
            this.tips = newTips;
            return true;
        } else if (!(this.unbakedMap.size() >= maxTrunkSize)) {
            if (!(this.tips.size() >= maxBranches) && context.getRandom().nextFloat() < branchChance) {
                TreeTip budTip = this.tips.get(random.nextInt(this.tips.size()));
                BlockPos budPos = budTip.getPos();

//                generate a blockPos one block in a cardinal direction away from the origin
                BlockPos offset = this.pushOut(random, budTip, false).toImmutable();
                if (!(this.unbakedMap.contains(budPos.add(offset)))) {
                    this.unbakedMap.add(budPos.add(offset).toImmutable());
                }
                BlockPos tip = new BlockPos(budPos.add(offset));
                for (int i = 0; i < random.nextBetween(1, 3); i++) {
                    tip = tip.add(offset.toImmutable());
                    if (!(this.unbakedMap.contains(tip))) {
                        this.unbakedMap.add(tip.toImmutable());
                    }
                }
                this.tips.add(new TreeTip(tip.toImmutable(), Direction.fromVector(offset)));
            } else {
                ArrayList<TreeTip> newTips = new ArrayList<>();
                for (TreeTip tip :
                        this.tips) {
                    BlockPos offset = this.pushOut(random, tip, true);
                    BlockPos newTip = tip.getPos().add(offset);
                    this.unbakedMap.add(newTip);
                    newTips.add(new TreeTip(newTip, Direction.fromVector(offset)));
                }
                this.tips = newTips;
            }
        }
        return true;
    }

    @NotNull
    private BlockPos pushOut(Random random, TreeTip treeTip, boolean canGrowUp) {
        BlockPos budTip = treeTip.getPos();
        BlockPos offset = budTip.subtract(this.origin);
        int x;
        int z;
        try {
            x = offset.getX() / Math.abs(offset.getX());
        } catch (ArithmeticException e) {
            x = 0;
        }
        try {
            z = offset.getZ() / Math.abs(offset.getZ());
        } catch (ArithmeticException e) {
            z = 0;
        }
//        restrict the offset to one axis
        if (canGrowUp) {
            float f = random.nextFloat();
            if (f > tallness) {
                offset = BlockPos.ORIGIN.offset(treeTip.getDirection());
            } else {
                offset = new BlockPos(0, 1, 0);
            }
        } else {
            offset = BlockPos.ORIGIN.offset(treeTip.getDirection());
        }
        return offset.toImmutable();
    }

    public BlockArray buildCanopy(FeatureContext<DefaultFeatureConfig> context) {
        BlockArray canopy = new BlockArray();
        for (TreeTip tip :
                this.tips) {
            canopy.add(this.buildCanopy(context, tip.getPos(), this.unbakedMap));
        }
        return canopy;
    }
    protected abstract BlockArray buildCanopy(FeatureContext<DefaultFeatureConfig> context, BlockPos tip, BlockArray stem);

    public static class TreeTip {
        private BlockPos pos;
        private final Direction direction;

        public TreeTip(BlockPos pos, net.minecraft.util.math.random.Random random) {
            this.pos = pos;
            this.direction = Direction.random(random);
        }
        public TreeTip(BlockPos pos, Direction direction) {
            this.pos = pos;
            this.direction = direction;
        }

        public void setPos(BlockPos pos) {
            this.pos = pos.toImmutable();
        }
        public BlockPos getPos() {
            return pos.toImmutable();
        }

        public Direction getDirection() {
            return direction;
        }
    }

}
