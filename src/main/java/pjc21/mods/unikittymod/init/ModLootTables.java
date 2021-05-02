package pjc21.mods.unikittymod.init;

import net.minecraft.util.ResourceLocation;
import pjc21.mods.unikittymod.UnikittyMod;

public class ModLootTables {
    public static final ResourceLocation UNIKITTY_WHITE = register("entities/unikitty_white");
    public static final ResourceLocation UNIKITTY_ORANGE = register("entities/unikitty_orange");
    public static final ResourceLocation UNIKITTY_MAGENTA = register("entities/unikitty_magenta");
    public static final ResourceLocation UNIKITTY_LIGHT_BLUE = register("entities/unikitty_light_blue");
    public static final ResourceLocation UNIKITTY_YELLOW = register("entities/unikitty_yellow");
    public static final ResourceLocation UNIKITTY_LIME = register("entities/unikitty_lime");
    public static final ResourceLocation UNIKITTY_PINK = register("entities/unikitty_pink");
    public static final ResourceLocation UNIKITTY_GRAY = register("entities/unikitty_gray");
    public static final ResourceLocation UNIKITTY_LIGHT_GRAY = register("entities/unikitty_light_gray");
    public static final ResourceLocation UNIKITTY_CYAN = register("entities/unikitty_cyan");
    public static final ResourceLocation UNIKITTY_PURPLE = register("entities/unikitty_purple");
    public static final ResourceLocation UNIKITTY_BLUE = register("entities/unikitty_blue");
    public static final ResourceLocation UNIKITTY_BROWN = register("entities/unikitty_brown");
    public static final ResourceLocation UNIKITTY_GREEN = register("entities/unikitty_green");
    public static final ResourceLocation UNIKITTY_RED = register("entities/unikitty_red");
    public static final ResourceLocation UNIKITTY_BLACK = register("entities/unikitty_black");

    private static ResourceLocation register(String id) {
        return new ResourceLocation(UnikittyMod.MODID, id);
    }
}
