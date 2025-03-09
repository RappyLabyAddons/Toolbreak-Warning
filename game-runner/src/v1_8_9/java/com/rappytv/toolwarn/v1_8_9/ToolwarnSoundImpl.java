package com.rappytv.toolwarn.v1_8_9;

import com.rappytv.toolwarn.api.IToolwarnSounds;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;

@Singleton
@Implements(IToolwarnSounds.class)
public class ToolwarnSoundImpl implements IToolwarnSounds {

    private final ResourceLocation plingSound = ResourceLocation.create("minecraft", "note.pling");
    private final ResourceLocation levelUpSound = ResourceLocation.create("minecraft", "random.levelup");
    private final ResourceLocation glassBreakSound = ResourceLocation.create("minecraft", "game.potion.smash");
    private final ResourceLocation anvilUseSound = ResourceLocation.create("minecraft", "random.anvil_use");

    @Override
    public @NotNull ResourceLocation getPlingSound() {
        return this.plingSound;
    }
    @Override
    public @NotNull ResourceLocation getLevelUpSound() {
        return this.levelUpSound;
    }
    @Override
    public @NotNull ResourceLocation getGlassBreakSound() {
        return this.glassBreakSound;
    }
    @Override
    public @NotNull ResourceLocation getAnvilUseSound() {
        return this.anvilUseSound;
    }
}
