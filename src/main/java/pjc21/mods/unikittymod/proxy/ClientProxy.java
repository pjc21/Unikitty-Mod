package pjc21.mods.unikittymod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.entities.unikitty.render.UnikittyRenderer;
import pjc21.mods.unikittymod.init.ModEntities;
import pjc21.mods.unikittymod.items.armor.unikitty.UnikittyArmorLayer;
import pjc21.mods.unikittymod.items.armor.unikitty.UnikittyArmorModel;

import java.util.List;

@Mod.EventBusSubscriber(modid = UnikittyMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientProxy implements IProxy{

    private final UnikittyArmorModel unikittyArmorModel = new UnikittyArmorModel(1.0f);
    private final UnikittyArmorModel unikittyArmorLeggings = new UnikittyArmorModel(0.5f);

    @Override
    public void setup(IEventBus modEventBus, IEventBus forgeEventBus) {
        modEventBus.addListener(ClientProxy::init);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> A getUnikittyArmorModel(EquipmentSlotType armorSlot) {
        return (A) (armorSlot == EquipmentSlotType.LEGS ? unikittyArmorLeggings : unikittyArmorModel);
    }

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.UNIKITTYENTITY.get(), UnikittyRenderer::new);
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            EntityRendererManager entityRenderManager = Minecraft.getInstance().getEntityRenderDispatcher();
            entityRenderManager.getSkinMap().forEach((key, renderer) -> addUnikittyArmorLayer(renderer));
            entityRenderManager.renderers.forEach((key, renderer) -> {
                if (renderer instanceof LivingRenderer) {
                    addUnikittyArmorLayer((LivingRenderer) renderer);
                }
            });
        });
    }

    private static <T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> void addUnikittyArmorLayer(LivingRenderer<T, M> renderer) {

        final List<LayerRenderer<T, M>> layers = ObfuscationReflectionHelper.getPrivateValue(LivingRenderer.class, renderer,"field_177097_h");
        LayerRenderer<T, M> matchingLayer = layers.stream().filter(layerRenderer -> layerRenderer != null && layerRenderer.getClass() == BipedArmorLayer.class).findFirst().orElse(null);
        if (matchingLayer != null) {
            BipedArmorLayer<T, M, A> bipedArmorLayer = (BipedArmorLayer<T, M, A>) matchingLayer;
            final A innerModel = ObfuscationReflectionHelper.getPrivateValue(BipedArmorLayer.class, bipedArmorLayer,"field_177189_c");
            final A outerModel = ObfuscationReflectionHelper.getPrivateValue(BipedArmorLayer.class, bipedArmorLayer,"field_177186_d");
            renderer.addLayer(new UnikittyArmorLayer<>(renderer, innerModel, outerModel));
        }
    }
}
