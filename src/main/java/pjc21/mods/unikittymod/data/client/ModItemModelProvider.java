package pjc21.mods.unikittymod.data.client;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import pjc21.mods.unikittymod.UnikittyMod;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;
import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, UnikittyMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/handheld"));

        withExistingParent("unikitty_spawn_egg", mcLoc("item/template_spawn_egg"));

        Arrays.stream(DyeColor.values()).forEach(dyeColor -> builder(itemGenerated, name(new ResourceLocation(UnikittyMod.MODID, dyeColor+"_string")), modLoc("item/string")));

        builder(itemGenerated, "unikitty_horn");
        builder(itemGenerated, "blue_crystal");

        builder(itemGenerated, "fish_golden");
        builder(itemGenerated, "unikitty_raw");
        builder(itemGenerated, "unikitty_cooked");

        builder(itemGenerated, "unikitty_helmet");
        builder(itemGenerated, "unikitty_chestplate");
        builder(itemGenerated, "unikitty_leggings");
        builder(itemGenerated, "unikitty_boots");
    }

    private void builder(ModelFile itemGenerated, String name, ResourceLocation texture) {
        getBuilder(name).parent(itemGenerated).texture("layer0", texture);
    }

    private void builder(ModelFile itemGenerated, String name) {
        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

    private ResourceLocation registryName(final Item item) {
        return checkNotNull(item.getRegistryName(), "Item %s has a null registry name", item);
    }

    private String name(final ResourceLocation location) {
        Item item = ITEMS.getValue(location);
        return registryName(item).getPath();
    }
}
