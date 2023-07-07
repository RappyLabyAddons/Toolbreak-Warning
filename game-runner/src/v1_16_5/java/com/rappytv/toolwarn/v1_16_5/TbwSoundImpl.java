package com.rappytv.toolwarn.v1_16_5;

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
        return ResourceLocation.create("minecraft", "block.note_block.pling");
    }
    @Override
    public ResourceLocation getLevelUpSound() {
        return ResourceLocation.create("minecraft", "entity.player.levelup");
    }
    @Override
    public @NotNull ResourceLocation getGlassBreakSound() {
        return ResourceLocation.create("minecraft", "block.glass.break");
    }
    @Override
    public ResourceLocation getAnvilUseSound() {
        return ResourceLocation.create("minecraft", "block.anvil.use");
    }
}
