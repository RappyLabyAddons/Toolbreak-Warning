package com.rappytv.toolwarn.ui;

import com.rappytv.toolwarn.util.WarnTool;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.util.I18n;

@AutoWidget
public class ToolWidget extends SimpleWidget {

    private WarnTool tool;

    public ToolWidget(WarnTool tool) {
        this.tool = tool;
    }

    @Override
    public void initialize(Parent parent) {
        super.initialize(parent);
        IconWidget iconWidget = new IconWidget(tool.getType().getIcon())
            .addId("tool-icon");

        ComponentWidget nameWidget = ComponentWidget.text(tool.getType().name())
            .addId("name-component");

        ComponentWidget meta = ComponentWidget.text(I18n.translate(
            "toolwarn.gui.meta",
            tool.getWarnAt(),
            tool.openChat() ? "§a✔§r" : "§c✘§r",
            tool.lastHitWarn() ? "§a✔§r" : "§c✘§r"
        )).addId("meta-component");

        this.addChild(iconWidget);
        this.addChild(nameWidget);
        this.addChild(meta);
    }

    public WarnTool getTool() {
        return tool;
    }
    public void setTool(WarnTool tool) {
        this.tool = tool;
    }
}
