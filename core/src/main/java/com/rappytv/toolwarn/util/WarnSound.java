package com.rappytv.toolwarn.util;

import com.rappytv.toolwarn.TbwAddon;
import net.labymod.api.client.resources.ResourceLocation;

public enum WarnSound {
    AnvilUse(TbwAddon.getSounds().getAnvilUseSound()),
    Pling(TbwAddon.getSounds().getPlingSound());

    private final ResourceLocation resourceLocation;

    WarnSound(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }
}
