package com.miir.astralscience.structure;

public class CascadiumSkeletonGenerator {
//    private static final Identifier[] SKELETONS = new Identifier[]{
//            AstralScience.id("cascadium_skeleton/curved_ribcage"),
//            AstralScience.id("cascadium_skeleton/jaw"),
//            AstralScience.id("cascadium_skeleton/large_phalange"),
//            AstralScience.id("cascadium_skeleton/medium_phalange"),
//            AstralScience.id("cascadium_skeleton/small_phalange"),
//            AstralScience.id("cascadium_skeleton/large_shard"),
//            AstralScience.id("cascadium_skeleton/medium_shard"),
//            AstralScience.id("cascadium_skeleton/rib1")
//    };
//
//
//
//    public static void addPieces(StructureTemplateManager manager, List<StructurePiece> pieces, ChunkRandom random, BlockPos pos) {
//        pieces.add(new CascadiumSkeletonGenerator.Piece(manager, (Identifier) Util.getRandom((Object[])SKELETONS, random), pos));
//    }
//    public static class Piece extends SimpleStructurePiece {
//
//
//        public Piece(StructureTemplateManager manager, Identifier template, BlockPos pos) {
//            super(AstralFeatures.CASCADIUM_SKELETON_PIECE, 0, manager, template, template.toString(), createPlacementData(), pos);
//            this.pos = pos;
//        }
//
//        public Piece(ServerWorld world, NbtCompound nbt) {
//            super(AstralFeatures.CASCADIUM_SKELETON_PIECE, nbt, world.getStructureTemplateManager(), (identifier) -> createPlacementData());
//        }
//
//        private static StructurePlacementData createPlacementData() {
//            return (new StructurePlacementData()).setMirror(BlockMirror.NONE).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
//        }
//
//
//        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random, BlockBox boundingBox) {
//        }
//
//        public void generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, net.minecraft.util.math.random.Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
////            boundingBox.encompass(this.template.calculateBoundingBox(this.placementData, this.pos));
//            super.generate(structureWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, blockPos);
//        }
//
//        @Override
//        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess world, net.minecraft.util.math.random.Random random, BlockBox boundingBox) {
//
//        }
//    }
}
