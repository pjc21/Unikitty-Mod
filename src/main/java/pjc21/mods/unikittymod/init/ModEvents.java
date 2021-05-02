package pjc21.mods.unikittymod.init;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import pjc21.mods.unikittymod.UnikittyMod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = UnikittyMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    private static final List<String> PlayersWithFlight = new ArrayList<>();

    @SubscribeEvent
    public static void onFly(TickEvent.PlayerTickEvent event) {
        if (event.player.isCreative() || event.player.isSpectator()) {
            return;
        }
        String userID = event.player.getStringUUID();

        if (event.player.hasEffect(ModPotions.FLY_EFFECT.get())) {
            event.player.abilities.mayfly = true;
            event.player.onUpdateAbilities();
            if (!PlayersWithFlight.contains(userID)) PlayersWithFlight.add(userID);
        } else if (PlayersWithFlight.contains(userID)) {
            event.player.abilities.mayfly = false;
            event.player.abilities.flying = false;
            event.player.onUpdateAbilities();
            PlayersWithFlight.remove(userID);
        }
    }
}
