package pjc21.mods.unikittymod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipe;

import javax.annotation.Nonnull;

public class ModBrewingRecipe extends BrewingRecipe {

    private final ItemStack stackIn;

    public ModBrewingRecipe(ItemStack  stackIn, Ingredient ingredient, ItemStack stackOut) {
        super(Ingredient.of(stackIn), ingredient, stackOut);
        this.stackIn = stackIn;
    }

    @Override
    public boolean isInput(@Nonnull ItemStack input) {
        return super.isInput(input) && PotionUtils.getPotion(input) == PotionUtils.getPotion(stackIn);
    }
}
