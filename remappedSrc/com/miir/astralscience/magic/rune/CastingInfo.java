package com.miir.astralscience.magic.rune;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CastingInfo {
    public final PlayerEntity caster;
    public final World world;
    public final Vec3d pos;
    @Nullable
    public final Target target;
    public CastingInfo(PlayerEntity caster, World world, Vec3d pos, @Nullable Target target) {
        this.caster = caster;
        this.world = world;
        this.pos = pos;
        this.target = target;
    }

    public boolean hasTarget() {
        return this.target != null;
    }

    public static class Target {
        private final World world;
        @Nullable
        public final BlockState state;
        public final Vec3d pos;
        public final boolean isEntity;

        public Target(Entity entity) {
            this.state = null;
            this.pos = entity.getPos();
            this.isEntity = true;
            this.world = entity.world;
        }
        public Target(World world, BlockPos pos) {
            this.state = world.getBlockState(pos);
            this.pos = Vec3d.ofCenter(pos);
            this.isEntity = false;
            this.world = world;
        }

    }
}
