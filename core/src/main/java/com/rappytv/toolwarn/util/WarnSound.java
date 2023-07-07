package com.rappytv.toolwarn.util;

import com.rappytv.toolwarn.TbwAddon;
import net.labymod.api.client.resources.ResourceLocation;

public enum WarnSound {
    NONE,
    PLING,
    LEVEL_UP,
    GLASS_BREAK,
    ANVIL_USE;

    public ResourceLocation getResourceLocation() {
        return switch (this) {
            case NONE -> null;
            case ANVIL_USE -> TbwAddon.getSounds().getAnvilUseSound();
            case LEVEL_UP -> TbwAddon.getSounds().getLevelUpSound();
            case GLASS_BREAK -> TbwAddon.getSounds().getGlassBreakSound();
            case PLING -> TbwAddon.getSounds().getPlingSound();
        };
    }
}
