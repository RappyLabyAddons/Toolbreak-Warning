package com.rappytv.toolwarn.api;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Nullable
@Referenceable
public interface IToolwarnSounds {

    @NotNull ResourceLocation getPlingSound();
    @NotNull ResourceLocation getLevelUpSound();
    @NotNull ResourceLocation getGlassBreakSound();
    @NotNull ResourceLocation getAnvilUseSound();
}
