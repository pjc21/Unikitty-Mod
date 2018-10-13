package pjc21.mod.util.handlers;

import java.util.ArrayList;
import java.util.List;

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
	private static List<String> PlayersWithFlight = new ArrayList<String>();

	@SubscribeEvent
	public static void onFly(TickEvent.PlayerTickEvent event)
	{
		if(!event.player.isCreative() || !event.player.isSpectator()) 
		{
			String username = event.player.getName();
			if(event.player.isPotionActive(PotionInit.FLY_POTION))
			{
				boolean shouldFly = true;
				
				event.player.capabilities.allowFlying = shouldFly;
				event.player.sendPlayerAbilities();
				if (!PlayersWithFlight.contains(username)) PlayersWithFlight.add(username);
			}
			else if (PlayersWithFlight.contains(username)) 
			{
				PlayersWithFlight.remove(username);
				event.player.capabilities.allowFlying = false;
				event.player.capabilities.isFlying = false;
				event.player.sendPlayerAbilities();
			}
		}
	}

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
