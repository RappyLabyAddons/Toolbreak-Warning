package com.rappytv.toolwarn.v1_12_2;

import com.rappytv.toolwarn.util.ITbwSounds;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;

@Singleton
@Implements(ITbwSounds.class)
public class TbwSoundImpl implements ITbwSounds {

    private final ResourceLocation plingSound = ResourceLocation.create("minecraft", "block.note.pling");
    private final ResourceLocation levelUpSound = ResourceLocation.create("minecraft", "entity.player.levelup");
    private final ResourceLocation glassBreakSound = ResourceLocation.create("minecraft", "block.glass.break");
    private final ResourceLocation anvilUseSound = ResourceLocation.create("minecraft", "block.anvil.use");

    @Override
    public ResourceLocation getPlingSound() {
        return plingSound;
    }
    @Override
    public ResourceLocation getLevelUpSound() {
        return levelUpSound;
    }
    @Override
    public @NotNull ResourceLocation getGlassBreakSound() {
        return glassBreakSound;
    }
    @Override
    public ResourceLocation getAnvilUseSound() {
        return anvilUseSound;
    }
}
