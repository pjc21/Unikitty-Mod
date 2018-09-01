package pjc21.mod.objects.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pjc21.mod.Main;
import pjc21.mod.init.ItemInit;
import pjc21.mod.init.PotionInit;
import pjc21.mod.objects.armor.model.ModelUniKittyArmor;
import pjc21.mod.util.interfaces.IHasModel;

public class UniKittyArmor extends ItemArmor implements IHasModel
{

	public UniKittyArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.UNKITTYTAB);
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) 
	{
		if(!itemStack.isEmpty()) 
		{
			if(itemStack.getItem() instanceof ItemArmor) 
			{
				
				ModelUniKittyArmor armorModel = new ModelUniKittyArmor(1.0f);
				ModelUniKittyArmor armorModelLegs = new ModelUniKittyArmor(0.5f);
				
				armorModel.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
				armorModel.bipedHeadwear.showModel = armorSlot == EntityEquipmentSlot.HEAD;
				armorModel.bipedBody.showModel = (armorSlot == EntityEquipmentSlot.CHEST) || (armorSlot == EntityEquipmentSlot.CHEST);
				armorModel.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				armorModel.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				armorModelLegs.bipedRightLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS) || (armorSlot == EntityEquipmentSlot.FEET);
				armorModelLegs.bipedLeftLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS) || (armorSlot == EntityEquipmentSlot.FEET);

				armorModel.isSneak = _default.isSneak;
				armorModel.isRiding = _default.isRiding;
				armorModel.isChild = _default.isChild;
				armorModel.rightArmPose = _default.rightArmPose;
				armorModel.leftArmPose = _default.leftArmPose;
				
				armorModelLegs.isSneak = _default.isSneak;
				armorModelLegs.isRiding = _default.isRiding;
				armorModelLegs.isChild = _default.isChild;
				armorModelLegs.rightArmPose = _default.rightArmPose;
				armorModelLegs.leftArmPose = _default.leftArmPose;

				return armorModel;
			}
		}
		
		return null;
	}
	
	@Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) 
	{
        if (player.inventory.armorInventory.get(3) != null && player.inventory.armorInventory.get(3).getItem() == ItemInit.HELMET_UNIKITTY)
        {
            this.effectPlayer(player, Potion.getPotionFromResourceLocation("night_vision"), 600, 2);
            this.effectPlayer(player, Potion.getPotionFromResourceLocation("water_breathing"), 100, 3);
        }
 
        if (this.isWearingFullSet(player, ItemInit.HELMET_UNIKITTY, ItemInit.CHESTPLATE_UNIKITTY, ItemInit.LEGGINGS_UNIKITTY, ItemInit.BOOTS_UNIKITTY)) 
        {
        	
            this.effectPlayer(player, Potion.getPotionFromResourceLocation("regeneration"), 100, 3);
            this.effectPlayer(player, Potion.getPotionFromResourceLocation("strength"), 100, 3);
            this.effectPlayer(player, Potion.getPotionFromResourceLocation("fire_resistance"), 100, 3);
            this.effectPlayer(player, Potion.getPotionFromResourceLocation("absorption"), 100, 3);
            this.effectPlayer(player, Potion.getPotionFromResourceLocation("resistance"), 100, 3);
            this.effectPlayer(player, Potion.getPotionFromResourceLocation("speed"), 100, 1);
            this.effectPlayer(player, Potion.getPotionFromResourceLocation("haste"), 100, 3);
            this.effectPlayer(player, PotionInit.FLY_POTION, 100, 0);
            
        }
	}
   
    private boolean isWearingFullSet (EntityPlayer player, Item helmet, Item chestpiece, Item leggins, Item boots) 
    {
        return
                  player.inventory.armorInventory.get(3) != null && player.inventory.armorInventory.get(3).getItem() == helmet
                && player.inventory.armorInventory.get(2) != null && player.inventory.armorInventory.get(2).getItem() == chestpiece
                && player.inventory.armorInventory.get(1) != null && player.inventory.armorInventory.get(1).getItem() == leggins
                && player.inventory.armorInventory.get(0) != null && player.inventory.armorInventory.get(0).getItem() == boots;
    }
   
    private void effectPlayer(EntityPlayer player, Potion potion, int duration, int amplifier) 
    {
    	if (player.getActivePotionEffect(potion) == null || Potion.getIdFromPotion(potion) == 16 && player.getActivePotionEffect(potion).getDuration() <= 200)
    	{
    		player.addPotionEffect(new PotionEffect(potion, duration, amplifier, true, false));
    	}
    	
        if (player.getActivePotionEffect(potion) == null || player.getActivePotionEffect(potion).getDuration() <= 1) 
        {
            player.addPotionEffect(new PotionEffect(potion, duration, amplifier, true, false));
        }
    }
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
