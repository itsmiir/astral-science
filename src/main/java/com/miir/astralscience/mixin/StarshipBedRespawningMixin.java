package com.miir.astralscience.mixin;

import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class StarshipBedRespawningMixin {
//    @Shadow public abstract ServerWorld getServerWorld();

    @Shadow public abstract ServerWorld getWorld();

    @Inject(
            method = "setSpawnPoint",
            at = @At("HEAD")
    )
    private void mixin(RegistryKey<World> dimension, BlockPos pos, float angle, boolean spawnPointSet, boolean bl, CallbackInfo ci) {
        if (this.getWorld().getBlockState(pos).isIn(BlockTags.BEDS)) {
            if (this.getWorld().getBlockEntity(pos) != null) {
                if (this.getWorld().getBlockEntity(pos) instanceof BedBlockEntity) {
//                    ((BedBlockEntity)this.getServerWorld().getBlockEntity(pos)).addSleeper((ServerPlayerEntity)(Object)this).getUUID();
                }
            }
//            this.getServerWorld().getBlockState(pos).getBlock()
        }
    }
}
