package com.rappytv.toolwarn;

import com.rappytv.toolwarn.commands.TbwCommand;
import com.rappytv.toolwarn.config.TbwConfiguration;
import com.rappytv.toolwarn.core.generated.DefaultReferenceStorage;
import com.rappytv.toolwarn.listener.GameTickListener;
import com.rappytv.toolwarn.util.ITbwSounds;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class TbwAddon extends LabyAddon<TbwConfiguration> {

    public static final String prefix = "§c§lTBW §8» §7";
    private static ITbwSounds sounds;

    @Override
    protected void enable() {
        sounds = ((DefaultReferenceStorage) this.referenceStorageAccessor()).iTbwSounds();
        registerSettingCategory();

        registerCommand(new TbwCommand(this));
        registerListener(new GameTickListener(this));
    }

    public static ITbwSounds getSounds() {
        return sounds;
    }

    @Override
    protected Class<? extends TbwConfiguration> configurationClass() {
        return TbwConfiguration.class;
    }
}
