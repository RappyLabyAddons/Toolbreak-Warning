package com.rappytv.toolwarn.util;

import com.rappytv.toolwarn.api.IToolwarnSounds;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Singleton
public class DefaultToolwarnSounds implements IToolwarnSounds {

    private final ResourceLocation plingSound = ResourceLocation.create("minecraft", "block.note_block.pling");
    private final ResourceLocation levelUpSound = ResourceLocation.create("minecraft", "entity.player.levelup");
    private final ResourceLocation glassBreakSound = ResourceLocation.create("minecraft", "block.glass.break");
    private final ResourceLocation anvilUseSound = ResourceLocation.create("minecraft", "block.anvil.use");

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
