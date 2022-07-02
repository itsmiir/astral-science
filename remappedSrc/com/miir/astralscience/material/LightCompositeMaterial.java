package com.miir.astralscience.material;

import com.miir.astralscience.item.AstralItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class LightCompositeMaterial implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
    private static final int[] PROTECTION_VALUES = new int[] {2, 6, 5, 2};
    private static final int DURABILITY_MODIFIER = 10;
    private static final int ENCHANTABILITY = 9;

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * DURABILITY_MODIFIER;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_VALUES[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return ENCHANTABILITY;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(AstralItems.LIGHT_COMPOSITE_SHEET);
    }

    @Override
    public String getName() {
        return "light_composite";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
