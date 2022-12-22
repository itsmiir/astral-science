package com.miir.astralscience.block.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.item.AstralItems;
import com.miir.astralscience.screen.CascadicCoolerScreenHandler;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CascadicCoolerBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider {

    private static final int[] TOP_SLOTS = new int[]{0};
    private static final int[] BOTTOM_SLOTS = new int[]{2, 1};
    private static final int[] SIDE_SLOTS = new int[]{1};
    protected DefaultedList<ItemStack> inventory;
    private int burnTime;
    private int fuelTime;
    private int cookTime;
    private int cookTimeTotal;
    protected final PropertyDelegate propertyDelegate;
    private final Object2IntOpenHashMap<Identifier> recipesUsed;
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;

    public CascadicCoolerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(AstralBlocks.CASCADIC_COOLER_TYPE, blockPos, blockState);
        this.inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> CascadicCoolerBlockEntity.this.burnTime;
                    case 1 -> CascadicCoolerBlockEntity.this.fuelTime;
                    case 2 -> CascadicCoolerBlockEntity.this.cookTime;
                    case 3 -> CascadicCoolerBlockEntity.this.cookTimeTotal;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CascadicCoolerBlockEntity.this.burnTime = value;
                    case 1 -> CascadicCoolerBlockEntity.this.fuelTime = value;
                    case 2 -> CascadicCoolerBlockEntity.this.cookTime = value;
                    case 3 -> CascadicCoolerBlockEntity.this.cookTimeTotal = value;
                }

            }

            public int size() {
                return 4;
            }
        };
        this.recipesUsed = new Object2IntOpenHashMap<>();
        this.recipeType = com.miir.astralscience.recipe.Recipe.COOLING;
    }

    public static Map<Item, Integer> createFuelTimeMap() {
        Map<Item, Integer> map = Maps.newLinkedHashMap();
        addFuel(map, AstralBlocks.CASCADIUM_BLOCK, 18000);
        addFuel(map, AstralItems.CASCADIUM_SHARD, 2000);
        return map;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CascadicCoolerScreenHandler(i, playerInventory);
    }

    private static void addFuel(Map<Item, Integer> fuelTimes, TagKey<Item> tag, int fuelTime) {
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(tag)) {
            fuelTimes.put(registryEntry.value(), fuelTime);
        }
    }

    private static void addFuel(Map<Item, Integer> map, ItemConvertible item, int fuelTime) {
        Item item2 = item.asItem();
        map.put(item2, fuelTime);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.burnTime = tag.getShort("BurnTime");
        this.fuelTime = tag.getShort("FuelTime");
        Inventories.readNbt(tag, this.inventory);
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        tag.putShort("BurnTime", (short) this.burnTime);
        tag.putShort("FuelTime", (short) this.burnTime);
        Inventories.writeNbt(tag, this.inventory);
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.astralscience.cooler");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new CascadicCoolerScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, CascadicCoolerBlockEntity cascadicCoolerBlockEntity) {
        boolean bl = cascadicCoolerBlockEntity.isBurning();
        boolean bl2 = false;
        if (cascadicCoolerBlockEntity.isBurning()) {
            --cascadicCoolerBlockEntity.burnTime;
        }

        ItemStack itemStack = cascadicCoolerBlockEntity.inventory.get(1);
        if (cascadicCoolerBlockEntity.isBurning() || !itemStack.isEmpty() && !cascadicCoolerBlockEntity.inventory.get(0).isEmpty()) {
            Recipe recipe = world.getRecipeManager().getFirstMatch(cascadicCoolerBlockEntity.recipeType, cascadicCoolerBlockEntity, world).orElse(null);
            int i = cascadicCoolerBlockEntity.getMaxCountPerStack();
            if (!cascadicCoolerBlockEntity.isBurning() && canAcceptRecipeOutput(recipe, cascadicCoolerBlockEntity.inventory, i)) {
                cascadicCoolerBlockEntity.burnTime = cascadicCoolerBlockEntity.getFuelTime(itemStack);
                cascadicCoolerBlockEntity.fuelTime = cascadicCoolerBlockEntity.burnTime;
                if (cascadicCoolerBlockEntity.isBurning()) {
                    bl2 = true;
                    if (!itemStack.isEmpty()) {
                        Item item = itemStack.getItem();
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            Item item2 = item.getRecipeRemainder();
                            cascadicCoolerBlockEntity.inventory.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                        }
                    }
                }
            }

            if (cascadicCoolerBlockEntity.isBurning() && canAcceptRecipeOutput(recipe, cascadicCoolerBlockEntity.inventory, i)) {
                ++cascadicCoolerBlockEntity.cookTime;
                if (cascadicCoolerBlockEntity.cookTime == cascadicCoolerBlockEntity.cookTimeTotal) {
                    cascadicCoolerBlockEntity.cookTime = 0;
                    cascadicCoolerBlockEntity.cookTimeTotal = getCookTime(world, cascadicCoolerBlockEntity.recipeType, cascadicCoolerBlockEntity);
                    if (craftRecipe(recipe, cascadicCoolerBlockEntity.inventory, i)) {
                        cascadicCoolerBlockEntity.setLastRecipe(recipe);
                    }

                    bl2 = true;
                }
            } else {
                cascadicCoolerBlockEntity.cookTime = 0;
            }
        } else if (!cascadicCoolerBlockEntity.isBurning() && cascadicCoolerBlockEntity.cookTime > 0) {
            cascadicCoolerBlockEntity.cookTime = MathHelper.clamp(cascadicCoolerBlockEntity.cookTime - 2, 0, cascadicCoolerBlockEntity.cookTimeTotal);
        }

        if (bl != cascadicCoolerBlockEntity.isBurning()) {
            bl2 = true;
            blockState = blockState.with(AbstractFurnaceBlock.LIT, cascadicCoolerBlockEntity.isBurning());
            world.setBlockState(blockPos, blockState, 3);
        }

        if (bl2) {
            markDirty(world, blockPos, blockState);
        }

    }

    private static boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> defaultedList, int i) {
        if (!defaultedList.get(0).isEmpty() && recipe != null) {
            ItemStack itemStack = recipe.getOutput();
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack2 = defaultedList.get(2);
                if (itemStack2.isEmpty()) {
                    return true;
                } else if (!itemStack2.isItemEqual(itemStack)) {
                    return false;
                } else if (itemStack2.getCount() < i && itemStack2.getCount() < itemStack2.getMaxCount()) {
                    return true;
                } else {
                    return itemStack2.getCount() < itemStack.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }

    private static boolean craftRecipe(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> defaultedList, int i) {
        if (recipe != null && canAcceptRecipeOutput(recipe, defaultedList, i)) {
            ItemStack itemStack = defaultedList.get(0);
            ItemStack itemStack2 = recipe.getOutput();
            ItemStack itemStack3 = defaultedList.get(2);
            if (itemStack3.isEmpty()) {
                defaultedList.set(2, itemStack2.copy());
            } else if (itemStack3.isOf(itemStack2.getItem())) {
                itemStack3.increment(1);
            }
            itemStack.decrement(1);
            return true;
        } else {
            return false;
        }
    }

    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            Item item = fuel.getItem();
            return (Integer)createFuelTimeMap().getOrDefault(item, 0);
        }
    }

    private static int getCookTime(World world, RecipeType<? extends AbstractCookingRecipe> recipeType, Inventory inventory) {
        return (Integer)world.getRecipeManager().getFirstMatch(recipeType, inventory, world).map(AbstractCookingRecipe::getCookTime).orElse(200);
    }

    public static boolean canUseAsFuel(ItemStack stack) {
        return createFuelTimeMap().containsKey(stack.getItem());
    }

    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        } else {
            return side == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
        }
    }

    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return dir != Direction.DOWN || slot != 1;
    }
    public int size() {
        return this.inventory.size();
    }

    public boolean isEmpty() {
        Iterator var1 = this.inventory.iterator();

        ItemStack itemStack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemStack = (ItemStack)var1.next();
        } while(itemStack.isEmpty());

        return false;
    }

    public ItemStack getStack(int slot) {
        return (ItemStack)this.inventory.get(slot);
    }

    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areNbtEqual(stack, itemStack);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }

        if (slot == 0 && !bl) {
            this.cookTimeTotal = getCookTime(this.world, this.recipeType, this);
            this.cookTime = 0;
            this.markDirty();
        }

    }

    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        } else if (slot == 0) {
            return true;
        } else {
            return canUseAsFuel(stack);
        }
    }

    public void clear() {
        this.inventory.clear();
    }

    public void setLastRecipe(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            Identifier identifier = recipe.getId();
            this.recipesUsed.addTo(identifier, 1);
        }

    }

    @Nullable
    public Recipe<?> getLastRecipe() {
        return null;
    }

    public void unlockLastRecipe(PlayerEntity player) {
    }

    public List<Recipe<?>> method_27354(ServerWorld serverWorld, Vec3d vec3d) {
        List<Recipe<?>> list = Lists.newArrayList();

        for (Object2IntMap.Entry<Identifier> identifierEntry : this.recipesUsed.object2IntEntrySet()) {
            serverWorld.getRecipeManager().get(((Object2IntMap.Entry<Identifier>) (Object2IntMap.Entry) identifierEntry).getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                dropExperience(serverWorld, vec3d, ((Object2IntMap.Entry<Identifier>) (Object2IntMap.Entry) identifierEntry).getIntValue(), ((AbstractCookingRecipe) recipe).getExperience());
            });
        }

        return list;
    }

    private static void dropExperience(ServerWorld serverWorld, Vec3d vec3d, int i, float f) {
        int j = MathHelper.floor((float)i * f);
        float g = MathHelper.fractionalPart((float)i * f);
        if (g != 0.0F && Math.random() < (double)g) {

            ++j;
        }

        ExperienceOrbEntity.spawn(serverWorld, vec3d, j);
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {

        for (ItemStack itemStack : this.inventory) {
            finder.addInput(itemStack);
        }

    }
}
