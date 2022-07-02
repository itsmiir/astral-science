package com.miir.astralscience.mixin;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.Config;
import com.miir.astralscience.util.Text;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class ReentryMixin {
    @Shadow
    public World world;

    @Shadow
    public abstract @Nullable MinecraftServer getServer();

    @Shadow private Vec3d pos;

    @Shadow private Vec3d velocity;

    @Shadow public float yaw;

    @Shadow public float pitch;

    @Shadow public abstract BlockPos getBlockPos();

    @Shadow public abstract double getX();

    @Shadow public abstract double getZ();

    @Shadow public abstract boolean canUsePortals();

    @Inject(
            method = "attemptTickInVoid",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;tickInVoid()V",
                    shift = At.Shift.BEFORE
            )
    )
    private void reentry(CallbackInfo ci) {
        if (this.world != null) {
            try {
                if (AstralDimensions.isOrbit(this.world) && this.world instanceof ServerWorld) {
                    Identifier hostPath;
                    if (world.getRegistryKey().getValue().equals(AstralScience.id("overworld_orbit"))) {
                        hostPath = new Identifier("overworld");
                    } else {
                        hostPath = AstralScience.id(Text.deorbitify(this.world.getRegistryKey().getValue().getPath()));
                    }
                    ServerWorld destination = this.getServer().getWorld(RegistryKey.of(Registry.WORLD_KEY, hostPath));
                    FabricDimensions.teleport((Entity) (Object) this, destination, new TeleportTarget(this.pos.add(0, Config.ORBIT_DROP_HEIGHT + 64, 0), this.velocity, this.yaw, this.pitch));
                    if (true) {
                        return;
                    }
//                stops the compiler from being mad at the return
                    AstralScience.LOGGER.error("I don't know how you got here, but if you're seeing this, there is a big problem.");
                }
            } catch (NullPointerException ignored) {}
        }
    }

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void orbit(CallbackInfo ci) {
        if (this.world != null) {
            if (this.world instanceof ServerWorld && this.canUsePortals() && !AstralDimensions.isOrbit(this.world)) {
                if (this.getBlockPos().getY() >= Config.ORBIT_HEIGHT) {
                    String path = this.world.getRegistryKey().getValue().getPath();
                    String orbitalPath = path + AstralScience.ORBIT_SUFFIX;
                    RegistryKey<World> key = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id(orbitalPath));
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
}
