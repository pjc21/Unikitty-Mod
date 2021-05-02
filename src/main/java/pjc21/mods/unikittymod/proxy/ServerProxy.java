package pjc21.mods.unikittymod.proxy;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import pjc21.mods.unikittymod.UnikittyMod;

@Mod.EventBusSubscriber(modid = UnikittyMod.MODID, value = Dist.DEDICATED_SERVER)
public class ServerProxy implements IProxy{
    @Override
    public void setup(IEventBus modEventBus, IEventBus forgeEventBus) { }
}
