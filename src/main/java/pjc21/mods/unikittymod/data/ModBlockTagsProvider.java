package pjc21.mods.unikittymod.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import pjc21.mods.unikittymod.UnikittyMod;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, UnikittyMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {}
}
