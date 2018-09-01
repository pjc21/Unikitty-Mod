package pjc21.mod.util.handlers;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pjc21.mod.init.EntityInit;
import pjc21.mod.init.ItemInit;
import pjc21.mod.init.PotionInit;
import pjc21.mod.init.RecipesInit;
import pjc21.mod.util.interfaces.IHasModel;

@EventBusSubscriber
public class RegistryHandler 
{
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}
	
		
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ItemInit.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		
		EntityInit.registerEntityRenders();
	}
	
	@SubscribeEvent
	public static void onFly(TickEvent.PlayerTickEvent event) 
	{
		boolean fly = false;
		
		if(event.player.isPotionActive(PotionInit.FLY_POTION))
			fly = true;
		if(fly || event.player.isCreative() || event.player.isSpectator()) 
		{
			event.player.capabilities.allowFlying = true;
			event.player.fallDistance = 0.0f;
		} else {
			fly = false;
			event.player.capabilities.isFlying = false;
			event.player.capabilities.allowFlying = false;
		}
	}
	
	public static void preInitRegistries()
	{
		EntityInit.registerEntities();
		PotionInit.registerPotions();
	}
	
	public static void initRegistries()
	{
		RecipesInit.registerRecipes();
		SoundsHandler.registerSounds();
	}
}
