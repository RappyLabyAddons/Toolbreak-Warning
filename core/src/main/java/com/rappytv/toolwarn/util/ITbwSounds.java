package com.rappytv.toolwarn.util;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

@Referenceable
public interface ITbwSounds {

    @NotNull ResourceLocation getPlingSound();
    @NotNull ResourceLocation getAnvilUseSound();
}
