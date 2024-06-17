package com.rappytv.toolwarn.v1_8_9;

import com.rappytv.toolwarn.api.ITbwSounds;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;

@Singleton
@Implements(ITbwSounds.class)
public class TbwSoundImpl implements ITbwSounds {

    private final ResourceLocation plingSound = ResourceLocation.create("minecraft", "note.pling");
    private final ResourceLocation levelUpSound = ResourceLocation.create("minecraft", "random.levelup");
    private final ResourceLocation glassBreakSound = ResourceLocation.create("minecraft", "game.potion.smash");
    private final ResourceLocation anvilUseSound = ResourceLocation.create("minecraft", "random.anvil_use");

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
