package pjc21.mods.unikittymod.items.armor.unikitty;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.config.PotionConfig;
import pjc21.mods.unikittymod.init.ModItems;

public class UnikittyArmorItem extends ArmorItem implements IDyeableArmorItem {
    public UnikittyArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties properties) {
        super(materialIn, slot, properties);
    }

    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return UnikittyMod.PROXY.getUnikittyArmorModel(armorSlot);
    }

    public int getColor(ItemStack p_200886_1_) {
        CompoundNBT compoundnbt = p_200886_1_.getTagElement("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 15872570;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
    {
        return stack.getItem() instanceof ArmorItem && (((ArmorItem) stack.getItem()).getMaterial() == UnikittyArmorMaterial.UNIKITTY);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ModItems.UNIKITTY_HELMET.get()) {
            PotionConfig.potionEffectsHelmet.stream().map(EffectInstance::new).forEach(player::addEffect);
        }

        if (this.isWearingFullSet(player)) {
            PotionConfig.potionEffectsFullSet.stream().map(EffectInstance::new).forEach(player::addEffect);
        }
    }

    private boolean isWearingFullSet (PlayerEntity player)
    {
        return player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ModItems.UNIKITTY_HELMET.get()
                && player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == ModItems.UNIKITTY_CHESTPLATE.get()
                && player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == ModItems.UNIKITTY_LEGGINGS.get()
                && player.getItemBySlot(EquipmentSlotType.FEET).getItem() == ModItems.UNIKITTY_BOOTS.get();
    }
}
