package pjc21.mod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import pjc21.mod.init.ItemInit;

public class UniKittyTab extends CreativeTabs
{
	public UniKittyTab(String label) { super("unikittytab");
	this.setBackgroundImageName("unikittytab.png");}
	public ItemStack getTabIconItem() { return new ItemStack(ItemInit.FISH_GOLDEN);}
}
