package com.miir.astralscience.mixin;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.Config;
import com.miir.astralscience.util.AstralText;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public World world;
    @Shadow public abstract @Nullable MinecraftServer getServer();
    @Shadow private Vec3d pos;
    @Shadow private Vec3d velocity;
    @Shadow private float yaw;
    @Shadow private float pitch;
    @Shadow public abstract BlockPos getBlockPos();
    @Shadow public abstract double getX();
    @Shadow public abstract double getZ();
    @Shadow public abstract boolean canUsePortals();

    @Inject(method = "attemptTickInVoid", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tickInVoid()V", shift = At.Shift.BEFORE))
    private void reenter(CallbackInfo ci) {
        if (this.world != null) {
            try {
                if (AstralDimensions.isOrbit(this.world) && this.world instanceof ServerWorld) {
                    Identifier hostPath;
                    if (world.getRegistryKey().getValue().equals(AstralScience.id("overworld_orbit"))) {
                        hostPath = new Identifier("overworld");
                    } else {
                        hostPath = AstralScience.id(AstralText.deorbitify(this.world.getRegistryKey().getValue().getPath()));
                    }
                    ServerWorld destination = this.getServer().getWorld(RegistryKey.of(RegistryKeys.WORLD, hostPath));
                    FabricDimensions.teleport((Entity) (Object) this, destination, new TeleportTarget(this.pos.add(0, Config.ORBIT_DROP_HEIGHT + 64, 0), this.velocity, this.yaw, this.pitch));
                    ci.cancel();
                }
            } catch (NullPointerException ignored) {}
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void orbit(CallbackInfo ci) {
        if (this.world != null) {
            if (this.world instanceof ServerWorld && this.canUsePortals() && AstralDimensions.hasOrbitalDimension(this.world)) {
                if (this.getBlockPos().getY() >= Config.ORBIT_HEIGHT) {
                    String path = this.world.getRegistryKey().getValue().getPath();
                    String orbitalPath = path + AstralScience.ORBIT_SUFFIX;
                    RegistryKey<World> key = RegistryKey.of(RegistryKeys.WORLD, AstralScience.id(orbitalPath));
                    ServerWorld destination = this.getServer().getWorld(key);
                    FabricDimensions.teleport((Entity) (Object) this, destination, new TeleportTarget(
                            new Vec3d(this.getX(), -52D, this.getZ()),
                            this.velocity,
                            this.yaw,
                            this.pitch
                    ));
                }
            }
        }
    }
    @ModifyArg(
            method = "handleFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z"
            ),
            index = 0
    )
    private float fallDamageModifier(float fallDistance) {
        if (world.getRegistryKey().getValue().getNamespace().equals(AstralScience.MOD_ID)) {
            float gravity = Config.GRAVITY_OMEIA;
            float multiplier = (float) (gravity / 9.81); // hardcoding this because i assume that gEarth will not change, sue me
            return fallDistance * multiplier;
        }
        return fallDistance;
    }
}
