package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.tag.AstralTags;
import com.miir.astralscience.world.BlockArray;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class AbstractBranchingPlantFeature extends AbstractFeature<BranchingPlantFeatureConfig> {
//    because fuck trees
//    future miir here, trees are fine, i was having a bit of a moment, sorry
//    even more future miir here, i think first miir had a point. trees suck
//    todo: properly implement branches
    private ArrayList<TreeTip> tips = new ArrayList<>();
    private BlockArray unbakedMap = new BlockArray();
    private BlockArray roots = new BlockArray();
    private BlockPos origin;

    public AbstractBranchingPlantFeature(Codec<BranchingPlantFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext context) {
        BranchingPlantFeatureConfig config = (BranchingPlantFeatureConfig) context.getConfig();
        this.origin = this.start(context).toImmutable();
        if (!config.adjacentAllowed()) {
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

        for (int i = 0; i < Math.min(random.nextInt(config.avgTrunkSize()) * 2 + config.minTrunkHeight(), config.maxTrunkSize()); i++) {
            this.step(context);
        }
        BlockArray canopy = this.buildCanopy(context);
        for (BlockPos pos :
                this.unbakedMap) {
            if (cannotPlaceAt(context, pos, AstralTags.GIANT_PLANT_REPLACEABLE)) {
                return false;
            }
        }
        this.unbakedMap.build(context, new SimpleBlockStateProvider(Registries.BLOCK.get(config.trunkProvider()).getDefaultState()));
        BlockArray canopyCopy = canopy.copy();
        for (BlockPos pos :
                canopy) {
            if (cannotPlaceAt(context, pos, AstralTags.GIANT_PLANT_REPLACEABLE)) {
                canopyCopy.remove(pos);
            }
        }
        canopy = canopyCopy.copy();

        canopy.build(context, new SimpleBlockStateProvider(Registries.BLOCK.get(config.canopyProvider()).getDefaultState()));
        this.buildRoots(context);
        this.decorate(context, new SimpleBlockStateProvider(Registries.BLOCK.get(config.decorationProvider()).getDefaultState()), this.unbakedMap, canopy);
        return true;
    }

    protected abstract void buildRoots(FeatureContext<BranchingPlantFeatureConfig> context);

    protected void decorate(FeatureContext<BranchingPlantFeatureConfig> context, BlockStateProvider decorationProvider, BlockArray stem, BlockArray canopy) {
    }

    protected abstract BlockPos start(FeatureContext<BranchingPlantFeatureConfig> context);

    protected void step(FeatureContext<BranchingPlantFeatureConfig> context) {
        BranchingPlantFeatureConfig config = context.getConfig();
        net.minecraft.util.math.random.Random random = context.getRandom();
        if (this.tips.size() == 1 && this.unbakedMap.size() <= config.minTrunkHeight()) {
            ArrayList<TreeTip> newTips = new ArrayList<>();
            for (TreeTip tip :
                    this.tips) {
                BlockPos newTip = tip.getPos().offset(Direction.UP).toImmutable();
                Direction direction = Direction.random(random);
                if (config.canBend() && random.nextFloat() < config.bendChance()) {
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
        } else if (!(this.unbakedMap.size() >= config.maxTrunkSize())) {
            if (!(this.tips.size() >= config.maxBranches()) && context.getRandom().nextFloat() < config.branchChance()) {
                TreeTip budTip = this.tips.get(random.nextInt(this.tips.size()));
                BlockPos budPos = budTip.getPos();

//                generate a blockPos one block in a cardinal direction away from the origin
                BlockPos offset = this.pushOut(random, budTip, false, config.tallness()).toImmutable();
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
                this.tips.add(new TreeTip(tip.toImmutable(), Direction.fromVector(offset.getX(), offset.getY(), offset.getZ())));
            } else {
                ArrayList<TreeTip> newTips = new ArrayList<>();
                for (TreeTip tip :
                        this.tips) {
                    BlockPos offset = this.pushOut(random, tip, true, config.tallness());
                    BlockPos newTip = tip.getPos().add(offset);
                    this.unbakedMap.add(newTip);
                    newTips.add(new TreeTip(newTip, Direction.fromVector(offset.getX(), offset.getY(), offset.getZ())));
                }
                this.tips = newTips;
            }
        }
    }

    @NotNull
    private BlockPos pushOut(Random random, TreeTip treeTip, boolean canGrowUp, float tallness) {
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
            Direction direction;
            do {
                direction = Direction.random(random);
            } while (direction == Direction.DOWN || direction == Direction.UP);
            offset = BlockPos.ORIGIN.offset(direction);
        }
        return offset.toImmutable();
    }

    private BlockArray buildCanopy(FeatureContext<BranchingPlantFeatureConfig> context) {
        BlockArray canopy = new BlockArray();
        for (TreeTip tip :
                this.tips) {
            canopy.add(this.buildCanopy(context, tip.getPos(), this.unbakedMap));
        }
        return canopy;
    }
    protected abstract BlockArray buildCanopy(FeatureContext<BranchingPlantFeatureConfig> context, BlockPos tip, BlockArray stem);

    protected static class TreeTip {
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
    public static final Object2ObjectArrayMap<Identifier, BlockStateProvider> STATE_PROVIDER_MAP = new Object2ObjectArrayMap<Identifier, BlockStateProvider>(
            new Identifier[]{AstralScience.id("wormwood_log")},
            new BlockStateProvider[]{BlockStateProvider.of(AstralBlocks.WORMWOOD_LOG)}
    );
}
