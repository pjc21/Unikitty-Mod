package pjc21.mod.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipesInit 
{
	public static void registerRecipes()
	{
		GameRegistry.addSmelting(ItemInit.UNIKITTY_RAW, new ItemStack(ItemInit.UNIKITTY_COOKED, 1), 1.5F);
	}
}
