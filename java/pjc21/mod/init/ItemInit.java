package pjc21.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import pjc21.mod.objects.armor.UniKittyArmor;
import pjc21.mod.objects.items.ItemBase;
import pjc21.mod.objects.items.food.ItemCustomFood;
import pjc21.mod.util.Reference;

public class ItemInit 
{
	public static final List<Item> ITEMS = new ArrayList<Item>();

	//Material
	public static final ArmorMaterial ARMOR_UNIKITTY = EnumHelper.addArmorMaterial("armor_unikitty", Reference.MODID + ":unikitty", 0, new int[]{6, 8, 8, 6}, 50, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);

	//Items
	public static final Item STRING_BLACK = new ItemBase("string_black");
	public static final Item STRING_BLUE = new ItemBase("string_blue");
	public static final Item STRING_BROWN = new ItemBase("string_brown");
	public static final Item STRING_CYAN = new ItemBase("string_cyan");
	public static final Item STRING_GRAY = new ItemBase("string_gray");
	public static final Item STRING_GREEN = new ItemBase("string_green");
	public static final Item STRING_LIGHT_BLUE = new ItemBase("string_light_blue");
	public static final Item STRING_LIGHT_GRAY = new ItemBase("string_light_gray");
	public static final Item STRING_LIME = new ItemBase("string_lime");
	public static final Item STRING_MAGENTA = new ItemBase("string_magenta");
	public static final Item STRING_ORANGE = new ItemBase("string_orange");
	public static final Item STRING_PINK = new ItemBase("string_pink");
	public static final Item STRING_PURPLE = new ItemBase("string_purple");
	public static final Item STRING_RED = new ItemBase("string_red");
	public static final Item STRING_YELLOW = new ItemBase("string_yellow");
	
	public static final Item UNIKITTY_HORN = new ItemBase("unikitty_horn");
	public static final Item BLUE_CRYSTAL = new ItemBase("blue_crystal");
		
	//Foods
	public static final Item FISH_GOLDEN = new ItemCustomFood("fish_golden", 8, false);
	public static final Item UNIKITTY_RAW = new ItemCustomFood("unikitty_raw", 6, true);
	public static final Item UNIKITTY_COOKED = new ItemCustomFood("unikitty_cooked", 10, true);
	
	//Armor
	public static final Item HELMET_UNIKITTY = new UniKittyArmor("helmet_unikitty", ARMOR_UNIKITTY, 1, EntityEquipmentSlot.HEAD);
	public static final Item CHESTPLATE_UNIKITTY = new UniKittyArmor("chestplate_unikitty", ARMOR_UNIKITTY, 1, EntityEquipmentSlot.CHEST);
	public static final Item LEGGINGS_UNIKITTY = new UniKittyArmor("leggings_unikitty", ARMOR_UNIKITTY, 2, EntityEquipmentSlot.LEGS);
	public static final Item BOOTS_UNIKITTY = new UniKittyArmor("boots_unikitty", ARMOR_UNIKITTY, 1, EntityEquipmentSlot.FEET);
	
}
