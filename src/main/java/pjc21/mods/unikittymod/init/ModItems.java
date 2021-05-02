package pjc21.mods.unikittymod.init;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.items.armor.unikitty.UnikittyArmorItem;
import pjc21.mods.unikittymod.items.armor.unikitty.UnikittyArmorMaterial;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModItems {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UnikittyMod.MODID);

    public static final Map<DyeColor, RegistryObject<Item>> COLORED_STRING = getDyeColorMap(ModItems::registerStringColors);

    public static final RegistryObject<Item> UNIKITTY_HORN = ITEMS.register("unikitty_horn", () -> new Item(new Item.Properties().tab(UnikittyMod.ITEM_GROUP)));
    public static final RegistryObject<Item> BLUE_CRYSTAL = ITEMS.register("blue_crystal", () -> new Item(new Item.Properties().tab(UnikittyMod.ITEM_GROUP)));

    public static final RegistryObject<Item> FISH_GOLDEN = ITEMS.register("fish_golden", () -> new Item((new Item.Properties()).tab(UnikittyMod.ITEM_GROUP).food(ModFoods.FISH_GOLDEN)));
    public static final RegistryObject<Item> UNIKITTY_RAW = ITEMS.register("unikitty_raw", () -> new Item((new Item.Properties()).tab(UnikittyMod.ITEM_GROUP).food(ModFoods.UNIKITTY_RAW)));
    public static final RegistryObject<Item> UNIKITTY_COOKED = ITEMS.register("unikitty_cooked", () -> new Item((new Item.Properties()).tab(UnikittyMod.ITEM_GROUP).food(ModFoods.UNIKITTY_COOKED)));

    public static final RegistryObject<ArmorItem> UNIKITTY_HELMET = ITEMS.register("unikitty_helmet", () -> new UnikittyArmorItem(UnikittyArmorMaterial.UNIKITTY, EquipmentSlotType.HEAD, new Item.Properties().tab(UnikittyMod.ITEM_GROUP)));
    public static final RegistryObject<ArmorItem> UNIKITTY_CHESTPLATE = ITEMS.register("unikitty_chestplate", () -> new UnikittyArmorItem(UnikittyArmorMaterial.UNIKITTY, EquipmentSlotType.CHEST, new Item.Properties().tab(UnikittyMod.ITEM_GROUP)));
    public static final RegistryObject<ArmorItem> UNIKITTY_LEGGINGS = ITEMS.register("unikitty_leggings", () -> new UnikittyArmorItem(UnikittyArmorMaterial.UNIKITTY, EquipmentSlotType.LEGS, new Item.Properties().tab(UnikittyMod.ITEM_GROUP)));
    public static final RegistryObject<ArmorItem> UNIKITTY_BOOTS = ITEMS.register("unikitty_boots", () -> new UnikittyArmorItem(UnikittyArmorMaterial.UNIKITTY, EquipmentSlotType.FEET, new Item.Properties().tab(UnikittyMod.ITEM_GROUP)));

    private static Map<DyeColor, RegistryObject<Item>> getDyeColorMap(Function<DyeColor, RegistryObject<Item>> colorRegistryFunction) {
        return Arrays.stream(DyeColor.values()).collect(Collectors.toMap(dyeColor -> dyeColor, colorRegistryFunction));
    }

    private static RegistryObject<Item> registerStringColors(DyeColor dyeColorIn){
        String coloredStringId = String.format("%s_string", dyeColorIn.toString());
        return ITEMS.register(coloredStringId,  () -> new Item(new Item.Properties().tab(UnikittyMod.ITEM_GROUP)));
    }

    public static void init(final IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
