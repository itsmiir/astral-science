package com.miir.astralscience.block;

import com.miir.astralscience.item.AstralItems;
import com.miir.astralscience.tag.AstralTags;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class SpearFernBlock extends PlantBlock implements Fertilizable {
    public static final IntProperty SECTION = IntProperty.of("section", 0, 2);
    private static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    protected SpearFernBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(SECTION, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SECTION);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(AstralItems.SPEAR_SEED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isIn(BlockTags.SNOW);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return (
                super.canPlaceAt(state, world, pos)
                        && world.getBlockState(pos.up(0)).isIn(AstralTags.AIR)
                        && world.getBlockState(pos.up(1)).isIn(AstralTags.AIR)
                        && world.getBlockState(pos.up(2)).isIn(AstralTags.AIR)
        );
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(1), AstralBlocks.SPEAR_FERN.getDefaultState().with(SECTION, 1), NOTIFY_LISTENERS);
        world.setBlockState(pos.up(2), AstralBlocks.SPEAR_FERN.getDefaultState().with(SECTION, 2), NOTIFY_LISTENERS);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        int section = state.get(SECTION);
        switch (section) {
            case 0:
                if (
                        canPlantOnTop(world.getBlockState(pos.down()), world, pos.down())) {
                    if (world.getBlockState(pos.up()).isOf(AstralBlocks.SPEAR_FERN)) {
                                if (
                                        world.getBlockState(pos.up()).get(SECTION) == 1
                                ) {
                                    return state;
                                }
                    }
                    return Blocks.AIR.getDefaultState();
                }
            case 1:
                if (
                        canPlantOnTop(world.getBlockState(pos.down(2)), world, pos.down(2))) {
                    if (
                            world.getBlockState(pos.down()).isOf(AstralBlocks.SPEAR_FERN)
                                    && world.getBlockState(pos.up()).isOf(AstralBlocks.SPEAR_FERN)) {
                        if (
                                world.getBlockState(pos.down()).get(SECTION) == 0
                                        && world.getBlockState(pos.up()).get(SECTION) == 2
                        ) {
                            return state;
                        }
                    }
                    return Blocks.AIR.getDefaultState();
                }
            case 2:
                if (
                        canPlantOnTop(world.getBlockState(pos.down(3)), world, pos.down(3))) {
                    if (
                            world.getBlockState(pos.down()).isOf(AstralBlocks.SPEAR_FERN)
                                    && world.getBlockState(pos.down(2)).isOf(AstralBlocks.SPEAR_FERN)) {
                        if (
                                world.getBlockState(pos.down()).get(SECTION) == 1
                                        && world.getBlockState(pos.down(2)).get(SECTION) == 0
                        ) {
                            return state;
                        }
                    }
                    return Blocks.AIR.getDefaultState();
                }
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos;
        switch (state.get(SECTION)) {
            case 0:
                blockPos = blockPos.up();
            case 1:
                blockPos = blockPos.up();
            case 2:
                break;
        }
        dropStack(world, blockPos, new ItemStack(AstralItems.SPEAR_SEED));
    }
}
