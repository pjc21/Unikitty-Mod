package pjc21.mod.init;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import pjc21.mod.objects.items.potion.PotionFly;

public class PotionInit 
{
	//Potions
	public static final Potion FLY_POTION = new PotionFly();
	
	//PotionTypes
	public static final PotionType FLY = new PotionType("fly", new PotionEffect[] {new PotionEffect(FLY_POTION, 2400)}).setRegistryName("fly");
	public static final PotionType LONG_FLY = new PotionType("fly", new PotionEffect[] {new PotionEffect(FLY_POTION, 9600)}).setRegistryName("long_fly");

	public static void registerPotions()
	{
		ForgeRegistries.POTIONS.register(FLY_POTION);
		ForgeRegistries.POTION_TYPES.register(FLY);
		ForgeRegistries.POTION_TYPES.register(LONG_FLY);
		PotionHelper.addMix(PotionTypes.INVISIBILITY, Items.ENDER_EYE, FLY);
		PotionHelper.addMix(FLY, Items.REDSTONE, LONG_FLY);
	}

}
