package pjc21.mods.unikittymod.data;

import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.Smelt;
import net.minecraftforge.registries.ForgeRegistries;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.init.ModEntities;
import pjc21.mods.unikittymod.init.ModItems;
import pjc21.mods.unikittymod.init.ModLootTables;

import java.util.stream.Collectors;

public class ModEntityLootTables extends EntityLootTables {

    @Override
    protected void addTables() {
        this.add(ModEntities.UNIKITTYENTITY.get(), getBaseLootPool());
        this.add(ModLootTables.UNIKITTY_WHITE, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.WHITE)));
        this.add(ModLootTables.UNIKITTY_ORANGE, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.ORANGE)));
        this.add(ModLootTables.UNIKITTY_MAGENTA, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.MAGENTA)));
        this.add(ModLootTables.UNIKITTY_LIGHT_BLUE, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.LIGHT_BLUE)));
        this.add(ModLootTables.UNIKITTY_YELLOW, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.YELLOW)));
        this.add(ModLootTables.UNIKITTY_LIME, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.LIME)));
        this.add(ModLootTables.UNIKITTY_PINK, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.PINK)));
        this.add(ModLootTables.UNIKITTY_GRAY, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.GRAY)));
        this.add(ModLootTables.UNIKITTY_LIGHT_GRAY, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.LIGHT_GRAY)));
        this.add(ModLootTables.UNIKITTY_CYAN, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.CYAN)));
        this.add(ModLootTables.UNIKITTY_PURPLE, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.PURPLE)));
        this.add(ModLootTables.UNIKITTY_BLUE, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.BLUE)));
        this.add(ModLootTables.UNIKITTY_BROWN, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.BROWN)));
        this.add(ModLootTables.UNIKITTY_GREEN, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.GREEN)));
        this.add(ModLootTables.UNIKITTY_RED, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.RED)));
        this.add(ModLootTables.UNIKITTY_BLACK, getBaseLootPool().withPool(getLootPoolByColor(DyeColor.BLACK)));
    }

    private LootPool.Builder getLootPoolByColor(DyeColor dyeColor){
        return LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(ModItems.COLORED_STRING.get(dyeColor).get())
                .apply(SetCount.setCount(RandomValueRange.between(2.0F, 4.0F)).when(KilledByPlayer.killedByPlayer())));
    }

    private LootTable.Builder getBaseLootPool(){
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(ModItems.UNIKITTY_RAW.get())
                                .setWeight(1)
                                .apply(SetCount.setCount(RandomValueRange.between(1.0F,3.0F)))
                                .apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(ModItems.BLUE_CRYSTAL.get())
                                .setWeight(40)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(KilledByPlayer.killedByPlayer())
                                .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.2F, 0.01F)))
                        .add(ItemLootEntry.lootTableItem(ModItems.UNIKITTY_HORN.get())
                                .setWeight(20)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(KilledByPlayer.killedByPlayer())
                                .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.05F, 0.01F))));
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected Iterable<EntityType<?>> getKnownEntities() {
        return ForgeRegistries.ENTITIES.getValues().stream()
                .filter(entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(UnikittyMod.MODID))
                .collect(Collectors.toList());
    }
}
