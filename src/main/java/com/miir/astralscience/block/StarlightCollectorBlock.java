package com.miir.astralscience.block;

import com.miir.astralscience.magic.rune.RuneParser;
import com.miir.astralscience.world.BlockArray;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StarlightCollectorBlock extends Block {
    public StarlightCollectorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false));
    }
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
    public static BooleanProperty LIT = Properties.LIT;
    public static int MAX_RUNE = 15;
    public static IntProperty RUNE = IntProperty.of("rune", 0, MAX_RUNE);
    private static final BlockArray MULTIBLOCK_SHAPE = new BlockArray()
            .add(new BlockPos(3, 0, 0))
            .add(new BlockPos(-3, 0, 0))
            .add(new BlockPos(0, 0, 3))
            .add(new BlockPos(0, 0, -3))
            .add(new BlockPos(2, 0, 2))
            .add(new BlockPos(2, 0, -2))
            .add(new BlockPos(-2, 0, 2))
            .add(new BlockPos(-2, 0, -2));

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(LIT);
        stateManager.add(RUNE);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockArray array = MULTIBLOCK_SHAPE.copy().offset(pos);
        for (BlockPos pos1 :
                array) {
            if (checkPattern(pos1, world, pos)) {
                BlockArray array1 = MULTIBLOCK_SHAPE.copy().offset(pos1);
                for (BlockPos pos2 :
                        array1) {
                    if (world.getBlockState(pos2).isOf(AstralBlocks.STARLIGHT_COLLECTOR)) {
                        world.setBlockState(pos2, world.getBlockState(pos2).with(LIT, true));
                        world.playSound(pos1.getX(), pos1.getY(), pos1.getZ(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                    }
                }
            }
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        for (BlockPos pos1 :
                MULTIBLOCK_SHAPE.copy().offset(pos)) {
            for (BlockPos pos2 :
                    MULTIBLOCK_SHAPE.copy().offset(pos1)) {
                if (world.getBlockState(pos2).isOf(AstralBlocks.STARLIGHT_COLLECTOR)) {
                    world.setBlockState(pos2, world.getBlockState(pos2).with(LIT, false));
                }
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(RUNE);
    }

    public static void cycle(World world, BlockPos pos, boolean forward) {
        int i = world.getBlockState(pos).get(RUNE);
        if (forward) {
            i = i == MAX_RUNE ? 0 : i + 1;
        } else {
            i = i == 0 ? MAX_RUNE : i - 1;
        }
        world.setBlockState(pos, world.getBlockState(pos).with(RUNE, i));
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
    }

    public static BlockArray getMultiblock() {
        return MULTIBLOCK_SHAPE.copy();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!state.get(LIT)) {
            BlockArray array = MULTIBLOCK_SHAPE.copy().offset(pos);
            for (BlockPos pos1 :
                    array) {
                if (checkPattern(pos1, world, pos)) {
                    BlockArray array1 = MULTIBLOCK_SHAPE.copy().offset(pos1);
                    for (BlockPos pos2 :
                            array1) {
                        if (world.getBlockState(pos2).isOf(AstralBlocks.STARLIGHT_COLLECTOR)) {
                            world.setBlockState(pos2, world.getBlockState(pos2).with(LIT, true));
                            world.playSound(pos1.getX(), pos1.getY(), pos1.getZ(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                        }
                    }
                }
            }
        }
        cycle(world, pos, !player.isSneaking());
        return ActionResult.SUCCESS;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT) && hasStarlight(world, pos)) {
            world.addParticle(ParticleTypes.END_ROD, pos.getX() + world.random.nextDouble(), pos.getY() + world.random.nextDouble() + 0.5d, pos.getZ() + world.random.nextDouble(), 0, 0, 0);
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    public static boolean hasStarlight(World world, BlockPos pos) {
//        apparently world.isNight() simply does not work :) perhaps i have broken something
//        (or maybe it doesn't work on the logical client?)
        return world.getTimeOfDay() < 23000 && world.getTimeOfDay() > 13000 && world.getLightLevel(LightType.SKY, pos.up()) == 15;
    }

    public static boolean checkPattern(BlockPos pos, World world, String n, String ne, String e, String se, String s, String sw, String w, String nw) {
        return checkPattern(pos, world, RuneParser.getRune(n), RuneParser.getRune(ne), RuneParser.getRune(e), RuneParser.getRune(se), RuneParser.getRune(s), RuneParser.getRune(sw), RuneParser.getRune(w), RuneParser.getRune(nw));
    }
    public static boolean checkPattern(BlockPos pos, World world, int n, int ne, int e, int se, int s, int sw, int w, int nw) {
        try {
            Direction forward = Direction.NORTH;
            for (int i = 0; i < 4; i++) {
                if (
                        world.getBlockState(pos.offset(forward, 3)).isOf(AstralBlocks.STARLIGHT_COLLECTOR) &&
                                world.getBlockState(pos.offset(forward, 3)).get(RUNE) == n &&
                                world.getBlockState(pos.offset(forward, 2).offset(forward.rotateYClockwise(), 2)).get(RUNE) == ne
                ) {
                    forward = forward.rotateYClockwise();
                    if (
                    world.getBlockState(pos.offset(forward, 3)).get(RUNE) == e &&
                    world.getBlockState(pos.offset(forward, 2).offset(forward.rotateYClockwise(), 2)).get(RUNE) == se
                    ) {
                        forward = forward.rotateYClockwise();
                        if (
                        world.getBlockState(pos.offset(forward, 3)).get(RUNE) == s &&
                        world.getBlockState(pos.offset(forward, 2).offset(forward.rotateYClockwise(), 2)).get(RUNE) == sw
                        ) {
                            forward = forward.rotateYClockwise();
                            if (
                                    world.getBlockState(pos.offset(forward, 3)).get(RUNE) == w &&
                                            world.getBlockState(pos.offset(forward, 2).offset(forward.rotateYClockwise(), 2)).get(RUNE) == nw
                            ) {
                                return true;
                            }
                            forward = forward.rotateYCounterclockwise();
                        }
                        forward = forward.rotateYCounterclockwise();
                    }
                    forward = forward.rotateYCounterclockwise();
                }
                forward = forward.rotateYClockwise();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public static boolean checkPattern(BlockPos pos, World world, BlockPos opos) {
        BlockArray array = MULTIBLOCK_SHAPE.offset(pos);
        return array.check((blockPos -> (world.getBlockState(blockPos).isOf(AstralBlocks.STARLIGHT_COLLECTOR) || blockPos.equals(opos))));
    }
}
