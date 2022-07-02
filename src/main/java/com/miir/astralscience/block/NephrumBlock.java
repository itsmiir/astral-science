package com.miir.astralscience.block;

import com.miir.astralscience.entity.effect.AstralStatusEffects;
import com.miir.astralscience.item.AstralItems;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class NephrumBlock extends PlantBlock implements Fertilizable {
    public static IntProperty AGE = IntProperty.of("age", 0, 4);
    public static final int MAX_AGE = 4;
    private static final VoxelShape SMALL_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    private static final VoxelShape LARGE_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    protected NephrumBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AGE, 0));
    }

    public static int getLuminance(BlockState state) {
        return state.get(AGE) * 3 + 3;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(AGE) == 0) {
            return SMALL_SHAPE;
        } else return LARGE_SHAPE;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient() && entity instanceof LivingEntity) {
            ((LivingEntity)entity).addStatusEffect(new StatusEffectInstance(AstralStatusEffects.GROUNDED, 100, 4));
        } else {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            net.minecraft.util.math.random.Random random = world.getRandom();
            for (int l = 0; l < 5; ++l) {
                int m = random.nextBoolean() ? -1 : 1;
                int n = random.nextBoolean() ? -1 : 1;
                mutable.set(i + MathHelper.nextInt(random, -1, 1), j + random.nextInt(1), k + MathHelper.nextInt(random, -1, 1));
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isFullCube(world, mutable)) {
                    world.addParticle(ParticleTypes.WARPED_SPORE, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() + random.nextDouble(), (double) mutable.getZ() + random.nextDouble(),  m * random.nextDouble() + 1, random.nextDouble() + 1, n * random.nextDouble() + 1);
                }
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = state.get(AGE);
        if (state.get(AGE) == MAX_AGE) {
            if (!world.isClient()) {
                world.setBlockState(pos, state.with(AGE, i - 1));
                dropStack(world, pos, new ItemStack(AstralItems.NEPHRYLL_POWDER, MathHelper.nextInt(world.getRandom(), 2, 5)));
                if (world.getRandom().nextFloat() < 0.25) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 15, 12));
                }
                return ActionResult.CONSUME;
            } else {
                world.playSound(player, pos, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 1.5f, 2.5f);
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < MAX_AGE;
    }


    public boolean canGrow(World world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
        int i = Math.min(MAX_AGE, state.get(AGE) + 1);
        world.setBlockState(pos, state.with(AGE, i), Block.NOTIFY_LISTENERS);
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(AstralItems.NEPHRYLL_POWDER);
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < MAX_AGE;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);
        if (i < MAX_AGE && random.nextInt(10) == 0) {
            world.setBlockState(pos, state.with(AGE, i + 1), Block.NOTIFY_LISTENERS);
        }

    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int l = 0; l < 14; ++l) {
            mutable.set(i + MathHelper.nextInt(random, -10, 10), j + random.nextInt(10), k + MathHelper.nextInt(random, -10, 10));
            if (MinecraftClient.getInstance().getCameraEntity().getPos().isInRange(new Vec3d(pos.getX(), pos.getY(), pos.getZ()), 40)) {
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isFullCube(world, mutable)) {
                    world.addParticle(ParticleTypes.SPORE_BLOSSOM_AIR, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

}
