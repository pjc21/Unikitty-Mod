package pjc21.mods.unikittymod.init;

import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import pjc21.mods.unikittymod.UnikittyMod;

public class ModTags {

    public static class Blocks { }

    public static class Items {

        public static final ITag.INamedTag<Item> UNIKITTY_HORN = tag("horn");
        public static final ITag.INamedTag<Item> UNIKITTY_RAW = tag("food");
        public static final ITag.INamedTag<Item> UNIKITTY_COOKED = tag("food");

        public static final ITag.INamedTag<Item> UNIKITTY_HELMET = tag("helmet");
        public static final ITag.INamedTag<Item> UNIKITTY_CHESTPLATE = tag("chestplate");
        public static final ITag.INamedTag<Item> UNIKITTY_LEGGINGS = tag("leggings");
        public static final ITag.INamedTag<Item> UNIKITTY_BOOTS = tag("boots");

        public static final ITag.INamedTag<Item> WHITE_STRING = tag("white_string");
        public static final ITag.INamedTag<Item> ORANGE_STRING = tag("orange_string");
        public static final ITag.INamedTag<Item> MAGENTA_STRING = tag("magenta_string");
        public static final ITag.INamedTag<Item> LIGHT_BLUE_STRING = tag("light_blue_string");
        public static final ITag.INamedTag<Item> YELLOW_STRING = tag("yellow_string");
        public static final ITag.INamedTag<Item> LIME_STRING = tag("lime_string");
        public static final ITag.INamedTag<Item> PINK_STRING = tag("pink_string");
        public static final ITag.INamedTag<Item> GRAY_STRING = tag("gray_string");
        public static final ITag.INamedTag<Item> LIGHT_GRAY_STRING = tag("light_gray_string");
        public static final ITag.INamedTag<Item> CYAN_STRING = tag("cyan_string");
        public static final ITag.INamedTag<Item> PURPLE_STRING = tag("purple_string");
        public static final ITag.INamedTag<Item> BLUE_STRING = tag("blue_string");
        public static final ITag.INamedTag<Item> BROWN_STRING = tag("brown_string");
        public static final ITag.INamedTag<Item> GREEN_STRING = tag("green_string");
        public static final ITag.INamedTag<Item> RED_STRING = tag("red_string");
        public static final ITag.INamedTag<Item> BLACK_STRING = tag("black_string");

        public static ITag.INamedTag<Item> getColorTag(DyeColor color) {
            switch (color) {
                default:
                case WHITE:
                    return WHITE_STRING;
                case ORANGE:
                    return ORANGE_STRING;
                case MAGENTA:
                    return MAGENTA_STRING;
                case LIGHT_BLUE:
                    return LIGHT_BLUE_STRING;
                case YELLOW:
                    return YELLOW_STRING;
                case LIME:
                    return LIME_STRING;
                case PINK:
                    return PINK_STRING;
                case GRAY:
                    return GRAY_STRING;
                case LIGHT_GRAY:
                    return LIGHT_GRAY_STRING;
                case CYAN:
                    return CYAN_STRING;
                case PURPLE:
                    return PURPLE_STRING;
                case BLUE:
                    return BLUE_STRING;
                case BROWN:
                    return BROWN_STRING;
                case GREEN:
                    return GREEN_STRING;
                case RED:
                    return RED_STRING;
                case BLACK:
                    return BLACK_STRING;
            }
        }

        private static ITag.INamedTag<Item> tag(final String name) {
            return ItemTags.bind(new ResourceLocation(UnikittyMod.MODID, name).toString());
        }
    }

    public static class EntityTypes {

        public static final ITag.INamedTag<EntityType<?>> UNIKITTYENTITY = tag("unikitty");

        private static ITag.INamedTag<EntityType<?>> tag(final String name) {
            return EntityTypeTags.createOptional(new ResourceLocation(UnikittyMod.MODID, name));
        }
    }
}
