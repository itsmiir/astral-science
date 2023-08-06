package com.miir.astralscience.block.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.miir.astralscience.block.AstralBlocks;
import com.miir.astralscience.item.AstralItems;
import com.miir.astralscience.screen.CascadicHeaterScreenHandler;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
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
import net.minecraft.registry.DynamicRegistryManager;
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

public class CascadicHeaterBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider {

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

    public CascadicHeaterBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(AstralBlocks.CASCADIC_HEATER_TYPE, blockPos, blockState);
        this.inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch(index) {
                    case 0:
                        return CascadicHeaterBlockEntity.this.burnTime;
                    case 1:
                        return CascadicHeaterBlockEntity.this.fuelTime;
                    case 2:
                        return CascadicHeaterBlockEntity.this.cookTime;
                    case 3:
                        return CascadicHeaterBlockEntity.this.cookTimeTotal;
                    default:
                        return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0:
                        CascadicHeaterBlockEntity.this.burnTime = value;
                        break;
                    case 1:
                        CascadicHeaterBlockEntity.this.fuelTime = value;
                        break;
                    case 2:
                        CascadicHeaterBlockEntity.this.cookTime = value;
                        break;
                    case 3:
                        CascadicHeaterBlockEntity.this.cookTimeTotal = value;
                }

            }

            public int size() {
                return 4;
            }
        };
        this.recipesUsed = new Object2IntOpenHashMap();
        this.recipeType = com.miir.astralscience.recipe.Recipe.HEATING;
    }

    public static Map<Item, Integer> createFuelTimeMap() {
        Map<Item, Integer> map = Maps.newLinkedHashMap();
        addFuel(map, AstralBlocks.CASCADIUM_BLOCK, 18000);
        addFuel(map, AstralItems.CASCADIUM_SHARD, 2000);
        return map;
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

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.astralscience.heater");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new CascadicHeaterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CascadicHeaterScreenHandler(i, playerInventory);
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, CascadicHeaterBlockEntity cascadicHeaterBlockEntity) {
        boolean bl = cascadicHeaterBlockEntity.isBurning();
        boolean bl2 = false;
        if (cascadicHeaterBlockEntity.isBurning()) {
            --cascadicHeaterBlockEntity.burnTime;
        }

        ItemStack itemStack = cascadicHeaterBlockEntity.inventory.get(1);
        if (cascadicHeaterBlockEntity.isBurning() || !itemStack.isEmpty() && !cascadicHeaterBlockEntity.inventory.get(0).isEmpty()) {
            Recipe recipe = world.getRecipeManager().getFirstMatch(cascadicHeaterBlockEntity.recipeType, cascadicHeaterBlockEntity, world).orElse(null);
            int i = cascadicHeaterBlockEntity.getMaxCountPerStack();
            if (!cascadicHeaterBlockEntity.isBurning() && canAcceptRecipeOutput(cascadicHeaterBlockEntity.getWorld().getRegistryManager(), recipe, cascadicHeaterBlockEntity.inventory, i)) {
                cascadicHeaterBlockEntity.burnTime = cascadicHeaterBlockEntity.getFuelTime(itemStack);
                cascadicHeaterBlockEntity.fuelTime = cascadicHeaterBlockEntity.burnTime;
                if (cascadicHeaterBlockEntity.isBurning()) {
                    bl2 = true;
                    if (!itemStack.isEmpty()) {
                        Item item = itemStack.getItem();
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            Item item2 = item.getRecipeRemainder();
                            cascadicHeaterBlockEntity.inventory.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                        }
                    }
                }
            }

            if (cascadicHeaterBlockEntity.isBurning() && canAcceptRecipeOutput(cascadicHeaterBlockEntity.getWorld().getRegistryManager(), recipe, cascadicHeaterBlockEntity.inventory, i)) {
                ++cascadicHeaterBlockEntity.cookTime;
                if (cascadicHeaterBlockEntity.cookTime == cascadicHeaterBlockEntity.cookTimeTotal) {
                    cascadicHeaterBlockEntity.cookTime = 0;
                    cascadicHeaterBlockEntity.cookTimeTotal = getCookTime(world, cascadicHeaterBlockEntity.recipeType, cascadicHeaterBlockEntity);
                    if (craftRecipe(cascadicHeaterBlockEntity.getWorld().getRegistryManager(), recipe, cascadicHeaterBlockEntity.inventory, i)) {
                        cascadicHeaterBlockEntity.setLastRecipe(recipe);
                    }

                    bl2 = true;
                }
            } else {
                cascadicHeaterBlockEntity.cookTime = 0;
            }
        } else if (!cascadicHeaterBlockEntity.isBurning() && cascadicHeaterBlockEntity.cookTime > 0) {
            cascadicHeaterBlockEntity.cookTime = MathHelper.clamp(cascadicHeaterBlockEntity.cookTime - 2, 0, cascadicHeaterBlockEntity.cookTimeTotal);
        }

        if (bl != cascadicHeaterBlockEntity.isBurning()) {
            bl2 = true;
            blockState = blockState.with(AbstractFurnaceBlock.LIT, cascadicHeaterBlockEntity.isBurning());
            world.setBlockState(blockPos, blockState, 3);
        }

        if (bl2) {
            markDirty(world, blockPos, blockState);
        }

    }

    private static boolean canAcceptRecipeOutput(DynamicRegistryManager manager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> defaultedList, int i) {
        if (!defaultedList.get(0).isEmpty() && recipe != null) {
            ItemStack itemStack = recipe.getOutput(manager);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack2 = defaultedList.get(2);
                if (itemStack2.isEmpty()) {
                    return true;
                } else if (!itemStack2.itemMatches(itemStack.getRegistryEntry())) {
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

    private static boolean craftRecipe(DynamicRegistryManager manager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> defaultedList, int i) {
        if (recipe != null && canAcceptRecipeOutput(manager, recipe, defaultedList, i)) {
            ItemStack itemStack = defaultedList.get(0);
            ItemStack itemStack2 = recipe.getOutput(manager);
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
            return createFuelTimeMap().getOrDefault(item, 0);
        }
    }

    private static int getCookTime(World world, RecipeType<? extends AbstractCookingRecipe> recipeType, Inventory inventory) {
        return world.getRecipeManager().getFirstMatch(recipeType, inventory, world).map(AbstractCookingRecipe::getCookTime).orElse(200);
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
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        this.burnTime = nbt.getShort("BurnTime");
        this.cookTime = nbt.getShort("CookTime");
        this.cookTimeTotal = nbt.getShort("CookTimeTotal");
        this.fuelTime = this.getFuelTime(this.inventory.get(1));
        NbtCompound nbtCompound = nbt.getCompound("RecipesUsed");
        Iterator var3 = nbtCompound.getKeys().iterator();

        while(var3.hasNext()) {
            String string = (String)var3.next();
            this.recipesUsed.put(new Identifier(string), nbtCompound.getInt(string));
        }

    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("BurnTime", (short)this.burnTime);
        nbt.putShort("CookTime", (short)this.cookTime);
        nbt.putShort("CookTimeTotal", (short)this.cookTimeTotal);
        Inventories.writeNbt(nbt, this.inventory);
        NbtCompound nbtCompound = new NbtCompound();
        this.recipesUsed.forEach((identifier, integer) -> {
            nbtCompound.putInt(identifier.toString(), integer);
        });
        nbt.put("RecipesUsed", nbtCompound);
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
        return this.inventory.get(slot);
    }

    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && stack.itemMatches(itemStack.getRegistryEntry()) && ItemStack.areItemsEqual(stack, itemStack);
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
        ObjectIterator var4 = this.recipesUsed.object2IntEntrySet().iterator();

        while(var4.hasNext()) {
            Object2IntMap.Entry<Identifier> entry = (Object2IntMap.Entry)var4.next();
            serverWorld.getRecipeManager().get(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                dropExperience(serverWorld, vec3d, entry.getIntValue(), ((AbstractCookingRecipe)recipe).getExperience());
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
