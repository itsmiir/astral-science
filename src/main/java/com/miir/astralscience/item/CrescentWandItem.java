package com.miir.astralscience.item;

import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.block.StarlightCollectorBlock;
import com.miir.astralscience.magic.rune.AstralRunes;
import com.miir.astralscience.magic.spell.SpellParser;
import com.miir.astralscience.world.dimension.AstralDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrescentWandItem extends Item {
    private static final int CHARGE_COLOR = MathHelper.packRgb(26, 160, 139);
    private static final int MAX_CHARGE = 500;

    public CrescentWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        String runes = getRunes(user.getStackInHand(hand));
        user.sendMessage(Text.of(runes), true);
//        SpellParser.cast(getRunes(user.getStackInHand(hand)), user, hand);
        if (user.isSneaking()) {
            user.openHandledScreen(((NamedScreenHandlerFactory) AstralItems.CRESCENT_WAND));
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.astralscience.crescent_wand.charge", getCharge(stack), MAX_CHARGE).formatted(Formatting.AQUA));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.min(getCharge(stack) / MAX_CHARGE * 13, 13);
    }



    private int getCharge(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        if (tag.contains("charge")) {
            return tag.getInt("charge");
        } else {
            tag.putInt("charge", 0);
            return 0;
        }
    }
    private void charge(ItemStack stack, int charge) {
        int i = getCharge(stack);
        if (i <= MAX_CHARGE) {
            NbtCompound tag = stack.getOrCreateNbt();
            tag.putInt("charge", Math.min(MAX_CHARGE, i + charge));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int charge = getCharge(stack);
        if (charge < MAX_CHARGE) {
            if (entity instanceof PlayerEntity && world instanceof ServerWorld) {
                if (StarlightCollectorBlock.hasStarlight(world, entity.getBlockPos())) {
                    if (world.getServer().getTicks() % 40 == 0) {
                        int i = AstralDimensions.isOrbit(world) ? 2 : 1;
                        charge(stack, i);
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return CHARGE_COLOR;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        Random random = world.getRandom();
        BlockState state = context.getWorld().getBlockState(pos);
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        if (player != null) {
            if (player.isSneaking()) {
                if (state.isOf(AstralBlocks.STARLIGHT_COLLECTOR) && state.get(StarlightCollectorBlock.LIT)) {
                    char rune = Integer.toHexString(state.get(StarlightCollectorBlock.RUNE)).charAt(0);
                    addRune(stack, rune);
                    for (int i = 0; i < 15; i++) {
                        context.getWorld().addParticle(ParticleTypes.INSTANT_EFFECT,
                                false,
                                pos.getX() + random.nextDouble(),
                                pos.getY() + random.nextDouble() + 0.5,
                                pos.getZ() + random.nextDouble(),
                                0, 0, 0);
                    }
                }
                else if (StarlightCollectorBlock.checkPattern(pos, world, pos.offset(Direction.NORTH, 3))) {
                    if (AstralRunes.runesMatch(world, state, pos) && AstralRunes.hasStarlight(world, pos)) {
                        Vec3d pos3d = Vec3d.ofCenter(pos);
                        BlockState newState = AstralRunes.transmute(state);
                        world.setBlockState(pos, newState);
                        world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                        for (int i = 0; i < 20; i++) {
                            int x = random.nextBoolean() ? 1 : -1;
                            int z = random.nextBoolean() ? 1 : -1;
                            world.addParticle(
                                    ParticleTypes.END_ROD,
                                    x * (random.nextDouble()) + pos3d.getX(),
                                    (random.nextDouble()) + pos3d.getY(),
                                    z * (random.nextDouble()) + pos3d.getZ(),
                                    0, 0, 0
                            );
                        }
                    } else {
                        world.playSound(player, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    }
                    return ActionResult.SUCCESS;
                }
            }
            else {
                if (getRunes(stack).length() > 0) {
                    if (SpellParser.cast(getRunes(stack), context)) {
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return super.useOnBlock(context);
    }

    private void addRune(ItemStack stack, char rune) {
        String runes = getRunes(stack);
        runes = runes + rune;
        NbtCompound tag = stack.getOrCreateNbt();
        tag.putString("runes", runes);
    }

    private String getRunes(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        if (tag.contains("runes")) {
            return tag.getString("runes");
        } else return "";
    }
    public static SimpleInventory getInventory(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        DefaultedList<ItemStack> stacks = DefaultedList.ofSize(3, new ItemStack(Blocks.AIR));
        Inventories.readNbt(tag, stacks);
        SimpleInventory inventory = new SimpleInventory(3);
        return inventory;
    }
    public static void putInventory(ItemStack stack, DefaultedList<ItemStack> inventory) {
        NbtCompound tag = stack.getOrCreateNbt();
        Inventories.writeNbt(tag, inventory);
    }

}
