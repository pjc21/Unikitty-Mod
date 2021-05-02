package pjc21.mods.unikittymod.config;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.init.ModPotions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PotionConfig {
    public static List<EffectInstance> potionEffectsHelmet = new ArrayList<>();
    public static List<EffectInstance> potionEffectsFullSet = new ArrayList<>();

    public static void init() {
        setDefaultPotionEffectsHelmet();
        setDefaultPotionEffectsFullSet();
        loadConfigPotionEffectsHelmet();
        loadConfigPotionEffectsFullSet();
    }

    private static void setDefaultPotionEffectsHelmet() {
        if (UnikittyConfig.armorSettings.potionEffectsHelmet.get().isEmpty()) UnikittyConfig.armorSettings.potionEffectsHelmet.set(getDefaultPotionEffectsHelmet());
    }

    private static void setDefaultPotionEffectsFullSet() {
        if (UnikittyConfig.armorSettings.potionEffectsFullSet.get().isEmpty()) UnikittyConfig.armorSettings.potionEffectsFullSet.set(getDefaultPotionEffectsFullSet());
    }

    private static void loadConfigPotionEffectsHelmet() {
        potionEffectsHelmet.clear();

        Pattern validEntry = Pattern.compile("[\\w:]+[\\d\\w|]+");
        UnikittyConfig.armorSettings.potionEffectsHelmet.get().forEach(entry -> {
            if (validEntry.matcher(entry).matches()) {
                String[] entryParts = entry.split("\\|");

                String name = entryParts[0];
                String dur = entryParts[1];
                String amp = entryParts[2];
                String part = entryParts[3];
                String icon = entryParts[4];

                addPotionEffectsHelmet(name, dur, amp, part, icon);
            } else {
                UnikittyMod.LOGGER.error("Potion Entry: " + entry + " is not valid.\n"
                        + "Format: potion_effect_name|duration|amplifier|show particles|show icon\n");
            }
        });
    }

    private static void loadConfigPotionEffectsFullSet() {
        potionEffectsFullSet.clear();

        Pattern validEntry = Pattern.compile("[\\w:]+[\\d\\w|]+");
        UnikittyConfig.armorSettings.potionEffectsFullSet.get().forEach(entry -> {
            if (validEntry.matcher(entry).matches()) {
                String[] entryParts = entry.split("\\|");

                String name = entryParts[0];
                String dur = entryParts[1];
                String amp = entryParts[2];
                String part = entryParts[3];
                String icon = entryParts[4];

                addPotionEffectsFullSet(name, dur, amp, part, icon);
            } else {
                UnikittyMod.LOGGER.error("Potion Entry: " + entry + " is not valid.\n"
                        + "Format: potion_effect_name|duration|amplifier|show particles|show icon\n");
            }
        });
    }

    private static void addPotionEffectsHelmet(String name, String dur, String amp, String part, String icon) {
        if (!name.isEmpty()) {
            Effect potionName = ForgeRegistries.POTIONS.getValue(new ResourceLocation(name));
            int duration = Integer.parseInt(dur);
            int amplifier = Integer.parseInt(amp);
            boolean showParticles = Boolean.parseBoolean(part);
            boolean showIcon = Boolean.parseBoolean(icon);
            EffectInstance effectInstance = new EffectInstance(potionName, duration, amplifier, false, showParticles, showIcon);

            potionEffectsHelmet.add(effectInstance);
        }
    }

    private static void addPotionEffectsFullSet(String name, String dur, String amp, String part, String icon) {
        if (!name.isEmpty()) {
            Effect potionName = ForgeRegistries.POTIONS.getValue(new ResourceLocation(name));
            int duration = Integer.parseInt(dur);
            int amplifier = Integer.parseInt(amp);
            boolean showParticles = Boolean.parseBoolean(part);
            boolean showIcon = Boolean.parseBoolean(icon);
            EffectInstance effectInstance = new EffectInstance(potionName, duration, amplifier, false, showParticles, showIcon);

            potionEffectsFullSet.add(effectInstance);
        }
    }

    public static List<String> getDefaultPotionEffectsHelmet() {
        List<String> defaultEffects = new ArrayList<>();

        defaultEffects.add(Effects.WATER_BREATHING.getRegistryName().toString()+"|100|3|false|false");
        defaultEffects.add(Effects.NIGHT_VISION.getRegistryName().toString()+"|400|3|false|false");

        return defaultEffects;
    }

    public static List<String> getDefaultPotionEffectsFullSet() {
        List<String> defaultEffects = new ArrayList<>();

        defaultEffects.add(Effects.REGENERATION.getRegistryName().toString()+"|100|3|false|false");
        defaultEffects.add(Effects.DAMAGE_BOOST.getRegistryName().toString()+"|100|3|false|false");
        defaultEffects.add(Effects.DAMAGE_RESISTANCE.getRegistryName().toString()+"|100|3|false|false");
        defaultEffects.add(Effects.ABSORPTION.getRegistryName().toString()+"|100|4|false|false");
        defaultEffects.add(Effects.FIRE_RESISTANCE.getRegistryName().toString()+"|100|4|false|false");
        defaultEffects.add(Effects.MOVEMENT_SPEED.getRegistryName().toString()+"|100|1|false|false");
        defaultEffects.add(Effects.DIG_SPEED.getRegistryName().toString()+"|100|3|false|false");
        defaultEffects.add(ModPotions.FLY_EFFECT.get().getRegistryName().toString()+"|100|3|false|false");

        return defaultEffects;
    }
}
