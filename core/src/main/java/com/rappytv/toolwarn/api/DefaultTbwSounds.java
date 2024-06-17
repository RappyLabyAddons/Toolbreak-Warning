package com.rappytv.toolwarn.api;

import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import javax.inject.Singleton;

@Singleton
public class DefaultTbwSounds implements ITbwSounds {

    private final ResourceLocation plingSound = ResourceLocation.create("minecraft", "block.note_block.pling");
    private final ResourceLocation levelUpSound = ResourceLocation.create("minecraft", "entity.player.levelup");
    private final ResourceLocation glassBreakSound = ResourceLocation.create("minecraft", "block.glass.break");
    private final ResourceLocation anvilUseSound = ResourceLocation.create("minecraft", "block.anvil.use");

    @Override
    public @NotNull ResourceLocation getPlingSound() {
        return plingSound;
    }
    @Override
    public @NotNull ResourceLocation getLevelUpSound() {
        return levelUpSound;
    }
    @Override
    public @NotNull ResourceLocation getGlassBreakSound() {
        return glassBreakSound;
    }
    @Override
    public @NotNull ResourceLocation getAnvilUseSound() {
        return anvilUseSound;
    }
}
