package pjc21.mods.unikittymod.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.config.UnikittyConfig;
import pjc21.mods.unikittymod.entities.unikitty.UnikittyEntity;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, UnikittyMod.MODID);
    public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(ForgeRegistries.ITEMS, UnikittyMod.MODID);
    public static final RegistryObject<EntityType<UnikittyEntity>> UNIKITTYENTITY = getEntityRegistry("unikitty", UnikittyEntity::new, 0xD317FB,0x13C03B, EntityClassification.CREATURE, 0.6F, 1.7F );

    private static <T extends Entity> RegistryObject<EntityType<T>> getEntityRegistry(String name, final EntityType.IFactory<T> entityIn, int primaryColor, int secondaryColor, EntityClassification classification, float minsize, float maxsize) {
        EntityType<T> entityType = EntityType.Builder.of(entityIn, classification)
                .sized(minsize, maxsize)
                .build(name);

        SPAWN_EGGS.register(String.format("%s_spawn_egg", name), () -> new SpawnEggItem(entityType, primaryColor, secondaryColor, new Item.Properties().tab(UnikittyMod.ITEM_GROUP)));

        return ENTITIES.register(name, () -> entityType);
    }

    @Mod.EventBusSubscriber(modid = UnikittyMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
            EntitySpawnPlacementRegistry.register(UNIKITTYENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, UnikittyEntity::checkUnikittySpawnRules);
        }

        @SubscribeEvent
        public static void registerAttributes(final EntityAttributeCreationEvent event) {
            event.put(UNIKITTYENTITY.get(), UnikittyEntity.createAttributes().build());
        }

        public static void spawnEntities (BiomeLoadingEvent event) {
            Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
            if (biome != null && UnikittyConfig.entitySettings.unikittyEntity.unikittySpawnBiomes.get().contains(biome.toString()))
                event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntities.UNIKITTYENTITY.get(), UnikittyConfig.entitySettings.unikittyEntity.unikittyWeight.get(), UnikittyConfig.entitySettings.unikittyEntity.unikittyMin.get(), UnikittyConfig.entitySettings.unikittyEntity.unikittyMax.get()));
        }
    }

    public static void init(final IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
        SPAWN_EGGS.register(modEventBus);
    }
}
