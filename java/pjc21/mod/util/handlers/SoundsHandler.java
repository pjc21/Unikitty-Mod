package pjc21.mod.util.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import pjc21.mod.util.Reference;

public class SoundsHandler 
{
	public static SoundEvent ENTITY_UNIKITTY_HAIRBALL;
	
	public static void registerSounds()
	{
		ENTITY_UNIKITTY_HAIRBALL = registerSound("entity.unikitty.hairball");
	}
	
	private static SoundEvent registerSound(String name)
	{
		ResourceLocation location = new ResourceLocation(Reference.MODID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}
