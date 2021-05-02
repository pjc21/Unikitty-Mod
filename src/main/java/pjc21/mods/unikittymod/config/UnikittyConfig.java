package pjc21.mods.unikittymod.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import pjc21.mods.unikittymod.UnikittyMod;

import java.util.*;

@Mod.EventBusSubscriber(modid = UnikittyMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UnikittyConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ArmorSettings armorSettings = new ArmorSettings(BUILDER);
    public static final EntitySettings entitySettings = new EntitySettings(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();

    public static class ArmorSettings {
        public final ForgeConfigSpec.ConfigValue<List<Integer>> armorDurability;
        public final ForgeConfigSpec.ConfigValue<List<Integer>> armorDamageReduction;
        public final ForgeConfigSpec.IntValue armorDurabilityMultiplier;
        public final ForgeConfigSpec.IntValue armorEnchantability;
        public final ForgeConfigSpec.DoubleValue armorToughness;
        public final ForgeConfigSpec.DoubleValue armorknockbackResistance;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> armorNames;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> potionEffectsHelmet;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> potionEffectsFullSet;

        ArmorSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("Armor settings").push("armor");

            builder.comment("Armor Durability").push("armorDurability");
            builder.comment("The Base Durability for each Armor piece.\nAll Vanilla Armors by default are 13, 15, 16, 11 & then multiplied by the Durability Multiplier.\nFormat: boots, leggings, chest, helmet");
            armorDurability = builder.define("armorDurability", getDefaultDurability());
            builder.pop(1);

            builder.comment("Armor Durability Multiplier").push("armorDurabilityMultiplier");
            builder.comment("The Durability Multiplier for Armor.\nThe Armor Durability is multiplied by this number.\nNetherite by default is 37.\nSetting to 0 = unbreakable");
            armorDurabilityMultiplier = builder.defineInRange("armorDurabilityMultiplier", 100, 0, 1000);
            builder.pop(1);

            builder.comment("Armor Damage Reduction").push("armorDamageReduction");
            builder.comment("The Damage Reduction for each Armor piece.\nDiamond/Netherite by default are 3, 6, 8, 3\nFormat: boots, leggings, chest, helmet");
            armorDamageReduction = builder.define("armorDamageReduction", getDefaultDamageReduction());
            builder.pop(1);

            builder.comment("Armor Toughness").push("armorToughness");
            builder.comment("The Armor Toughness.\nNetherite by default is 3.0F");
            armorToughness = builder.defineInRange("armorToughness", 5.0F, 0.0F, 100.0F);
            builder.pop(1);

            builder.comment("Armor knockback Resistance").push("armorknockbackResistance");
            builder.comment("The Armor knockback Resistance.\nNetherite by default is 0.1");
            armorknockbackResistance = builder.defineInRange("armorknockbackResistance", 0.3F, 0.0F, 1.0F);
            builder.pop(1);

            builder.comment("Armor Enchantability").push("armorEnchantability");
            builder.comment("The Enchantability amount of the Armor.\nNetherite by default is 15");
            armorEnchantability = builder.defineInRange("armorEnchantability", 50, 0, 500);
            builder.pop(1);

            builder.comment("Armor Names").push("armorNames");
            builder.comment("A list of names that will make Unikitty Armor change color like when a sheep is named jeb_");
            armorNames = builder.defineList("armorNames", Arrays.asList("Kaena", "Casey", "Emma", "Kaitlyn"), lt -> lt instanceof String);
            builder.pop(1);

            builder.comment("Potions Added to Helmet").push("potionsAddedHelmet");
            builder.comment("Potions added to the Helmet.\nFormat: potion_effect_name|amplifier|showParticles|showIcon, Example: [\"minecraft:regeneration|100|3|false|true\"]");
            potionEffectsHelmet = builder.defineList("potionsAddedHelmet", new ArrayList<>(), lt -> lt instanceof String);
            builder.pop(1);

            builder.comment("Potions Added Full Set").push("potionEffectsFullSet");
            builder.comment("Potions added when wearing full set.\nFormat: potion_effect_name|amplifier|showParticles|showIcon, Example: [\"minecraft:regeneration|100|3|false|true\"]");
            potionEffectsFullSet = builder.defineList("potionEffectsFullSet", new ArrayList<>(), lt -> lt instanceof String);
            builder.pop(1);

            builder.pop();
        }
    }

    public static class EntitySettings {
        EntitySettings(ForgeConfigSpec.Builder builder) {
            builder.comment("Entity Settings").push("entities");
            unikittyEntity = new UnikittyEntitySettings(builder);
            builder.pop();
        }

        public final UnikittyEntitySettings unikittyEntity;

        public static class UnikittyEntitySettings {
            public final ForgeConfigSpec.BooleanValue unikittyHairball;
            public final ForgeConfigSpec.BooleanValue unikittyDropExp;
            public final ForgeConfigSpec.IntValue unikittyMin;
            public final ForgeConfigSpec.IntValue unikittyMax;
            public final ForgeConfigSpec.IntValue unikittyWeight;
            public final ForgeConfigSpec.ConfigValue<List<? extends String>> unikittySpawnBlocks;
            public final ForgeConfigSpec.ConfigValue<List<? extends String>> unikittySpawnBiomes;
            public final ForgeConfigSpec.ConfigValue<List<? extends String>> unikittyNames;

            UnikittyEntitySettings(ForgeConfigSpec.Builder builder) {
                builder.comment("Unikitty Settings").push("unikitty");

                unikittyHairball = builder.comment("Should Unikitty's cough up hairball's").define("doHairballs", true);
                unikittyDropExp = builder.comment("Should Unikitty's Drop Experience when killed").define("dropExperience", true);

                builder.comment("Spawn Settings").push("spawnSettings");
                unikittyMin = builder.comment("Min spawn amount").defineInRange("min", 2, 0, 16);
                unikittyMax = builder.comment("Max spawn amount").defineInRange("max", 4, 0, 16);
                unikittyWeight = builder.comment("To disable spawning set weight to 0").defineInRange("weight", 7, 0, 100);
                builder.pop(1);

                builder.comment("Spawnable Blocks").push("spawnableBlocks");
                builder.comment("A list of blocks the entity can spawn on.\nFormat: modid:blockName, Example: [\"minecraft:grass_block\", \"minecraft:sand\"]");
                unikittySpawnBlocks = builder.defineList("spawnableBlocks", Collections.singletonList(Objects.requireNonNull(Blocks.GRASS_BLOCK.getRegistryName()).toString()), lt -> lt instanceof String && ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(lt.toString())));
                builder.pop(1);

                builder.comment("Spawnable Biomes").push("spawnableBiomes");
                builder.comment("A list of biomes the entity can spawn in.\nFormat: modid:biomeName, Example: [\"minecraft:forest\", \"minecraft:desert\"]");
                unikittySpawnBiomes = builder.defineList("spawnableBiomes", Arrays.asList(Biomes.FOREST.location().toString(), Biomes.FLOWER_FOREST.location().toString()), lt -> lt instanceof String && ForgeRegistries.BIOMES.containsKey(new ResourceLocation(lt.toString())));
                builder.pop(1);

                builder.comment("Unkikitty Names").push("unikittyNames");
                builder.comment("A list of nametag names that will make Unikitty's change color like when a sheep is named jeb_");
                unikittyNames = builder.defineList("unikittyNames", Arrays.asList("Kaena", "Casey", "Emma", "Kaitlyn"), lt -> lt instanceof String);
                builder.pop(1);

                builder.pop();
            }
        }
    }

    public static void setup() {
        final CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve("unikittymod.toml"))
                .sync()
                .autosave()
                .preserveInsertionOrder()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) { }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) { }

    private static List<Integer> getDefaultDurability() {
        List<Integer> defaultDurability = new ArrayList<>(4);
        defaultDurability.add(13);
        defaultDurability.add(15);
        defaultDurability.add(16);
        defaultDurability.add(11);

        return defaultDurability;
    }

    private static List<Integer> getDefaultDamageReduction() {
        List<Integer> defaultDurability = new ArrayList<>(4);
        defaultDurability.add(3);
        defaultDurability.add(6);
        defaultDurability.add(8);
        defaultDurability.add(3);

        return defaultDurability;
    }
}