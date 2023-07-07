package com.rappytv.toolwarn.util;

import com.rappytv.toolwarn.TbwAddon;
import net.labymod.api.client.resources.ResourceLocation;

public enum WarnSound {
    NONE,
    PLING,
    ANVIL_USE;

    public ResourceLocation getResourceLocation() {
        return switch (this) {
            case NONE -> null;
            case ANVIL_USE -> TbwAddon.getSounds().getAnvilUseSound();
            case PLING -> TbwAddon.getSounds().getPlingSound();
        };
    }
}
