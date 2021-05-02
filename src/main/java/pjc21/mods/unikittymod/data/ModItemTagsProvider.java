package pjc21.mods.unikittymod.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.init.ModItems;
import pjc21.mods.unikittymod.init.ModTags;

import javax.annotation.Nullable;
import java.util.Arrays;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, UnikittyMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(Tags.Items.GEMS).add(ModItems.BLUE_CRYSTAL.get());
        tag(ModTags.Items.UNIKITTY_HORN).add(ModItems.UNIKITTY_HORN.get());

        tag(ItemTags.FISHES).add(ModItems.FISH_GOLDEN.get());
        tag(ModTags.Items.UNIKITTY_RAW).add(ModItems.UNIKITTY_RAW.get());
        tag(ModTags.Items.UNIKITTY_COOKED).add(ModItems.UNIKITTY_COOKED.get());

        tag(ModTags.Items.UNIKITTY_HELMET).add(ModItems.UNIKITTY_HELMET.get());
        tag(ModTags.Items.UNIKITTY_CHESTPLATE).add(ModItems.UNIKITTY_CHESTPLATE.get());
        tag(ModTags.Items.UNIKITTY_LEGGINGS).add(ModItems.UNIKITTY_LEGGINGS.get());
        tag(ModTags.Items.UNIKITTY_BOOTS).add(ModItems.UNIKITTY_BOOTS.get());

        Arrays.stream(DyeColor.values()).forEach(color -> {
            ITag.INamedTag<Item> colorTag = ModTags.Items.getColorTag(color);
            tag(Tags.Items.STRING).add(ModItems.COLORED_STRING.get(color).get());
            tag(colorTag).add(ModItems.COLORED_STRING.get(color).get());
        });
    }
}
