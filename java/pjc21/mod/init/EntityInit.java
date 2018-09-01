package pjc21.mod.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pjc21.mod.Main;
import pjc21.mod.entity.unikitty.EntityUniKitty;
import pjc21.mod.entity.unikitty.render.RenderUniKitty;
import pjc21.mod.util.Reference;

public class EntityInit 
{
	public static void registerEntities()
	{
		registerEntity("unikitty", EntityUniKitty.class, Reference.ENTITY_UNIKITTY, 64, 0xff26aa, 0xfff820);
		addSpawn();
	}

	private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id++, Main.instance, range, 3, true, color1, color2);
	}
	
	private static void addSpawn()
	{
		EntityRegistry.addSpawn(EntityUniKitty.class, 100, 3, 5, EnumCreatureType.CREATURE, Biomes.FOREST, Biomes.ROOFED_FOREST);
	}

    @SideOnly(Side.CLIENT)
    public static void registerEntityRenders() 
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityUniKitty.class, RenderUniKitty.FACTORY);
    }
}
