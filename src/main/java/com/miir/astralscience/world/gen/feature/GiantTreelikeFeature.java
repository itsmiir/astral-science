package com.miir.astralscience.world.gen.feature;

import com.miir.astralscience.world.BlockArray;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class GiantTreelikeFeature extends Feature<GiantTreelikeFeatureConfig> {
    public GiantTreelikeFeature(Codec<GiantTreelikeFeatureConfig> configCodec) {
        super(configCodec);
    }

    public static BlockArray generateTree(FeatureContext<GiantTreelikeFeatureConfig> context, BlockPos origin, boolean makeTrunk, int initThickness, int depth, float tallness, float scale) {
        Random random = context.getRandom();
        GiantTreelikeFeatureConfig config = context.getConfig();
        int maxBranches = context.getConfig().maxBranches();
        int branchLength = config.branchLength();
        int branchVariation = config.branchVariation();
        float width = config.wideness();
        BlockArray array = new BlockArray(origin);
        maxBranches += 1;
        branchLength *= (scale/5f);
        int branchCount = random.nextBetween(2, maxBranches);
        float n = 20;
        boolean trunk = makeTrunk;
        Vec3d[] branches = new Vec3d[branchCount];
        for (int i = 0; i < branchCount; i++) {
            Vec3d vec;
//                ensure that all the branches are spread out by pregenerating them and checking to see if they're spread out
            boolean spreadOut;
            do {
                spreadOut = true;
                vec = new Vec3d(random.nextBetween(-5, 5)*scale/5f*width, random.nextBetween(10, 20)*tallness*scale/5f, random.nextBetween(-5, 5)*scale/5f*width);
//                    the y component is excluded because i only care about the xz part
                Vec3d vecN = new Vec3d(vec.x, 0, vec.z).normalize();
                for (Vec3d v : branches) {
                    if (v != null) {
//                            if there are 4 branches, the minimum space they can take up is π radians
                        if (new Vec3d(v.x, 0, v.z).normalize().dotProduct(vecN) > MathHelper.cos((float) (Math.PI / branchCount)))
                            spreadOut = false;
                    }
                }
            } while (!spreadOut);
            branches[i] = vec;
        }
        for (int size = 0; size < branchCount; size++) {
            BlockPos o = origin;
            Vec3d vec1 = branches[size];
            float thickness = trunk ? initThickness : initThickness - 1;
            for (int i = 0; i < (trunk ? 1 : branchVariation); i++) {
                float l = 0;
                Vector3f unit = vec1.normalize().toVector3f();
                do {
                    Vector3f radial = new Vector3f(unit).cross(new Vector3f(1, 0, 0)).normalize(); // we just need ANY vector that's perp. to unit
                    Vec3d vec3d = new Vec3d(new Vector3f(unit).mul(l));
                    BlockPos pos = BlockPos.ofFloored(vec3d.x, vec3d.y, vec3d.z).add(o);
                    float t = thickness; // todo: implement taper that doesnt look like shit
                    array.add(pos, (int) thickness);
                    Quaternionf quat = RotationAxis.of(unit).rotationDegrees(360 / n);
                    for (int j = 0; j < n; j++) {
                        radial = quat.transform(radial);
                        for (int k = 1; k <= t; k++) {
                            Vec3d vec3d1 = new Vec3d(new Vector3f(radial).mul(k).add(new Vector3f(unit).mul(l)));
                            BlockPos pos1 = BlockPos.ofFloored(vec3d1.x, vec3d1.y, vec3d1.z) .add(o);
                            array.add(pos1, (int) thickness);
                        }
                    }

                    l += .5;
                } while (l < vec1.length());
                o = o.add(new Vec3i((int) vec1.x, (int) vec1.y, (int) vec1.z));
                Vec3i diff = o.subtract(origin);
                double x = (diff.getX() > 0) ? random.nextBetween(0, branchLength)*width : random.nextBetween(-branchLength, 0)*width; // ensure that branches always go outward
                double z = (diff.getZ() > 0) ? random.nextBetween(0, branchLength)*width : random.nextBetween(-branchLength, 0)*width;
                vec1 = new Vec3d(x, random.nextBetween(-branchLength / 3, branchLength), z);
            }
            if (trunk) {
                origin = o;
                trunk = false;
            } else if (depth > 1) {
                array.add(generateTree(context, o,false,initThickness-1,depth-1, tallness/2f,scale/2f));
            }
        }
        return array;
    }

    @Override
    public boolean generate(FeatureContext<GiantTreelikeFeatureConfig> context) {
        BlockPos origin = AstralFeatures.findNextFloor(context).down(3);
        StructureWorldAccess world = context.getWorld();
        GiantTreelikeFeatureConfig config = context.getConfig();
        BlockArray array = generateTree(context, origin, true, config.initThickness(), config.depth(), config.tallness(), config.scale());
        array.build(world, new SimpleBlockStateProvider(Registries.BLOCK.get(config.blockState()).getDefaultState()));
        return true;
    }
}
