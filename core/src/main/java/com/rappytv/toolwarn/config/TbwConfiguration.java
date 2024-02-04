package com.rappytv.toolwarn.config;

import com.rappytv.toolwarn.ui.ToolConfigActivity;
import com.rappytv.toolwarn.util.WarnTool;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.ActivitySettingWidget.ActivitySetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
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
        this.tools.removeIf(entry ->
            entry.getWarnAt() < 1
                || entry.getWarnAt() > 25
                || entry.getType() == null
        );
    }

    @SwitchSetting
    @SpriteSlot(size = 32)
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SettingSection("tools")
    @MethodOrder(after = "enabled")
    @ActivitySetting
    public Activity toolConfig() {
        return new ToolConfigActivity();
    }

    @Override
    public ConfigProperty<Boolean> enabled() {
        return enabled;
    }
}
