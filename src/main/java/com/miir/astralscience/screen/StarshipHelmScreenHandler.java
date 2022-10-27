package com.miir.astralscience.screen;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.block.entity.StarshipHelmBlockEntity;
import com.miir.astralscience.client.gui.screen.StarshipHelmMapSlot;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class StarshipHelmScreenHandler extends AstralScreenHandler {
    private BlockPos pos;
    private StarshipHelmBlockEntity helm;
    public RegistryKey<World> destination;
    private World world;
    public StarshipHelmScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf packet) {
        this(syncId, playerInventory, new SimpleInventory(StarshipHelmBlockEntity.SIZE));
        this.pos = packet.readBlockPos();
        BlockEntity blockEntity = playerInventory.player.world.getBlockEntity(this.pos);
        if (blockEntity != null) {
            if (blockEntity instanceof StarshipHelmBlockEntity) {
                this.helm = (StarshipHelmBlockEntity) blockEntity;
                this.world = blockEntity.getWorld();
                this.destination = this.helm.getWorld().getRegistryKey();
            }
        }
    }
    public StarshipHelmScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(AstralScreens.STARSHIP_HELM_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.addSlotArray(this.inventory, 1, 3, 8, 17);
        this.addPlayerInventory(playerInventory);
        this.addSlot(new StarshipHelmMapSlot(inventory, 3, 151, 56));
        this.pos = AstralScience.INVALID;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public StarshipHelmBlockEntity getHelm() {
        return (StarshipHelmBlockEntity) this.world.getBlockEntity(this.pos);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public static boolean canWarpTo(Identifier destination, StarshipHelmBlockEntity helm) {
        return true;
//        int fuel = this.helm.fuel;
//        int energy = this.helm.energy;
//        return requiredFuel(destination) < fuel && requiredEnergy(destination) < energy;
    }

    private static int requiredEnergy(Identifier destination, StarshipHelmBlockEntity helm) {
        return 1000000000;
    }

    private static int requiredFuel(Identifier destination, StarshipHelmBlockEntity helm) {
        return 1000000000;
    }

    public ServerPlayerEntity toServerEntity(PlayerEntity playerEntity) {
        if (playerEntity instanceof ServerPlayerEntity) {
            return (ServerPlayerEntity) playerEntity;
        }
        ClientPlayerEntity client = (ClientPlayerEntity) playerEntity;
        if (this.helm.getWorld() instanceof ServerWorld) {
            MinecraftServer server = this.helm.getWorld().getServer();
            return server.getPlayerManager().getPlayer(client.getEntityName());
        }
        return null;
    }
}
