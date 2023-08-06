package com.miir.astralscience.world;

import com.miir.astralscience.world.gen.stateprovider.AdvancedBlockStateProvider;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * this class is here to make feature generation more dynamic. basically it's just a list of block
 * positions that can be manipulated in cool ways. since it's primarily for worldgen, the build() method can go through
 * and call setBlockState on each blockpos in the array. each blockpos is also mapped to an int so if you want you
 * can set a bunch of flags for stuff like setting different blocks to different states using BlockStateProviders.
 * you can even use this system for dynamic or procedurally generated structure features; the vanilla method is
 * probably a lot more efficient and intuitive, but i don't want to learn it. you will find that this is my
 * approach to modding, programming, and life in general. DRY can go fuck itself
 */
public class BlockArray implements Iterable<BlockPos>, Cloneable {

    private final Object2IntArrayMap<BlockPos> blocks;

    public BlockArray() {
        this.blocks = new Object2IntArrayMap<>();
    }

    public BlockArray(BlockPos pos) {
        this(pos, 0);
    }

    public BlockArray(BlockPos pos, int flags) {
        this.blocks = new Object2IntArrayMap<>();
        this.blocks.put(pos, flags);
    }

    /**
     * @return a <code>BlockArray</code> of the blocks surrounding the origin, rotated along each axis x, y, and z
     */
    public static BlockArray surrounding(BlockPos origin, boolean x, boolean y, boolean z) {
        BlockArray array = new BlockArray();
        if (x) {
            array.add(origin.north());
            array.add(origin.up());
            array.add(origin.south());
            array.add(origin.down());
            array.add(origin.north().up());
            array.add(origin.north().down());
            array.add(origin.south().up());
            array.add(origin.south().down());
        }
        if (y) {
            array.add(origin.north());
            array.add(origin.east());
            array.add(origin.south());
            array.add(origin.west());
            array.add(origin.north().west());
            array.add(origin.north().east());
            array.add(origin.south().west());
            array.add(origin.south().east());
        }
        if (z) {
            array.add(origin.west());
            array.add(origin.up());
            array.add(origin.east());
            array.add(origin.down());
            array.add(origin.west().up());
            array.add(origin.west().down());
            array.add(origin.east().up());
            array.add(origin.east().down());
        }
        return array;
    }

    /**
     * generates a cuboid centered around the center param. can also be used to generate lines, cubes, and
     * rectangles. the resulting cuboid will always have an odd diameter (one block will be the center).
     *
     * @return a blockArray of the cuboid
     */
    public static BlockArray cuboid(BlockPos center, int rx) {
        return cuboid(center, rx, rx, 0, 0);
    }

    public static BlockArray cuboid(BlockPos center, int rx, int rz) {
        return cuboid(center, rx, rz, 0, 0);
    }

    public static BlockArray cuboid(BlockPos center, int rx, int rz, int ry) {
        return cuboid(center, rx, rz, ry, 0);
    }

    public static BlockArray cuboid(BlockPos center, int rx, int rz, int ry, int flags) {
        BlockArray array = new BlockArray(center);
        BlockPos pos = new BlockPos(center.getX(), center.getY(), center.getZ());
        pos = pos.subtract(new Vec3i(rx, ry, rz));
        for (int i = 0; i < rx * 2 + 1; i++) {
            for (int j = 0; j < ry * 2 + 1; j++) {
                for (int k = 0; k < rz * 2 + 1; k++) {
                    array.add(pos.add(i, j, k), flags);
                }
            }
        }
        return array;
    }

//    creates a cuboid cornered from the blockpos start to the end, inclusive
    public static BlockArray cuboid(BlockPos start, BlockPos end) {
        BlockArray array = new BlockArray();
        BlockPos min = new BlockPos(
                Math.min(start.getX(), end.getX()),
                Math.min(start.getY(), end.getY()),
                Math.min(start.getZ(), end.getZ())
        );
        BlockPos max = new BlockPos(
                Math.max(start.getX(), end.getX()),
                Math.max(start.getY(), end.getY()),
                Math.max(start.getZ(), end.getZ())
        );
        for (int i = min.getX(); i <= max.getX(); i++) {
            for (int j = min.getY(); j <= max.getY(); j++) {
                for (int k = min.getZ(); k <= max.getZ(); k++) {
                    array.add(new BlockPos(i, j, k));
                }

            }
        }
        return array;
    }

    /**
     * creates a circle/sphere of the desired radius. the resulting round thing will always have an odd diameter. if
     * you need something with an even diameter just call the method four times (yes, this will be four times as slow
     * as a "real" implementation (which would actually probably not be too hard to implement, so actually look for a
     * "evenCircle()" method or something of the like. perhaps i coded it in later. knowing how lazy i am, however,
     * this is not likely))
     *
     * @return a blockArray of the sphere/circle
     */
    public static BlockArray circle(BlockPos center, int r, int flags) {
        BlockArray array = cuboid(center, r, r, 0, flags);
        Vec3d middle = Vec3d.ofCenter(center);
        array.removeIf((Predicate<BlockPos>) element -> {
            Vec3d testPos = Vec3d.ofCenter(element);
            double d =
                    Math.hypot(
                            Math.abs(testPos.getX()) - Math.abs(middle.getX()),
                            Math.abs(testPos.getZ()) - Math.abs(middle.getZ()));
            return !(d < r - 1.5);
        });
        return array;
    }
    public static BlockArray sphere(BlockPos center, int r, int flags) {
        BlockArray array = cuboid(center, r, r, r, flags);
        Vec3d middle = Vec3d.ofCenter(center);
        array.removeIf((Predicate<BlockPos>) element -> {
            Vec3d testPos = Vec3d.ofCenter(element);
            double d =
                    Math.hypot(
                            Math.hypot(
                                    Math.abs(testPos.getX()) - Math.abs(middle.getX()),
                                    Math.abs(testPos.getY()) - Math.abs(middle.getY())),
                            Math.abs(testPos.getZ()) - Math.abs(middle.getZ()));
            return !(d < r - 1.5);
        });
        return array;
    }

    public boolean removeIf(Predicate<BlockPos> filter) {
        boolean removed = false;
        BlockArray test = this.copy();
        for (BlockPos pos : test) {
            if (filter.test(pos)) {
                this.remove(pos);
                removed = true;
            }
        }
        return removed;
    }
    public boolean removeIf(IntPredicate filter) {
        boolean removed = false;
        BlockArray test = this.copy();
        for (BlockPos pos : test) {
            if (filter.test(this.getFlags(pos))) {
                this.remove(pos);
                removed = true;
            }
        }
        return removed;
    }

    public boolean retainIf(Predicate<BlockPos> filter) {
        boolean removed = false;
        BlockArray test = this.copy();
        for (BlockPos pos : test) {
            if (!filter.test(pos)) {
                this.remove(pos);
                removed = true;
            }
        }
        return removed;
    }
    public boolean retainIf(IntPredicate filter) {
        boolean removed = false;
        BlockArray test = this.copy();
        for (BlockPos pos : test) {
            if (!filter.test(this.getFlags(pos))) {
                this.remove(pos);
                removed = true;
            }
        }
        return removed;
    }

    public boolean check(Predicate<BlockPos> filter) {
        for (BlockPos pos : this) {
            if (!filter.test(pos)) {
                return false;
            }
        }
        return true;
    }

    public BlockArray offset(int x, int y, int z) {
        return this.offset(new Vec3i(x, y, z));
    }
    public BlockArray offset(Vec3i offset) {
        BlockArray newArray = new BlockArray();
        for (BlockPos pos : this) {
            newArray.add(pos.add(offset), this.getFlags(pos));
        }
        return newArray;
    }

    public BlockArray add(BlockArray blocks) {
        this.blocks.putAll(blocks.blocks);
        return this;
    }
    public BlockArray add(ArrayList<BlockPos> blocks) {
        for (BlockPos block : blocks) {
            this.add(block);
        }
        return this;
    }

    public BlockArray add(BlockPos blockPos, int flags) {
        if (!this.blocks.containsKey(blockPos.toImmutable())) {
            this.blocks.put(blockPos.toImmutable(), flags);
        }
        return this;
    }
    public BlockArray add(BlockPos blockPos) {
        this.add(blockPos.toImmutable(), 0);
        return this;
    }

    public BlockArray remove(BlockArray blocks) {
        for (BlockPos pos : blocks) {
            this.remove(pos);
        }
        return this;
    }
    public BlockArray remove(BlockPos blockPos) {
        if (this.contains(blockPos)) {
            this.blocks.removeInt(blockPos.toImmutable());
        }
        return this;
    }

    public BlockArray setFlags(int flags) {
        BlockArray copy = this.copy();
        this.clear();
        for (BlockPos pos : copy) {
            this.blocks.put(pos, flags);
        }
        return this;
    }

    public int getFlags(BlockPos pos) {
//        the possibilities are limitless! each flag could represent a different blockstate, or whatever you specify
        if (this.blocks.containsKey(pos.toImmutable())) {
            return this.blocks.get(pos.toImmutable());
        }
        return 0;
    }

    public void clear() {
        this.blocks.clear();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BlockArray && this.blocks.equals(((BlockArray) o).blocks);
    }

    @Override
    public int hashCode() {
        return this.blocks.hashCode();
    }

    public BlockPos getRandom(Random random) {
        return (BlockPos) Arrays.stream(this.blocks.keySet().toArray()).toList().get(random.nextInt(this.blocks.size()));
    }

    public BlockArray getLowest() {
        BlockArray lowest = new BlockArray();
        int lowestY = Integer.MAX_VALUE;
        for (BlockPos pos : this.blocks.keySet()) {
            if (pos.getY() < lowestY) {
                lowestY = pos.getY();
            }
        }
        for (BlockPos pos : this.blocks.keySet()) {
            if (pos.getY() == lowestY) {
                lowest.add(pos, this.blocks.get(pos));
            }
        }
        return lowest;
    }

    public BlockArray getHighest() {
        BlockArray highest = new BlockArray();
        int highestY = Integer.MIN_VALUE;
        for (BlockPos pos : this.blocks.keySet()) {
            if (pos.getY() > highestY) {
                highestY = pos.getY();
            }
        }
        for (BlockPos pos : this.blocks.keySet()) {
            if (pos.getY() == highestY) {
                highest.add(pos, this.blocks.get(pos));
            }
        }
        return highest;
    }

    public BlockPos center() {
//        it'd be cool to implement some feature that maybe gets the center of "mass" of the array at some point
        BlockPos min = this.min();
        BlockPos max = this.max();
        BlockPos delta = max.subtract(min);
        return new BlockPos((int) (delta.getX() / 2.0), (int) (delta.getY() / 2.0), (int) (delta.getZ() / 2.0)).add(min);
    }
    public BlockPos centerOfMass() {
//        whoa i actually did it
        double x = 0;
        double y = 0;
        double z = 0;
        int n = 0;
        for (BlockPos pos : this) {
            x = x * n + pos.getX() / (n + 1.0);
            y = y * n + pos.getY() / (n + 1.0);
            z = z * n + pos.getZ() / (n + 1.0);
            n++;
        }
        return new BlockPos(((int) Math.round(x)), ((int) Math.round(y)), ((int) Math.round(z)));
    }

    public BlockPos min() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        for (BlockPos pos : this.blocks.keySet()) {
            if (pos.getX() < minX) {
                minX = pos.getX();
            }
            if (pos.getY() < minY) {
                minY = pos.getY();
            }
            if (pos.getZ() < minZ) {
                minZ = pos.getZ();
            }
        }
        return new BlockPos(minX, minY, minZ);
    }
    public BlockPos max() {
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;
        for (BlockPos pos : this.blocks.keySet()) {
            if (pos.getX() > maxX) {
                maxX = pos.getX();
            }
            if (pos.getY() > maxY) {
                maxY = pos.getY();
            }
            if (pos.getZ() > maxZ) {
                maxZ = pos.getZ();
            }
        }
        return new BlockPos(maxX, maxY, maxZ);
    }

    public void build(FeatureContext<? extends FeatureConfig> context, BlockStateProvider provider) {
        this.build(context.getWorld(), provider);
    }

    public void build(WorldAccess world, BlockStateProvider provider) {
//        this is one i'm really excited about. basically you can do anything you want here, and with a robust
//        enough BlockStateProvider i am that much further along on my quest to reimplement half of the codebase of
//        minecraft in a less efficient way so i never have to learn how to make stuff like "carvers" (blegh)
        for (BlockPos block : this.blocks.keySet()) {
            if (provider instanceof AdvancedBlockStateProvider) {
                world.setBlockState(block, ((AdvancedBlockStateProvider) provider).get(world.getRandom(), block, this), 3);
            } else {
                world.setBlockState(block, provider.get(world.getRandom(), block), 3);
            }
        }
    }

    public BlockArray fill(int dist) {
//        todo
//        ooh, this is one i want to do for sure. idea being it takes a thin line-like BlockArray and kinda fattens
//        it up a bit. useful for ore veins, thicc trees, etc
//        float majorAxis = BlockPos.iterateOutwards()
        BlockPos min = this.min().subtract(new Vec3i(dist, dist, dist));
        BlockPos max = this.max().add(dist, dist, dist);
        BlockArray test = BlockArray.cuboid(min, max);
        for (BlockPos pos : test) {
//            if (pos.isWithinDistance())
//            how to do this in a non-resource intensive way?
        }
        System.err.println("unimplemented function ");
        return this;
    }


    public int size() {
        return this.blocks.size();
    }
    public boolean isEmpty() {
        return this.blocks.isEmpty();
    }
    public BlockArray copy() {
        BlockArray clone = new BlockArray();
        clone.add(this);
        return clone;
    }


    public boolean contains(Object o) {
        return o instanceof BlockPos && this.blocks.containsKey(o);
    }

    @NotNull
    @Override
    public Iterator<BlockPos> iterator() {
        return this.blocks.keySet().stream().iterator();
    }

    @Override
    public String toString() {
//            no class is complete without a fancy toString method
        StringBuilder str = new StringBuilder("BlockArray of ");
        for (BlockPos pos : this.blocks.keySet()) {
            str.append("[")
                    .append(pos.toShortString())
                    .append("], ")
                    .append(this.blocks.get(pos).toString())
                    .append("\n              ");
        }
        return str.toString();
    }
}
