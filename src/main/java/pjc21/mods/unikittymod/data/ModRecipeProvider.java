package pjc21.mods.unikittymod.data;

import net.minecraft.block.Block;
import net.minecraft.data.*;
import net.minecraft.item.DyeColor;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.init.ModItems;

import java.util.Arrays;
import java.util.function.Consumer;

import static net.minecraft.item.Items.COD;
import static net.minecraft.item.Items.GOLD_NUGGET;
import static net.minecraftforge.registries.ForgeRegistries.BLOCKS;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(UnikittyMod.MODID, path);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        Arrays.stream(DyeColor.values()).forEach(dyeColor -> {
            Block block = BLOCKS.getValue(new ResourceLocation("minecraft", dyeColor + "_wool")).getBlock();
            ShapedRecipeBuilder.shaped(block)
                    .define('s', ModItems.COLORED_STRING.get(dyeColor).get())
                    .pattern("ss ")
                    .pattern("ss ")
                    .group(UnikittyMod.MODID)
                    .unlockedBy("has_item", has(ModItems.COLORED_STRING.get(dyeColor).get()))
                    .save(consumer);
        });

        ShapedRecipeBuilder.shaped(ModItems.FISH_GOLDEN.get())
                .define('f', COD)
                .define('n', GOLD_NUGGET)
                .pattern("nnn")
                .pattern("nfn")
                .pattern("nnn")
                .group(UnikittyMod.MODID)
                .unlockedBy("has_cod", has(COD))
                .unlockedBy("has_gold_nugget", has(GOLD_NUGGET))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.UNIKITTY_HELMET.get())
                .define('h', ModItems.UNIKITTY_HORN.get())
                .define('c', ModItems.BLUE_CRYSTAL.get())
                .pattern("chc")
                .pattern("c c")
                .group(UnikittyMod.MODID)
                .unlockedBy("has_horn", has(ModItems.UNIKITTY_HORN.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.UNIKITTY_CHESTPLATE.get())
                .define('c', ModItems.BLUE_CRYSTAL.get())
                .pattern("c c")
                .pattern("ccc")
                .pattern("ccc")
                .group(UnikittyMod.MODID)
                .unlockedBy("has_blue_crystal", has(ModItems.BLUE_CRYSTAL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.UNIKITTY_LEGGINGS.get())
                .define('c', ModItems.BLUE_CRYSTAL.get())
                .pattern("ccc")
                .pattern("c c")
                .pattern("c c")
                .group(UnikittyMod.MODID)
                .unlockedBy("has_blue_crystal", has(ModItems.BLUE_CRYSTAL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.UNIKITTY_BOOTS.get())
                .define('c', ModItems.BLUE_CRYSTAL.get())
                .pattern("c c")
                .pattern("c c")
                .group(UnikittyMod.MODID)
                .unlockedBy("has_blue_crystal", has(ModItems.BLUE_CRYSTAL.get()))
                .save(consumer);


        CookingRecipeBuilder.cooking(Ingredient.of(ModItems.UNIKITTY_RAW.get()), ModItems.UNIKITTY_COOKED.get(), 0.35F, 200, IRecipeSerializer.SMOKING_RECIPE)
                .unlockedBy("has_unikitty_raw", has(ModItems.UNIKITTY_RAW.get()))
                .save(consumer, modId("cooked_unikitty_from_smoking"));

        CookingRecipeBuilder.cooking(Ingredient.of(ModItems.UNIKITTY_RAW.get()), ModItems.UNIKITTY_COOKED.get(), 0.35F, 200, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy("has_unikitty_raw", has(ModItems.UNIKITTY_RAW.get()))
                .save(consumer, modId("cooked_unikitty_from_campfire_cooking"));

        CookingRecipeBuilder.smelting(Ingredient.of(ModItems.UNIKITTY_RAW.get()), ModItems.UNIKITTY_COOKED.get(), 0.35f, 200)
                .unlockedBy("has_unikitty_raw", has(ModItems.UNIKITTY_RAW.get()))
                .save(consumer, modId("cooked_unikitty_from_smelting"));
    }
}
