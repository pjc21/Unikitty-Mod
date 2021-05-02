package pjc21.mods.unikittymod.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.init.ModEntities;
import pjc21.mods.unikittymod.init.ModTags;

import javax.annotation.Nullable;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public ModEntityTypeTagsProvider(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, UnikittyMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.EntityTypes.UNIKITTYENTITY).add(ModEntities.UNIKITTYENTITY.get());
    }
}
