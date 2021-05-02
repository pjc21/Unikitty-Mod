package pjc21.mods.unikittymod.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pjc21.mods.unikittymod.UnikittyMod;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UnikittyMod.MODID);
    public static final RegistryObject<SoundEvent> UNIKITTY_HAIRBALL = registerSound("unikitty.hairball");

    private static RegistryObject<SoundEvent> registerSound(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(UnikittyMod.MODID, name)));
    }
}
