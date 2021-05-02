package pjc21.mods.unikittymod;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pjc21.mods.unikittymod.config.PotionConfig;
import pjc21.mods.unikittymod.config.UnikittyConfig;
import pjc21.mods.unikittymod.init.ModEntities;
import pjc21.mods.unikittymod.init.ModItems;
import pjc21.mods.unikittymod.init.ModPotions;
import pjc21.mods.unikittymod.init.ModSounds;
import pjc21.mods.unikittymod.proxy.ClientProxy;
import pjc21.mods.unikittymod.proxy.IProxy;
import pjc21.mods.unikittymod.proxy.ServerProxy;

import javax.annotation.Nonnull;

@Mod(UnikittyMod.MODID)
public class UnikittyMod {

    public static final String MODID = "unikittymod";

    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public UnikittyMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus(), forgeEventBus = MinecraftForge.EVENT_BUS;
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::setup);
        forgeEventBus.addListener(this::onBiomeLoad);
        PROXY.setup(modEventBus, forgeEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        ModPotions.init(modEventBus);
        ModItems.init(modEventBus);
        ModEntities.init(modEventBus);
        UnikittyConfig.setup();
    }

    public static final ItemGroup ITEM_GROUP = new ItemGroup(UnikittyMod.MODID) {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.UNIKITTY_HORN.get());
        }
    };

    private void setup(FMLCommonSetupEvent event) {
        PotionConfig.init();
        event.enqueueWork(ModPotions::addBrewingRecipe);
    }

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        ModEntities.RegistrationHandler.spawnEntities(event);
    }
}
