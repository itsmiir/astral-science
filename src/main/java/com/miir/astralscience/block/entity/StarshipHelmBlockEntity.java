package com.miir.astralscience.block.entity;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.entity.InteractableHologramEntity;
import com.miir.astralscience.screen.StarshipHelmScreenHandler;
import com.miir.astralscience.tag.AstralTags;
import com.miir.astralscience.util.AstralText;
import com.miir.astralscience.world.dimension.AstralDimensions;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Clearable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StarshipHelmBlockEntity extends LockableContainerBlockEntity implements ExtendedScreenHandlerFactory, Inventory {
    public static final float DRAW_DISTANCE = 100;
    private static final Text TITLE = Text.translatable("container.astralscience.starship_helm");
    public static final int SIZE = 4;
    public static final int RANGE = 5;
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(SIZE, ItemStack.EMPTY);
    private BlockPos relativeOrigin;
    private BlockPos relativeExtent;
    private BlockPos absoluteCenter;
    public Vec3i radius;
    private UUID owner;
    private BlockPos helmRelativePos;
    private boolean canWarp;
    private boolean linked;
    public int fuel;
    public int energy;
    private RegistryKey<World> destination;
    private Object2ObjectArrayMap<String, UUID> map;

    public final RegistryKey<World> getDestination() {
        if (this.destination != null) {
            return this.destination;
        }
        if (this.world != null) {
            return this.world.getRegistryKey();
        }
        return World.OVERWORLD;
    }

    public void setDestination(RegistryKey<World> destination) {
        this.destination = destination;
        this.markDirty();
    }

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 1 -> fuel;
                case 0 -> energy;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 1:
                    fuel = value;
                case 0:
                    energy = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };


    public StarshipHelmBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(AstralBlocks.STARSHIP_HELM_TYPE, blockPos, blockState);
        this.canWarp = false;
        this.absoluteCenter = BlockPos.ORIGIN;
        this.relativeOrigin = BlockPos.ORIGIN;
        this.relativeExtent = BlockPos.ORIGIN;
        this.radius = Vec3i.ZERO;
        this.helmRelativePos = BlockPos.ORIGIN;
        this.fuel = 0;
        this.map = new Object2ObjectArrayMap<>();
    }


    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> new CraftingScreenHandler(i, playerInventory, ScreenHandlerContext.create(world, pos)), TITLE);
    }

    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack stack = this.getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = Inventories.splitStack(this.inventory, slot, amount);
        if (stack.isEmpty()) {
            this.markDirty();
        }
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        this.markDirty();
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        this.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.inventory.clear();
        this.markDirty();
    }

    public void link(BlockPos origin, BlockPos extent, PlayerEntity player) {
        this.relativeOrigin = origin.subtract(this.pos);
        this.relativeExtent = extent.subtract(this.pos);
        this.absoluteCenter = findCenter(origin, extent);
        this.radius = findRadius(this.absoluteCenter, extent);
        this.helmRelativePos = this.pos.subtract(origin);
        this.canWarp = true;
        this.linked = true;
        if (player instanceof ServerPlayerEntity) {
            player.sendMessage(Text.translatable(
                            "message.astralscience.link_success",
                            this.getPos().getX(),
                            this.getPos().getY(),
                            this.getPos().getZ()
                    ),
                    false);
        }
        this.markDirty();
    }

    private Vec3i findRadius(BlockPos center, BlockPos extent) {
        return new Vec3i(
//                + 1 to ensure that the radius is always >= the actual size of the ship
                extent.getX() - center.getX() + 1,
                extent.getY() - center.getY() + 1,
                extent.getZ() - center.getZ() + 1
        );
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        this.relativeOrigin = BlockPos.fromLong(tag.getLong("origin"));
        this.relativeExtent = BlockPos.fromLong(tag.getLong("extent"));
        this.absoluteCenter = BlockPos.fromLong(tag.getLong("center"));
        this.radius = BlockPos.fromLong(tag.getLong("radius"));
        this.helmRelativePos = BlockPos.fromLong(tag.getLong("helmRelativePos"));
        this.canWarp = tag.getBoolean("canWarp");
        this.linked = tag.getBoolean("linked");
        this.fuel = tag.getInt("fuel");
        this.destination = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id(tag.getString("destination")));
        Inventories.writeNbt(tag, inventory);
        super.writeNbt(tag);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        Inventories.readNbt(tag, inventory);
        tag.putLong("origin", this.relativeOrigin.asLong());
        tag.putLong("extent", this.relativeExtent.asLong());
        tag.putLong("center", this.absoluteCenter.asLong());
        tag.putLong("radius", new BlockPos(this.radius).asLong());
        tag.putLong("helmRelativePos", this.helmRelativePos.asLong());
        tag.putBoolean("canWarp", this.canWarp);
        tag.putBoolean("linked", this.linked);
        tag.putInt("fuel", this.fuel);
        tag.putString("destination", this.getDestination().getValue().getPath());
    }

    public boolean isLinked() {
        return this.linked;
    }

    private BlockPos findCenter(BlockPos origin, BlockPos extent) {
        Vec3i difference = extent.subtract(origin);
        BlockPos differenceHalved = new BlockPos(difference.getX() / 2, difference.getY() / 2, difference.getZ() / 2);
        return new BlockPos(differenceHalved.add(origin));
    }

    public BlockPos getAbsoluteValue(BlockPos relativePos) {
        return new BlockPos(relativePos.add(this.pos.toImmutable()));
    }

    public void warp(ServerWorld world, ServerPlayerEntity player) {
        this.warp(world.getRegistryKey(), player);
    }

    public void warp(RegistryKey<World> destinationOrbital, ServerPlayerEntity player) {
        RegistryKey<World> destination = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id(AstralText.orbitify(destinationOrbital.getValue().getPath())));
        if (AstralDimensions.canHouseStarship(destination) && this.world instanceof ServerWorld) {
            ServerWorld destinationWorld = this.getWorld().getServer().getWorld(destination);
            if (destinationWorld.getRegistryKey().equals(this.world.getRegistryKey())) {
                player.sendMessage(Text.translatable("message.astralscience.already_here"), false);
                return;
            }
            player.sendMessage(Text.translatable("message.astralscience.start_warp", "§6" + AstralDimensions.fromId(destination.getValue().getPath())), false);
            BlockPos destinationPos = this.findNewOrigin(destination);
            if (destinationPos.equals(AstralScience.INVALID)) {
                player.sendMessage(Text.translatable("message.astralscience.no_space"), false);
                return;
            } else {
                if (this.canWarp) {
                    this.moveAllBlocks(destinationWorld, destinationPos);
                    this.moveAllEntities(destinationWorld, destinationPos);
                    player.sendMessage(Text.translatable("message.astralscience.warp_success"), false);
                    destinationWorld.getChunkManager().save(true);
                    return;
                }
                player.sendMessage(Text.translatable("message.astralscience.unable_to_warp"), false);
            }

        }
        player.sendMessage(Text.translatable("message.astralscience.invalid_dimension"), false);
    }

    private BlockPos findNewOrigin(RegistryKey<World> destination) {
        ServerWorld destinationWorld = this.world.getServer().getWorld(destination);
        destinationWorld.getPointOfInterestStorage().preloadChunks(destinationWorld, this.pos, getMaxRadius(this.radius) * 2);
        if (spaceIsEmpty(destinationWorld, this.getAbsoluteValue(this.relativeOrigin), this.getAbsoluteValue(this.relativeExtent))) {
            return this.getAbsoluteValue(this.relativeOrigin);
        }
        ArrayList<BlockPos> blocks = getBlocks(this.getAbsoluteValue(this.getRelativeOrigin()), this.getAbsoluteValue(this.getRelativeExtent()));
        ArrayList<BlockPos> warpableBlocks = getWarpableBlocks(this.world, this.getRelativeOrigin(), this.getRelativeExtent());
        for (BlockPos testPos : blocks) {
            if (spaceIsEmpty(destinationWorld, warpableBlocks, testPos)) {
                return testPos.toImmutable();
            }
        }
        return AstralScience.INVALID;
    }

    public static boolean spaceIsEmpty(ServerWorld world, BlockPos origin, BlockPos extent) {
        ArrayList<BlockPos> blocks = getBlocks(origin, extent);
        return spaceIsEmpty(world, blocks);
    }

    public static boolean spaceIsEmpty(World world, ArrayList<BlockPos> blocks) {
        return spaceIsEmpty(world, blocks, BlockPos.ORIGIN);
    }

    public static boolean spaceIsEmpty(World world, ArrayList<BlockPos> blocks, BlockPos shift) {
        for (BlockPos block :
                blocks) {
            if (!world.getBlockState(block.add(shift)).isIn(AstralTags.STARSHIP_REPLACEABLE)) {
                return false;
            }
        }
        return true;
    }

    private int getMaxRadius(Vec3i radius) {
        return Math.max(Math.max(radius.getX(), radius.getZ()), radius.getY());
    }

    public BlockPos getRelativeOrigin() {
        return this.relativeOrigin.toImmutable();
    }

    public BlockPos getRelativeExtent() {
        return this.relativeExtent.toImmutable();
    }

    public UUID getOwner() {
        return this.owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        this.markDirty();
    }

    private void moveAllBlocks(ServerWorld destinationWorld, BlockPos newOrigin) {
        BlockPos origin = this.getAbsoluteValue(this.relativeOrigin);
        BlockPos extent = this.getAbsoluteValue(this.relativeExtent);
        ArrayList<BlockPos> blocks = new ArrayList<>(getBlocks(origin, extent));
        for (BlockPos blockPos : blocks) {
            if (!this.world.getBlockState(blockPos).isIn(AstralTags.UNSTARSHIPPABLE)) {
                BlockState state = this.world.getBlockState(blockPos);
                BlockPos relativePosition = new BlockPos(blockPos.subtract(origin));
                BlockPos newPos = relativePosition.add(newOrigin);
                destinationWorld.setBlockState(newPos, state);
                if ((this.world.getBlockEntity(blockPos) != null)) {
                    copyBlockEntity(this.world, destinationWorld, blockPos, newPos);
                    BlockEntity blockEntity = this.world.getBlockEntity(blockPos);
//                there is a flag on World#setBlockState to mark an inventory to not drop its items, but i can't figure
//                out how it works :tiny_potato:
                    Clearable.clear(blockEntity);
                    blockEntity.markRemoved();
                }
                this.world.setBlockState(blockPos, AstralScience.STARSHIP_VOID, 63, -1);
            }
        }
    }

    private void moveAllEntities(ServerWorld destinationWorld, BlockPos newOrigin) {
        BlockPos origin = this.getAbsoluteValue(this.relativeOrigin);
        BlockPos extent = this.getAbsoluteValue(this.relativeExtent);
//        shamelessly copied from Structure#addEntitiesFromWorld() :)
        assert this.world != null;
        List<Entity> entities = this.world.getEntitiesByClass(Entity.class, new Box(origin, extent), Entity::canUsePortals);
        for (Entity entity : entities) {
            Vec3d position = entity.getPos();
            Vec3d relativePosition = position.subtract(Vec3d.ofCenter(origin));
            Vec3d newPosition = relativePosition.add(Vec3d.ofCenter(newOrigin));
            FabricDimensions.teleport(entity, destinationWorld, new TeleportTarget(newPosition, entity.getVelocity(), entity.getYaw(), entity.getPitch()));
        }
    }

    public static void copyBlockEntity(World world, World destinationWorld, BlockPos pos1, BlockPos pos2) {
        if (world.getBlockEntity(pos1) != null) {
            BlockEntity sourceBE = world.getBlockEntity(pos1);
            if (sourceBE != null) {
                NbtCompound tag = sourceBE.createNbtWithId();
//                TODO: fix remaining piston jank
//                  as of now, pistons will break if they are retracting during a warp; they should pop off as items.
//                  The block they're pushing also will turn into a phantom MovingPiston block. You can remove it by
//                  standing in the block and right clicking the air with a screwdriver. Will be fixed Soon(TM).
                if (sourceBE instanceof PistonBlockEntity) {
                    Direction direction = ((PistonBlockEntity) sourceBE).getMovementDirection();
                    BlockState state = ((PistonBlockEntity) sourceBE).getPushedBlock();
                    if (((PistonBlockEntity) sourceBE).isExtending()) {
                        pos2 = pos2.offset(direction);
                    }
                    destinationWorld.setBlockState(pos2, state, 63, -1);
                } else if (destinationWorld.getBlockState(pos2).hasBlockEntity()) {
                    BlockEntity blockEntity = destinationWorld.getBlockEntity(pos2);
                    if (blockEntity != null) {
                        blockEntity.readNbt(tag);
                        blockEntity.markDirty();
                    }
                }
            }
        }
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos currentOrigin, BlockPos currentExtent) {
        ArrayList<BlockPos> blocks = new ArrayList<>();
        BlockPos testPos = currentOrigin.mutableCopy();
        while (testPos.getX() <= currentExtent.getX()) {
            while (testPos.getY() <= currentExtent.getY()) {
                while (testPos.getZ() <= currentExtent.getZ()) {
                    blocks.add(testPos);
                    testPos = testPos.add(0, 0, 1);
                }
                testPos = testPos.add(0, 1, 0);
                testPos = new BlockPos(testPos.getX(), testPos.getY(), currentOrigin.getZ());
            }
            testPos = testPos.add(1, 0, 0);
            testPos = new BlockPos(testPos.getX(), currentOrigin.getY(), testPos.getZ());
        }
        return blocks;
    }

    public static ArrayList<BlockPos> getWarpableBlocks(World world, BlockPos currentOrigin, BlockPos currentExtent) {
        ArrayList<BlockPos> blocks = new ArrayList<>();
        BlockPos testPos = currentOrigin.mutableCopy();
        while (testPos.getX() <= currentExtent.getX()) {
            while (testPos.getY() <= currentExtent.getY()) {
                while (testPos.getZ() <= currentExtent.getZ()) {
                    if (!world.getBlockState(testPos).isIn(AstralTags.UNSTARSHIPPABLE)) {
                        blocks.add(testPos);
                    }
                    testPos = testPos.add(0, 0, 1);
                }
                testPos = testPos.add(0, 1, 0);
                testPos = new BlockPos(testPos.getX(), testPos.getY(), currentOrigin.getZ());
            }
            testPos = testPos.add(1, 0, 0);
            testPos = new BlockPos(testPos.getX(), currentOrigin.getY(), testPos.getZ());
        }
        return blocks;
    }

    @Override
    public Text getDisplayName() {
        return TITLE;
    }

    @Override
    protected Text getContainerName() {
        return Text.of("Test");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new StarshipHelmScreenHandler(syncId, inv, this);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new StarshipHelmScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.pos);
    }

    public static void tick(World world, BlockPos pos, BlockState state, StarshipHelmBlockEntity blockEntity) {
        if (!world.isClient()) {
            if (world.isPlayerInRange(pos.getX(), pos.getY(), pos.getZ(), RANGE)) {
                RegistryKey<World> key = world.getRegistryKey();
                Vec3d origin = Vec3d.ofCenter(blockEntity.getPos());
//                Vec3d origin = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), RANGE, false).getPos();
                double x = origin.x;
                double y = origin.y;
                double z = origin.z;
                if (world.isPlayerInRange(x, y, z, RANGE)) {
                    for (RegistryKey<World> destination : AstralDimensions.VISITABLE_DIMENSIONS) {
                        if (AstralDimensions.isVisibleFromHere(destination, key)) {
                            Vec2f angles = AstralDimensions.getRelativeDirection(destination, world);
                            float skyAngle = world.getSkyAngle(0) * 360.0f;
                            double angleX = Math.toRadians(skyAngle + angles.x) + Math.PI / 2;
                            double angleY = Math.toRadians(angles.y);
                            Vec3d renderPos = new Vec3d(
                                    DRAW_DISTANCE / 10 * Math.cos(angleY) * Math.cos(angleX),
                                    DRAW_DISTANCE / 10 * Math.sin(angleX),
                                    DRAW_DISTANCE / 10 * Math.sin(angleY) * -Math.cos(angleX));
                            renderPos = renderPos.add(origin);
                            String path = destination.getValue().getPath();
                            if (blockEntity.map.containsKey(path)) {
                                Entity e = ((ServerWorld) world).getEntity(blockEntity.map.get(path));
                                if (e == null) {
                                    e = new BoatEntity(EntityType.BOAT, world);
                                    e.setNoGravity(true);
                                    e.setPos(renderPos.x, renderPos.y, renderPos.z);
                                    world.spawnEntity(e);
                                } else {
                                    e.setPos(renderPos.x, renderPos.y, renderPos.z);
                                }
//                                e.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 100));
                            } else {
//                                ItemEntity e = new ItemEntity(EntityType.ITEM, world);
//                                e.setStack(Items.GLOWSTONE.getDefaultStack());
//                                InteractableHologramEntity e = new InteractableHologramEntity(world);
                                BoatEntity e = new BoatEntity(EntityType.BOAT, world);
                                e.setNoGravity(true);
//                                e.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 100));
                                blockEntity.map.put(path, e.getUuid());
                                e.setPos(renderPos.x, renderPos.y, renderPos.z);
                                world.spawnEntity(e);
                            }
                        }
                    }
                    double maxDistance = DRAW_DISTANCE;
                    PlayerEntity player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), RANGE, false);
                    Vec3d vec3d = player.getEyePos();
                    Vec3d vec3d2;
                    EntityHitResult entityHitResult = ProjectileUtil.raycast(player, vec3d, vec3d.add(vec3d2 = player.getRotationVec(1.0f).multiply(maxDistance)), player.getBoundingBox().stretch(vec3d2).expand(1.0), entity -> !entity.isSpectator() && entity.canHit(), maxDistance * maxDistance);
                    if (entityHitResult != null) {
                        Entity e = entityHitResult.getEntity();
                        if (e instanceof BoatEntity) {
                            player.sendMessage(Text.of("hit!"), false);
                        }
                    }
                } else {
//                    blockEntity.map.forEach((s, uuid) -> ((ServerWorld) world).getEntity(uuid).discard());
//                    blockEntity.map.clear();
                }
            }
        }
    }
}
