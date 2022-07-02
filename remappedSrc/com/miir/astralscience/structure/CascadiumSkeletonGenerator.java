package com.miir.astralscience.structure;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.world.gen.feature.AstralFeatures;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.List;
import java.util.Random;

public class CascadiumSkeletonGenerator {
    private static final Identifier[] SKELETONS = new Identifier[]{
            AstralScience.id("cascadium_skeleton/curved_ribcage"),
            AstralScience.id("cascadium_skeleton/jaw"),
            AstralScience.id("cascadium_skeleton/large_phalange"),
            AstralScience.id("cascadium_skeleton/medium_phalange"),
            AstralScience.id("cascadium_skeleton/small_phalange"),
            AstralScience.id("cascadium_skeleton/large_shard"),
            AstralScience.id("cascadium_skeleton/medium_shard"),
            AstralScience.id("cascadium_skeleton/rib1")
    };



    public static void addPieces(StructureManager manager, List<StructurePiece> pieces, ChunkRandom random, BlockPos pos) {
        pieces.add(new CascadiumSkeletonGenerator.Piece(manager, (Identifier) Util.getRandom((Object[])SKELETONS, random), pos));
    }
    public static class Piece extends SimpleStructurePiece {


        public Piece(StructureManager manager, Identifier template, BlockPos pos) {
            super(AstralFeatures.CASCADIUM_SKELETON_PIECE, 0, manager, template, template.toString(), createPlacementData(), pos);
            this.pos = pos;
        }

        public Piece(ServerWorld world, NbtCompound nbt) {
            super(AstralFeatures.CASCADIUM_SKELETON_PIECE, nbt, world, (identifier) -> createPlacementData());
        }

        private static StructurePlacementData createPlacementData() {
            return (new StructurePlacementData()).setMirror(BlockMirror.NONE).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
        }


        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random, BlockBox boundingBox) {
        }

        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            boundingBox.encompass(this.structure.calculateBoundingBox(this.placementData, this.pos));
            return super.generate(structureWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, blockPos);
        }
    }
}
