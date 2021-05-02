package pjc21.mods.unikittymod.proxy;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.eventbus.api.IEventBus;

public interface IProxy {
    void setup(IEventBus modEventBus, IEventBus forgeEventBus);

    default <A> A getUnikittyArmorModel(EquipmentSlotType armorSlot) {
        return null;
    }
}
