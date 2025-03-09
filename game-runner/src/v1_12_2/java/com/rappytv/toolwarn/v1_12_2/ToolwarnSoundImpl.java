package com.rappytv.toolwarn.v1_12_2;

import com.rappytv.toolwarn.api.IToolwarnSounds;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;

@Singleton
@Implements(IToolwarnSounds.class)
public class ToolwarnSoundImpl implements IToolwarnSounds {

    private final ResourceLocation plingSound = ResourceLocation.create("minecraft", "block.note.pling");
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
