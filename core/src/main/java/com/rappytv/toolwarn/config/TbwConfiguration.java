package com.rappytv.toolwarn.config;

import com.rappytv.toolwarn.config.subconfig.TbwSoundSubConfig;
import com.rappytv.toolwarn.ui.ToolConfigActivity;
import com.rappytv.toolwarn.util.WarnTool;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.ActivitySettingWidget.ActivitySetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.util.MethodOrder;
import java.util.ArrayList;
import java.util.List;

@ConfigName("settings")
@SpriteTexture(value = "settings")
public class TbwConfiguration extends AddonConfig {

    @Exclude
    private final List<WarnTool> tools = new ArrayList<>();

    public List<WarnTool> getTools() {
        return tools;
    }
    public void removeInvalidTools() {
        this.tools.removeIf(entry -> entry.getWarnAt() < 1 || entry.getWarnAt() > 25);
    }

    @SwitchSetting
    @SpriteSlot(size = 32)
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);
    @SettingSection("general")
    @SwitchSetting
    @SpriteSlot(size = 32, x = 1)
    private final ConfigProperty<Boolean> openChat = new ConfigProperty<>(true);
    @SwitchSetting
    @SpriteSlot(size = 32, x = 3)
    private final ConfigProperty<Boolean> lastHit = new ConfigProperty<>(true);

    @SettingSection("sounds")
    @SpriteSlot(size = 32, y = 1)
    private final TbwSoundSubConfig sounds = new TbwSoundSubConfig();

    @SettingSection("tools")
    @MethodOrder(after = "sounds")
    @ActivitySetting
    public Activity toolConfig() {
        return new ToolConfigActivity();
    }
    @SliderSetting(steps = 1, min = 1, max = 25)
    @SpriteSlot(size = 32, y = 2)
    private final ConfigProperty<Integer> swordPercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    @SpriteSlot(size = 32, y = 2, x = 1)
    private final ConfigProperty<Integer> pickaxePercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    @SpriteSlot(size = 32, y = 2, x = 2)
    private final ConfigProperty<Integer> axePercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    @SpriteSlot(size = 32, y = 2, x = 3)
    private final ConfigProperty<Integer> shovelPercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    @SpriteSlot(size = 32, y = 3)
    @VersionCompatibility("1.14<*")
    @IntroducedIn(namespace = "globaltags", value = "1.4.3")
    private final ConfigProperty<Integer> crossbowPercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    @SpriteSlot(size = 32, y = 3, x = 1)
    @IntroducedIn(namespace = "globaltags", value = "1.4.3")
    private final ConfigProperty<Integer> lighterPercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    @SpriteSlot(size = 32, y = 3, x = 2)
    @IntroducedIn(namespace = "globaltags", value = "1.4.3")
    private final ConfigProperty<Integer> shearsPercentage = new ConfigProperty<>(5);
    @SliderSetting(steps = 1, min = 1, max = 25)
    @SpriteSlot(size = 32, y = 3, x = 3)
    @VersionCompatibility("1.13<*")
    @IntroducedIn(namespace = "globaltags", value = "1.4.3")
    private final ConfigProperty<Integer> tridentPercentage = new ConfigProperty<>(5);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return enabled;
    }
    public ConfigProperty<Boolean> openChat() {
        return openChat;
    }
    public ConfigProperty<Boolean> lastHit() {
        return lastHit;
    }

    public TbwSoundSubConfig sounds() {
        return sounds;
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
    public ConfigProperty<Integer> crossbowPercentage() {
        return crossbowPercentage;
    }
    public ConfigProperty<Integer> lighterPercentage() {
        return lighterPercentage;
    }
    public ConfigProperty<Integer> shearsPercentage() {
        return shearsPercentage;
    }
    public ConfigProperty<Integer> tridentPercentage() {
        return tridentPercentage;
    }
}
