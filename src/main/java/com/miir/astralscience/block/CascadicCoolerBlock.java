package com.miir.astralscience.block;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.block.entity.CascadicCoolerBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CascadicCoolerBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty LIT = Properties.LIT;

    public CascadicCoolerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(LIT, false));
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            this.openScreen(world, pos, player);
            return ActionResult.CONSUME;
        }
    }

    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CascadicCoolerBlockEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory)blockEntity);
        }
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(FACING, context.getPlayerFacing());
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasCustomName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CascadicCoolerBlockEntity) {
                ((CascadicCoolerBlockEntity)blockEntity).setCustomName(itemStack.getName());
            }
        }

    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CascadicCoolerBlockEntity) {
                if (world instanceof ServerWorld) {
                    ItemScatterer.spawn(world, (BlockPos)pos, (Inventory)((CascadicCoolerBlockEntity)blockEntity));
                    ((CascadicCoolerBlockEntity)blockEntity).method_27354((ServerWorld)world, Vec3d.ofCenter(pos));
                }

                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CascadicCoolerBlockEntity( blockPos, blockState);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(LIT);
        stateManager.add(Properties.HORIZONTAL_FACING);
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if ((Boolean)state.get(LIT)) {
            double d = (double)pos.getX() + 0.5D;
            double e = (double)pos.getY();
            double f = (double)pos.getZ() + 0.5D;
            if (random.nextDouble() < 0.1D) {
                world.playSound(d, e, f, SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction2 = (Direction)state.get(FACING);
            Direction direction = direction2.getOpposite();
            Direction.Axis axis = direction.getAxis();
            double g = random.nextDouble();
            double h = random.nextDouble() * 0.6D - 0.3D;
            double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52D : h;
            double j = random.nextDouble() * 6.0D / 16.0D;
            double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52D : h;
            double i2 = axis == Direction.Axis.X ? (double)direction2.getOffsetX() * 0.52D : h;
            double j2 = random.nextDouble() * 6.0D / 16.0D;
            double k2 = axis == Direction.Axis.Z ? (double)direction2.getOffsetZ() * 0.52D : h;
            world.addParticle(ParticleTypes.DRIPPING_DRIPSTONE_WATER, d + i2, e + j2, f + k2, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.SNOWFLAKE, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
        }
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> checkType(World world, BlockEntityType<T> givenType, BlockEntityType<? extends CascadicCoolerBlockEntity> expectedType) {
        return world.isClient ? null : checkType(givenType, expectedType, CascadicCoolerBlockEntity::tick);
    }

}
