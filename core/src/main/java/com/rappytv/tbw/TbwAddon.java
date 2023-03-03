package com.rappytv.tbw;

import com.rappytv.tbw.commands.TbwCommand;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class TbwAddon extends LabyAddon<TbwConfiguration> {

    public static final String prefix = "§c§lTBW §8» §7";

    @Override
    protected void enable() {
        registerSettingCategory();

        registerCommand(new TbwCommand(this));
    }

    @Override
    protected Class<? extends TbwConfiguration> configurationClass() {
        return TbwConfiguration.class;
    }
}
