package com.rappytv.toolwarn;

import com.rappytv.toolwarn.commands.TbwCommand;
import com.rappytv.toolwarn.core.generated.DefaultReferenceStorage;
import com.rappytv.toolwarn.listener.GameTickListener;
import com.rappytv.toolwarn.util.ITbwSounds;
import com.rappytv.toolwarn.util.WarnSound;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class TbwAddon extends LabyAddon<TbwConfiguration> {

    public static final String prefix = "§c§lTBW §8» §7";
    public static WarnSound sound;
    private static ITbwSounds sounds;
    private static TbwAddon instance;

    @Override
    protected void load() {
        sounds = ((DefaultReferenceStorage) this.referenceStorageAccessor()).iTbwSounds();
        sound = WarnSound.Pling;
    }

    @Override
    protected void enable() {
        instance = this;
        registerSettingCategory();

        registerCommand(new TbwCommand(this));
        registerListener(new GameTickListener(this));
    }

    public static TbwAddon get() {
        return instance;
    }

    public static ITbwSounds getSounds() {
        return sounds;
    }

    @Override
    protected Class<? extends TbwConfiguration> configurationClass() {
        return TbwConfiguration.class;
    }
}
