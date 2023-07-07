package com.rappytv.toolwarn.v1_8_9;

import com.rappytv.toolwarn.util.ITbwSounds;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;

@Singleton
@Implements(ITbwSounds.class)
public class TbwSoundImpl implements ITbwSounds {

    @Override
    public ResourceLocation getPlingSound() {
        return ResourceLocation.create("minecraft", "note.pling");
    }
    @Override
    public ResourceLocation getLevelUpSound() {
        return ResourceLocation.create("minecraft", "random.levelup");
    }
    @Override
    public @NotNull ResourceLocation getGlassBreakSound() {
        return ResourceLocation.create("minecraft", "game.potion.smash");
    }
    @Override
    public ResourceLocation getAnvilUseSound() {
        return ResourceLocation.create("minecraft", "random.anvil_use");
    }
}
