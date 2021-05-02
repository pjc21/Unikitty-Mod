package pjc21.mods.unikittymod.init;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.*;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.util.ModBrewingRecipe;

public class ModPotions extends Effect {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, UnikittyMod.MODID);
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, UnikittyMod.MODID);

    public static final RegistryObject<Effect> FLY_EFFECT = EFFECTS.register("fly_effect", () -> new ModPotions(EffectType.NEUTRAL, 0xc98fff));
    public static final RegistryObject<Potion> FLY = POTIONS.register("fly", () -> new Potion(new EffectInstance(FLY_EFFECT.get(), 6000)));

    protected ModPotions(EffectType effectType, int colorIn) {
        super(effectType, colorIn);
    }

    public static void addBrewingRecipe() {
        BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD), Ingredient.of(new ItemStack(ModItems.UNIKITTY_HORN.get())), PotionUtils.setPotion (new ItemStack(Items.POTION), ModPotions.FLY.get())));
    }

    public static void init(final IEventBus modEventBus) {
        POTIONS.register(modEventBus);
        EFFECTS.register(modEventBus);
    }
}
