package com.miir.astralscience.block;

import com.miir.astralscience.block.entity.StarshipHelmBlockEntity;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.TransparentBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StarshipConstructionBlock extends TransparentBlock {
    public StarshipConstructionBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
    private static ArrayList<BlockPos> findLine(BlockPos blockPos, Direction direction, World world) {
        BlockPos pos = blockPos;
        ArrayList<BlockPos> line = new ArrayList<>();
        if (world.getBlockState(pos.add(direction.getVector())).isOf(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK)) {
            while (world.getBlockState(pos).isOf(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK)) {
                line.add(pos);
                pos = pos.add(direction.getVector());
            }
        }
        return line;
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.isSneaking()) {
            if (isCorner(world, pos)) {
                if (buildStarship(state, world, pos, player)) {
                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.CONSUME;
                }
            }
            if (world instanceof ServerWorld) {
                player.sendMessage(Text.translatable("message.astralscience.incomplete_frame"), false);
            }
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }

    private boolean buildStarship(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (AstralDimensions.canHouseStarship(world)) {
            try {
                ArrayList<BlockPos> corners = findCorners3D(state, world, pos);
                BlockPos extent = findExtent(state, world, corners);
                BlockPos origin = findOrigin(state, world, corners);
                BlockPos helmPos = findHelmInConstruct(world, origin, extent, player);
                if (!(helmPos == null)) {
                    BlockEntity blockEntity = world.getBlockEntity(helmPos);
                    if (blockEntity instanceof StarshipHelmBlockEntity) {
                        ((StarshipHelmBlockEntity) blockEntity).link(origin, extent, player);

                    }
                    if (world instanceof ClientWorld) {
                        player.sendMessage(Text.translatable("message.astralscience.starship_construct_success"), false);
                    }
                    return true;
                } else {
                    return false;
                }
            } catch (NullPointerException e) {
                player.sendMessage(Text.translatable("message.astralscience.incomplete_frame"), false);
                return false;
            }
        }
        player.sendMessage(Text.translatable("message.astralscience.space_only"), false);
        return false;
    }

    private BlockPos findHelmInConstruct(World world, BlockPos origin, BlockPos extent, PlayerEntity player) {
        BlockPos testPos = origin.mutableCopy();
        while (testPos.getX() <= extent.getX()) {
            while (testPos.getY() <= extent.getY()) {
                while (testPos.getZ() <= extent.getZ()) {
                    if (world.getBlockState(testPos).isOf(AstralBlocks.STARSHIP_HELM)) {
                        return testPos;
                    }
                    testPos = testPos.add(0, 0, 1);
                }
                testPos = testPos.add(0, 1, 0);
                testPos = new BlockPos(testPos.getX(), testPos.getY(), origin.getZ());
            }
            testPos = testPos.add(1, 0, 0);
            testPos = new BlockPos(testPos.getX(), origin.getY(), testPos.getZ());
        }
        if (world instanceof ServerWorld) {
            player.sendMessage(Text.translatable("message.astralscience.no_helm"), false);
        }
        return null;
    }

    @NotNull
    private ArrayList<BlockPos> findCorners3D(BlockState state, World world, BlockPos pos) {
        ArrayList<BlockPos> corners = new ArrayList<>();
        BlockPos testPos = pos.mutableCopy();
        corners.add(pos);
        corners.addAll(findCorners1D(world, "x", testPos));
        corners.addAll(findCorners1D(world, "y", testPos));
        corners.addAll(findCorners1D(world, "z", testPos));
        return corners;
    }

    @NotNull
    private BlockPos findOrigin(BlockState state, World world, ArrayList<BlockPos> corners) {
        int currZ;
        int currY;
        int currX;
        int minX = 100000000;
        int minY = 100000000;
        int minZ = 100000000;
        for (BlockPos blockPos: corners) {
            currX = blockPos.getX();
            currY = blockPos.getY();
            currZ = blockPos.getZ();
            if (currX < minX) {
                minX = currX;
            }
            if (currY < minY) {
                minY = currY;
            }
            if (currZ < minZ) {
                minZ = currZ;
            }
        }
        return new BlockPos(minX, minY, minZ);
    }

    @NotNull
    private BlockPos findExtent(BlockState state, World world, ArrayList<BlockPos> corners) {
        int maxX = -100000000;
        int maxY = -100000000;
        int maxZ = -100000000;
        int currX;
        int currY;
        int currZ;
        for (BlockPos blockPos: corners) {
            currX = blockPos.getX();
            currY = blockPos.getY();
            currZ = blockPos.getZ();
            if (currX > maxX) {
                maxX = currX;
            }
            if (currY > maxY) {
                maxY = currY;
            }
            if (currZ > maxZ) {
                maxZ = currZ;
            }
        }
        return new BlockPos(maxX, maxY, maxZ);
    }

    /**
     * Finds corners of a Starship Construction Block structure. Needs to be run three times in each axis to get the
     * two most distant corners of a structure. The other corners can be interpolated from there.
     * @param world
     * The <code>World</code> that the structure is in.
     * @param axis
     * The axis to search in; either "x", "y", or "z".
     * @param pos
     * The starting <code>BlockPos</code>. It is assumed that the starting position is already added to the list of
     * corners.
     * @return
     * An <code>ArrayList</code> containing all the <b>new</b> corners (excluding the <code>pos</code> parameter).
     */
    private ArrayList<BlockPos> findCorners1D(World world, String axis, BlockPos pos) {
        ArrayList<BlockPos> corners = new ArrayList<>();
        Direction forward;
        Direction backward;
        ArrayList<BlockPos> line = new ArrayList<>();
        switch (axis) {
            case "y" -> {
                forward = Direction.UP;
                backward = Direction.DOWN;
            }
            case "z" -> {
                forward = Direction.SOUTH;
                backward = Direction.NORTH;
            }
            default -> {
                forward = Direction.EAST;
                backward = Direction.WEST;
            }
        }
        if (world.getBlockState(pos.offset(forward)).isOf(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK)) {
            line = findLine(pos, forward, world);
        } else if (world.getBlockState(pos.offset(backward)).isOf(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK)) {
            line = findLine(pos, backward, world);
        }
        boolean first = true;
        for (BlockPos blockPos: line) {
            if (isCorner(world, blockPos)) {
                if (first) {
                    first = false;
                } else {
                    corners.add(blockPos);
                }
            }
        }
        return corners;
    }

    /**
     * Finds if a part of a construction block structure is a corner (i.e., has exactly three connections).
     * @param world
     * The local <code>World</code>.
     * @param pos
     * The <code>BlockPos</code> to test.
     * @return
     * True if the given <code>BlockPos</code> is a corner.
     */
    private boolean isCorner(World world, BlockPos pos) {
        int attached = 0;
        if (world.getBlockState(pos.up()).isOf(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK)) {
            ++attached;
        }
        if (world.getBlockState(pos.north()).isOf(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK)) {
            ++attached;
        }
        if (world.getBlockState(pos.south()).isOf(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK)) {
            ++attached;
        }
        if (world.getBlockState(pos.west()).isOf(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK)) {
            ++attached;
        }
        if (world.getBlockState(pos.east()).isOf(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK)) {
            ++attached;
        }
        if (world.getBlockState(pos.down()).isOf(AstralBlocks.STARSHIP_CONSTRUCTION_BLOCK)) {
            ++attached;
        }
        return attached == 3;
    }
}
