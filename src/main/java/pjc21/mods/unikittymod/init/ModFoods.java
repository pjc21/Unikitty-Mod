package pjc21.mods.unikittymod.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModFoods {

    public static final Food FISH_GOLDEN = (new Food.Builder()).nutrition(8).saturationMod(0.8F).build();
    public static final Food UNIKITTY_RAW = (new Food.Builder()).nutrition(4).saturationMod(0.2F).effect(() -> new EffectInstance(Effects.WEAKNESS, 600, 1), 0.5F).meat().build();
    public static final Food UNIKITTY_COOKED = (new Food.Builder()).nutrition(10).saturationMod(0.8F).meat().build();
}
