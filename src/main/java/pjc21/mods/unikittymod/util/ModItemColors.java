package pjc21.mods.unikittymod.util;

import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.DyeColor;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.init.ModItems;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = UnikittyMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItemColors {

    @SubscribeEvent
    public static void init(ColorHandlerEvent.Item event){
        ItemColors itemColors = event.getItemColors();
        Arrays.stream(DyeColor.values()).forEach(dyeColor1 -> itemColors.register((stack, tintIndex) -> tintIndex == 0 ? dyeColor1.getColorValue() : DyeColor.WHITE.getColorValue(), ModItems.COLORED_STRING.get(dyeColor1).get()));
        itemColors.register((stack, color) -> color > 0 ? -1 : ((IDyeableArmorItem) stack.getItem()).getColor(stack), ModItems.UNIKITTY_HELMET.get(), ModItems.UNIKITTY_CHESTPLATE.get(), ModItems.UNIKITTY_LEGGINGS.get(), ModItems.UNIKITTY_BOOTS.get());
    }
}
