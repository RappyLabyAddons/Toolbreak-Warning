package com.rappytv.toolwarn;

import com.rappytv.toolwarn.api.WarnTool;
import com.rappytv.toolwarn.api.WarnTool.Type;
import com.rappytv.toolwarn.ui.activities.ToolConfigActivity;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.ActivitySettingWidget.ActivitySetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.util.MethodOrder;

@ConfigName("settings")
@SpriteTexture(value = "settings")
public class ToolwarnConfig extends AddonConfig {

    @Exclude
    private final List<WarnTool> tools = new ArrayList<>();
    @Exclude
    private final ConfigProperty<Boolean> createDefaultTools = new ConfigProperty<>(true);

    @SpriteSlot(size = 32)
    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SettingSection("tools")
    @IntroducedIn(namespace = "toolwarn", value = "1.4.0")
    @MethodOrder(after = "enabled")
    @SpriteSlot(size = 32, x = 1)
    @ActivitySetting
    public Activity toolConfig() {
        return new ToolConfigActivity();
    }

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    public void createDefaultTools() {
        if (!this.createDefaultTools.get()) {
            return;
        }
        this.createDefaultTools.set(false);
        if (!this.tools.isEmpty()) {
            return;
        }
        this.tools.add(new WarnTool()); // Sword is the default
        this.tools.add(new WarnTool(Type.PICKAXE));
        this.tools.add(new WarnTool(Type.AXE));
        this.tools.add(new WarnTool(Type.SHOVEL));
        this.tools.add(new WarnTool(Type.HOE));
        this.tools.add(new WarnTool(Type.BOW));
        this.tools.add(new WarnTool(Type.CROSSBOW));
        this.tools.add(new WarnTool(Type.LIGHTER));
        this.tools.add(new WarnTool(Type.SHEARS));
        this.tools.add(new WarnTool(Type.TRIDENT));
    }

    public void removeInvalidTools() {
        this.tools.removeIf(entry ->
            entry.getWarnAt() < 1
                || entry.getWarnAt() > 25
                || entry.getType() == null
        );
    }

    @Override
    public int getConfigVersion() {
        return 2;
    }

    public List<WarnTool> getTools() {
        return this.tools;
    }
}
