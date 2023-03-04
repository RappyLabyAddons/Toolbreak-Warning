package com.rappytv.tbw;

import com.rappytv.tbw.commands.TbwCommand;
import com.rappytv.tbw.listener.GameTickListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class TbwAddon extends LabyAddon<TbwConfiguration> {

    public static final String prefix = "§c§lTBW §8» §7";
    private static TbwAddon instance;

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

    @Override
    protected Class<? extends TbwConfiguration> configurationClass() {
        return TbwConfiguration.class;
    }
}
