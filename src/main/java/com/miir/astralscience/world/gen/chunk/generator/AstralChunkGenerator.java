package com.miir.astralscience.world.gen.chunk.generator;

import com.google.common.collect.Sets;
import com.miir.astralscience.world.dimension.AstralDimensions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.noise.NoiseConfig;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


/**
 * this class was born out of my hatred for data-driven worldgen. i'm sorry apollo, i wasn't strong enough
 */
public class AstralChunkGenerator extends ChunkGenerator {
    private static final long SEED = AstralDimensions.SEED;
    public static final Codec<AstralChunkGenerator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                BiomeSource.CODEC.fieldOf("biome_source").forGetter(AstralChunkGenerator::getBiomeSource),
                Identifier.CODEC.fieldOf("function").forGetter(AstralChunkGenerator::getFunctionID),
                Identifier.CODEC.fieldOf("default_block").forGetter(AstralChunkGenerator::getDefaultBlockIdentifier),
                Codec.INT.fieldOf("sea_level").forGetter(AstralChunkGenerator::getSeaLevel),
                Codec.INT.fieldOf("world_height").forGetter(AstralChunkGenerator::getWorldHeight),
                Codec.INT.fieldOf("min_y").forGetter(AstralChunkGenerator::getMinimumY)
                ).apply(instance, AstralChunkGenerator::new));
    private static final BlockState AIR = Blocks.AIR.getDefaultState();

    private final Identifier functionID;
    private final AstralGeneratorFunction function;
    private final BlockState defaultBlock;
    private final int seaLevel;
    private final int worldHeight;
    private final int minimumY;

    public AstralChunkGenerator(BiomeSource biomeSource, Identifier function, Identifier defaultBlock, int seaLevel, int worldHeight, int minimumY) {
        super(biomeSource);
        this.functionID = function;
        this.seaLevel = seaLevel;
        this.worldHeight = worldHeight;
        this.minimumY = minimumY;
        this.defaultBlock = Registries.BLOCK.getOrThrow(RegistryKey.of(RegistryKeys.BLOCK, defaultBlock)).getDefaultState();
        this.function = AstralGeneratorFunctions.MAP.getOrDefault(function, ((context) -> new GeneratorSample(Blocks.AIR.getDefaultState(), this.minimumY)));
    }
    public Identifier getFunctionID() {return this.functionID;}
    @Override
    protected Codec<AstralChunkGenerator> getCodec() {return CODEC;}
    // eesh
    private Identifier getDefaultBlockIdentifier() {return this.defaultBlock.getBlock().getRegistryEntry().registryKey().getValue();}
    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver carverStep) {}
    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {

    }
    @Override
    public void populateEntities(ChunkRegion region) {}
    @Override
    public int getWorldHeight() {return this.worldHeight;}
    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        int minimumY = this.getMinimumY();
        int verticalBlockSize = 8;
        long seed = 0;
        int minCubeY = MathHelper.floorDiv(minimumY, verticalBlockSize);
        int cellHeight = MathHelper.floorDiv(this.getWorldHeight(), verticalBlockSize);
        if (cellHeight <= 0) {
            return CompletableFuture.completedFuture(chunk);
        }
        int l = chunk.getSectionIndex(cellHeight * verticalBlockSize - 1 + minimumY);
        int m = chunk.getSectionIndex(minimumY);
        HashSet<ChunkSection> set = Sets.newHashSet();
        for (int n = l; n >= m; --n) {
            ChunkSection chunkSection = chunk.getSection(n);
            chunkSection.lock();
            set.add(chunkSection);
        }
        return CompletableFuture.supplyAsync(Util.debugSupplier("wgen_fill_noise", () -> this.buildChunk(blender, structureAccessor, noiseConfig, chunk, minCubeY, cellHeight)), Util.getMainWorkerExecutor()).whenCompleteAsync((chunk2, throwable) -> {
            for (ChunkSection chunkSection : set) {
                chunkSection.unlock();
            }
        }, executor);
    }

    public final Chunk buildChunk(Blender blender, StructureAccessor structureAccessor, NoiseConfig noiseConfig, Chunk chunk, int minCubeY, int cellHeight) {
//        get the chunk heightmaps for the land and sea
        int xOffset = chunk.getPos().x*16;
        int zOffset = chunk.getPos().z*16;
        Heightmap oceanFloorHeightmap = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap worldSurfaceHeightmap = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        for (int x = 0; x < 16; x++) {
            mutablePos.setX(x+xOffset);
            for (int z = 0; z < 16; z++) {
                mutablePos.setZ(z+zOffset);
                mutablePos.setY(minimumY);
                float elevation = function.sample(new GeneratorContext(mutablePos.toImmutable(), SEED, this.seaLevel, this.minimumY, this.worldHeight)).maxY();
                if (elevation < minimumY) elevation = minimumY;
                for (int y = minimumY; y < elevation+1; y++) { // +1 to allow for things like snow layer interpolation
                    mutablePos.setY(y);
                    BlockState blockState = function.sample(new GeneratorContext(mutablePos.toImmutable(), SEED, this.seaLevel, this.minimumY, this.worldHeight)).state();
                    if (blockState == null) blockState = defaultBlock;
//                    if the blockstate is air or the world is debug and the current chunk is outside the generation area,
//                    no need for lighting, water, or heightmap updates, or even to place the block at all
                    if (blockState == AIR || SharedConstants.isOutsideGenerationArea(chunk.getPos())) continue;
//                    add the light source to the chunk if it's a ProtoChunk
//                    if (blockState.getLuminance() != 0 && chunk instanceof ProtoChunk) ((ProtoChunk) chunk).(mutablePos);
//                    set the blockstate
                    int chunkSectionIndex = chunk.getSectionIndex(y);
                    ChunkSection chunkSection = chunk.getSection(chunkSectionIndex);
                    chunkSection.setBlockState(x & 0xf,y & 0xf, z & 0xf, blockState, false);
//                    update the heightmaps
                    oceanFloorHeightmap.trackUpdate(x, y, z, blockState);
                    worldSurfaceHeightmap.trackUpdate(x, y, z, blockState);
//                    if there's no fluid at this blockpos, no post-processing needed
                    if (blockState.getFluidState().isEmpty()) continue;
                    chunk.markBlockForPostProcessing(mutablePos);
                }
            }
        }
        return chunk;
    }

    @Override
    public int getSeaLevel() {return this.seaLevel;}
    @Override
    public int getMinimumY() {return this.minimumY;}
    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        return ((int) this.function.sample(new GeneratorContext(new BlockPos(x, 0, z), SEED, seaLevel, minimumY, worldHeight)).maxY());
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        return null;
    }

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {
        text.add(String.valueOf(function.sample(new GeneratorContext(pos, 0, seaLevel, minimumY, worldHeight)).maxY()));
    }
}
