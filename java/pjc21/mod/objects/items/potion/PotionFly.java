package pjc21.mod.objects.items.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import pjc21.mod.util.Reference;

public class PotionFly extends Potion
{

	public PotionFly() {
		super(false, 8376831);
		setPotionName("effect.fly");
		setIconIndex(0, 0);
		setRegistryName(new ResourceLocation(Reference.MODID + ":" + "fly"));
	}

	@Override
	public boolean hasStatusIcon() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/container/custom_effects.png"));
		return true;
	} 
	
}
