package com.rappytv.toolwarn.v1_17_1;

import com.rappytv.toolwarn.util.ITbwSounds;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

@Singleton
@Implements(ITbwSounds.class)
public class TbwSoundImpl implements ITbwSounds {

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
