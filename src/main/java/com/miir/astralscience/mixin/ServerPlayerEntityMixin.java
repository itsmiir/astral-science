package com.miir.astralscience.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }
//    @Shadow public abstract ServerWorld getServerWorld();
//    @Shadow public abstract ServerWorld getWorld();

    @Inject(
            method = "setSpawnPoint",
            at = @At("HEAD")
    )
    private void spaceshipRespawn(RegistryKey<World> dimension, BlockPos pos, float angle, boolean spawnPointSet, boolean bl, CallbackInfo ci) {
        if (this.getWorld().getBlockState(pos).isIn(BlockTags.BEDS)) {
            if (this.getWorld().getBlockEntity(pos) != null) {
                if (this.getWorld().getBlockEntity(pos) instanceof BedBlockEntity) {
//                    ((BedBlockEntity)this.getWorld().getBlockEntity(pos)).((ServerPlayerEntity)(Object)this).getUUID();
                }
            }
//            this.getServerWorld().getBlockState(pos).getBlock()
        }
    }
}
