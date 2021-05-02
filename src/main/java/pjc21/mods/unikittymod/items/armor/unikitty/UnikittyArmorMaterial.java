package pjc21.mods.unikittymod.items.armor.unikitty;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Lazy;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.config.UnikittyConfig;
import pjc21.mods.unikittymod.init.ModItems;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum UnikittyArmorMaterial implements IArmorMaterial {
    UNIKITTY("unikitty", UnikittyConfig.armorSettings.armorDurabilityMultiplier.get(),
            new int[] {UnikittyConfig.armorSettings.armorDurability.get().get(0), UnikittyConfig.armorSettings.armorDurability.get().get(1), UnikittyConfig.armorSettings.armorDurability.get().get(2), UnikittyConfig.armorSettings.armorDurability.get().get(3)},
            new int[] {UnikittyConfig.armorSettings.armorDamageReduction.get().get(0), UnikittyConfig.armorSettings.armorDamageReduction.get().get(1), UnikittyConfig.armorSettings.armorDamageReduction.get().get(2), UnikittyConfig.armorSettings.armorDamageReduction.get().get(3)},
            UnikittyConfig.armorSettings.armorEnchantability.get(), SoundEvents.ARMOR_EQUIP_GENERIC, UnikittyConfig.armorSettings.armorToughness.get().floatValue(), UnikittyConfig.armorSettings.armorknockbackResistance.get().floatValue(),
            () -> Ingredient.of(ModItems.BLUE_CRYSTAL.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] durabilityArray;
    private final int[] damageReductionArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final Lazy<Ingredient> repairMaterialLazy;

    UnikittyArmorMaterial(String nameIn, int durabilityMultiplierIn, int[] durabilityArrayIn, int[] damageReductionArrayIn, int enchantabilityIn, SoundEvent soundEventIn, float toughnessIn, float knockbackResistanceIn, Supplier<Ingredient> repairMaterialSupplier) {
        this.name = UnikittyMod.MODID + ":" + nameIn;
        this.durabilityMultiplier = durabilityMultiplierIn;
        this.durabilityArray = durabilityArrayIn;
        this.damageReductionArray = damageReductionArrayIn;
        this.enchantability = enchantabilityIn;
        this.soundEvent = soundEventIn;
        this.toughness = toughnessIn;
        this.knockbackResistance = knockbackResistanceIn;
        this.repairMaterialLazy = Lazy.concurrentOf(repairMaterialSupplier);
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlotType slotIn) {
        return durabilityArray[slotIn.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slotIn) {
        return this.damageReductionArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Nonnull
    @Override
    public SoundEvent getEquipSound() {
        return this.soundEvent;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterialLazy.get();
    }

    @Nonnull
    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
