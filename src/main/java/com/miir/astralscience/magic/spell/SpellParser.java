package com.miir.astralscience.magic.spell;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class SpellParser {

    private static boolean tryParse(String spell) {
        return false;
    }

    public static boolean cast(String spell, PlayerEntity user, Hand hand) {
        Random random = new Random(user.getBlockPos().hashCode());
        int mod = 2;
        //        so that the client and server sides are in sync
        if (tryParse(spell)) {
            return false;
        } else {
            char[] runes = spell.toCharArray();
            if (runes.length == 1) {
                switch (runes[0]) {
                    case '0':
                    case '5':
                    case 'f':
                    case 'e':
                    case 'b':
                        return false;
                    case '1':
                        return waergo(user, mod);
                    //                runes that are not commands or modifiers
                    case '2':
                    case '3':
                    case '7':
                    case '8':
                    case '9':
                    case 'a':
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    public static boolean cast(String spell, ItemUsageContext ctx) {
        Random random = new Random(ctx.getBlockPos().hashCode());
        int mod = 1;
        //        so that the client and server sides are in sync
        if (tryParse(spell)) {
            return false;
        } else {
            char[] runes = spell.toCharArray();
            if (runes.length == 1) {
                switch (runes[0]) {
                    case '0':
                        return figma(ctx, random);
                    case '1':
                        return waergo(ctx.getPlayer(), mod);
                    case '2':
                        return physe(ctx, mod);
                    case '5':
                    case 'f':
                    case 'e':
                    case 'b':
                        return false;
                    //                runes that are not commands or modifiers
                    case '3':
                        return kyter(ctx, mod);
                    case '7':
                    case '8':
                    case '9':
                    case 'a':
                        return mica(ctx, mod);
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    //    single-rune spells

    private static boolean figma(ItemUsageContext ctx, Random random) {
        PlayerEntity caster = ctx.getPlayer();
        PlayerInventory inventory = caster.getInventory();
        int i = random.nextInt(9);
        int j = 0;
        ItemStack stack = inventory.getStack(i);
        while (!(stack.getItem() instanceof BlockItem) && j < 27) {
            i = random.nextInt(9);
            stack = inventory.getStack(i);
            j++;
        }
        if ((stack.getItem() instanceof BlockItem)) {
            BlockHitResult hit = new BlockHitResult(ctx.getHitPos(), ctx.getSide(), ctx.getBlockPos(), ctx.hitsInsideBlock());
            ((BlockItem) stack.getItem()).place(new ItemPlacementContext(ctx.getPlayer(), ctx.getHand(), stack, hit));
            return true;
        } else return false;
    }

    private static boolean waergo(PlayerEntity playerEntity, int mod) {
        int i = 10 * mod + 10;
        HitResult target = playerEntity.raycast(i, 1, false);
        if (target.getType() == HitResult.Type.ENTITY || target.getType() == HitResult.Type.BLOCK) {
            double x = target.getPos().getX();
            double y = target.getPos().getY();
            double z = target.getPos().getZ();
            playerEntity.requestTeleport(x, y, z);
            playerEntity.world.playSound(x, y, z, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
            return true;
        }
        return false;
    }

    private static boolean physe(ItemUsageContext ctx, int mod) {
        ItemStack stack;
        switch (mod) {
            case 0:
                stack = new ItemStack(Blocks.GRAVEL);
            case 2:
                stack = new ItemStack(Blocks.DEEPSLATE);
            default:
                stack = new ItemStack(Blocks.STONE);
        }
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos().offset(ctx.getSide());
        net.minecraft.util.math.random.Random random = world.getRandom();
        BlockHitResult hit = new BlockHitResult(ctx.getHitPos(), ctx.getSide(), ctx.getBlockPos(), ctx.hitsInsideBlock());
        if (((BlockItem) stack.getItem()).place(new ItemPlacementContext(ctx.getPlayer(), ctx.getHand(), stack, hit)).equals(ActionResult.success(true))) {
            for (int i = 0; i < 25; i++) {
                world.addParticle(
                        ParticleTypes.END_ROD,
                        pos.getX() - 1 + random.nextDouble() + random.nextInt(3),
                        pos.getY() + random.nextDouble(),
                        pos.getZ() - 1 + random.nextDouble() + random.nextInt(3),
                        0, 0, 0);
            }
        }
        return true;
    }

    private static boolean kyter(ItemUsageContext ctx, int mod) {
        int r = mod * 5;
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        PlayerEntity player = ctx.getPlayer();
        Box box = player.getBoundingBox().expand(r, r, r);
        if (world instanceof ServerWorld) {

            List<Entity> entities = player.world.getNonSpectatingEntities(
                    Entity.class,
                    box
            );
            for (Entity entity :
                    entities) {
                if (!entity.hasNoGravity()) {
                    double dX = entity.getX() - player.getX();
                    double dY = entity.getY() - player.getY();
                    double dZ = entity.getZ() - player.getZ();
                    double x = dX == 0 ? 0 : -((dX / Math.abs(dX)) * r + dX) / 4;
                    double y = dY == 0 ? 0 : -((dY / Math.abs(dY)) * r + dY) / 4;
                    double z = dZ == 0 ? 0 : -((dZ / Math.abs(dZ)) * r + dZ) / 4;
                    entity.addVelocity(x, y, z);
                }
            }
        }
        return true;
    }

    private static boolean mica(ItemUsageContext ctx, int mod) {
        int r = mod * 5;
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        PlayerEntity player = ctx.getPlayer();
        Box box = player.getBoundingBox().expand(r, r, r);
        if (world instanceof ServerWorld) {

            List<Entity> entities = player.world.getNonSpectatingEntities(
                    Entity.class,
                    box
            );
            for (Entity entity :
                    entities) {
                if (!(entity instanceof AbstractDecorationEntity)) {
                    double dX = entity.getX() - player.getX();
                    double dY = entity.getY() - player.getY();
                    double dZ = entity.getZ() - player.getZ();
                    double x = dX == 0 ? 0 : -((dX / Math.abs(dX)) * r + dX) / 4;
                    double y = dY == 0 ? 0 : -((dY / Math.abs(dY)) * r + dY) / 4;
                    double z = dZ == 0 ? 0 : -((dZ / Math.abs(dZ)) * r + dZ) / 4;
                    entity.addVelocity(-x, -y, -z);
                }
            }
        }
        return true;
    }
}
