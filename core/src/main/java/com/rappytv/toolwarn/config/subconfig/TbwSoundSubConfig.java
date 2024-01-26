package com.rappytv.toolwarn.config.subconfig;

import com.rappytv.toolwarn.util.WarnSound;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class TbwSoundSubConfig extends Config {

    @ParentSwitch
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);
    @DropdownSetting
    @SpriteSlot(size = 32, y = 1, x = 1)
    private final ConfigProperty<WarnSound> warnSound = new ConfigProperty<>(WarnSound.PLING);
    @DropdownSetting
    @SpriteSlot(size = 32, y = 1, x = 2)
    private final ConfigProperty<WarnSound> lastHitSound = new ConfigProperty<>(WarnSound.ANVIL_USE);

    public ConfigProperty<Boolean> enabled() {
        return enabled;
    }
    public ConfigProperty<WarnSound> warnSound() {
        return warnSound;
    }
    public ConfigProperty<WarnSound> lastHitSound() {
        return lastHitSound;
    }
}
