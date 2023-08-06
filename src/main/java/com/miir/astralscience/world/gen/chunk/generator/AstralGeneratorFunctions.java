package com.miir.astralscience.world.gen.chunk.generator;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.world.dimension.AstralDimensions;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.math.random.ThreadSafeRandom;

// i hate helper classes but i'm working in the minecraft codebase
public abstract class AstralGeneratorFunctions {
    public static double normalize(double d) {return (d+1)/2;}
    public static double midpoint(double d) {return 1 - 2 * Math.abs(d);}
    private static double square(double d) {return d*d;}
    private static double octaves(SimplexNoiseSampler noise, double x, double y, int octaves) {
        double d = 0;
        for (int i = 1; i <= octaves; i++) {
            d += noise.sample(x*i, y*i)/Math.pow(2, i-1);
        }
        return MathHelper.clamp(d, -1, 1);
    }

    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private static final BlockState BEDROCK = Blocks.BEDROCK.getDefaultState();

    private static final BlockState PSIONIC_SAND = AstralBlocks.PSIONIC_SAND.getDefaultState();
    private static final AstralGeneratorFunction PSIDON = (context -> {
        final float scale = 20;
        long seed = 0;
        BlockPos pos = context.pos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int worldHeight = context.height();
        int minY = context.minY();
//        dune noise
        ThreadSafeRandom r = new ThreadSafeRandom(AstralDimensions.SEED);
        SimplexNoiseSampler noise = new SimplexNoiseSampler(r);
        float xl = 64*scale;
        float largeNoise = (float) octaves(noise, (z)/xl+ 0, (x)/xl+ 0, 5);
        float l = 32*scale;
        float s = 12*scale;
        double h = noise.sample(x/l, z/l);
        h += octaves(noise, (x)/l+ 0, (z)/l+ 0, 4);
        h /= 2f;
        if (largeNoise < 0) {
            if (h < 0) h *= h;
            h = (h * (1 - h)) + (normalize(midpoint(octaves(noise, (x) / s + 0, (z) / s + 0, 4))) * (h));
            h *= largeNoise * 5f;
            h *= h*h/Math.abs(h);
        } else {
            if (h < 0) h /= 4;
            h = h*(0.5) + (normalize(midpoint(noise.sample((x)/s, (z)/s))*(0.5)));
            h*= largeNoise*2;
        }
        h = MathHelper.clamp(h, -1, 3);

//        this transformation basically replaces all values below canyonLevel with the top right part of a cubic function
        final float canyonLevel = -.5f;
        final float slopeModifier = 20f; // steepness of the walls
        final float mul = 3f;
        if (h <= canyonLevel) {
//            h = Math.sqrt((float) h);
            h = mul*slopeModifier*Math.pow(h-canyonLevel+Math.cbrt(1/slopeModifier), 3) - mul + canyonLevel;
        }
//        transform to a heightmap
        h *= 30;// [-22.5,  90]
        h += 90; // [67.5, 180]
        h = MathHelper.clamp(h, minY+6, minY+worldHeight);
        if (y <= 100) h += square(normalize(midpoint(noise.sample((x+323423)/s, (z+323423)/s))))*2*(((float)(164-y-minY)/164));
        float elevation = (float) h;
        int depth = (int) (elevation - y);
        if (elevation <= minY) return new GeneratorSample(BEDROCK, elevation);
        if (depth < 0) return new GeneratorSample(AIR, elevation);
        if (depth <= 5) return new GeneratorSample(PSIONIC_SAND, elevation);
//        else return new GeneratorSample(null, elevation);
        else return bedrockPattern(y, elevation, minY, r);
    });


    private static GeneratorSample bedrockPattern(int y, float elevation, int minY, ThreadSafeRandom r) {
        if ((y <= minY + r.nextBetween(1, 5))) return new GeneratorSample(BEDROCK, elevation);
        return new GeneratorSample(null, elevation);
    }

    public static final Object2ObjectArrayMap<Identifier, AstralGeneratorFunction> MAP = new Object2ObjectArrayMap<>(
            new Identifier[]{
                    AstralScience.id("psidon")
            }, new AstralGeneratorFunction[]{
                    PSIDON
    });

}
