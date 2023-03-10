package com.rappytv.tbw;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
@SpriteTexture(value = "settings")
public class TbwConfiguration extends AddonConfig {

    @SwitchSetting
    @SpriteSlot(size = 32)
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);
    @SwitchSetting
    @SpriteSlot(size = 32, x = 1)
    private final ConfigProperty<Boolean> format = new ConfigProperty<>(true);
    @SwitchSetting
    @SpriteSlot(size = 32, x = 2)
    private final ConfigProperty<Boolean> debug = new ConfigProperty<>(false);

    @SwitchSetting
    @SpriteSlot(size = 32, x = 3)
    private final ConfigProperty<Boolean> lastHit = new ConfigProperty<>(true);
    @SliderSetting(steps = 1, min = 1, max = 25)
    @SpriteSlot(size = 32, y = 1)
    private final ConfigProperty<Integer> swordPercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    @SpriteSlot(size = 32, x = 1, y = 1)
    private final ConfigProperty<Integer> pickaxePercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    @SpriteSlot(size = 32, x = 2, y = 1)
    private final ConfigProperty<Integer> axePercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    @SpriteSlot(size = 32, x = 3, y = 1)
    private final ConfigProperty<Integer> shovelPercentage = new ConfigProperty<>(5);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return enabled;
    }
    public ConfigProperty<Boolean> format() {
        return format;
    }
    public ConfigProperty<Boolean> debug() {
        return debug;
    }

    public ConfigProperty<Boolean> lastHit() {
        return lastHit;
    }
    public ConfigProperty<Integer> swordPercentage() {
        return swordPercentage;
    }
    public ConfigProperty<Integer> pickAxePercentage() {
        return pickaxePercentage;
    }
    public ConfigProperty<Integer> axePercentage() {
        return axePercentage;
    }
    public ConfigProperty<Integer> shovelPercentage() {
        return shovelPercentage;
    }
}
