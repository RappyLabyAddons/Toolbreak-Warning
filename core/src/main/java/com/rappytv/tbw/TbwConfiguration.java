package com.rappytv.tbw;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class TbwConfiguration extends AddonConfig {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);
    @SwitchSetting
    private final ConfigProperty<Boolean> format = new ConfigProperty<>(true);
    @SwitchSetting
    private final ConfigProperty<Boolean> debug = new ConfigProperty<>(false);

    @SwitchSetting
    private final ConfigProperty<Boolean> lastHit = new ConfigProperty<>(true);
    @SliderSetting(steps = 1, min = 1, max = 25)
    private final ConfigProperty<Integer> swordPercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    private final ConfigProperty<Integer> pickaxePercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    private final ConfigProperty<Integer> axePercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    private final ConfigProperty<Integer> shovelPercentage = new ConfigProperty<>(5);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return enabled;
    }
    public ConfigProperty<Boolean> format() {
        return enabled;
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
