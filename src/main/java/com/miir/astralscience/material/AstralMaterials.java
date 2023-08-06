package com.miir.astralscience.material;

import com.miir.astralscience.item.AstralItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum AstralMaterials implements ArmorMaterial {;
//    LIGHT_COMPOSITE("light_composite", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
//        return Ingredient.ofItems(AstralItems.LIGHT_COMPOSITE_SHEET);
//    }),
//    HEAVY_COMPOSITE("heavy_composite", 45, new int[]{3, 6, 8, 3}, 4, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 1.0F, 0.0F, () -> {
//        return Ingredient.ofItems(Items.IRON_INGOT);
//    });

    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Lazy<Ingredient> repairIngredientSupplier;

    AstralMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredientSupplier) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = new Lazy<>(repairIngredientSupplier);
    }

    public int getDurability(ArmorItem.Type slot) {
        return BASE_DURABILITY[slot.ordinal()] * this.durabilityMultiplier;
    }

    public int getProtection(ArmorItem.Type slot) {
        return this.protectionAmounts[slot.ordinal()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredientSupplier.get();
    }

    @Environment(EnvType.CLIENT)
    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
