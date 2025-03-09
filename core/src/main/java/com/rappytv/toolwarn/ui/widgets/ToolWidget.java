package com.rappytv.toolwarn.ui.widgets;

import com.rappytv.toolwarn.api.WarnTool;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;

@AutoWidget
public class ToolWidget extends SimpleWidget {

    private static final Component enabled = Component.text("✔", NamedTextColor.GREEN);
    private static final Component disabled = Component.text("✘", NamedTextColor.RED);
    private WarnTool tool;

    public ToolWidget(WarnTool tool) {
        this.tool = tool;
    }

    @Override
    public void initialize(Parent parent) {
        super.initialize(parent);
        IconWidget iconWidget = new IconWidget(this.tool.getType().getIcon())
            .addId("tool-icon");

        ComponentWidget nameWidget = ComponentWidget.i18n(
            "toolwarn.gui.dropdown.type." + this.tool.getType().name().toLowerCase()
        ).addId("name-component");

        ComponentWidget meta = ComponentWidget.component(Component.translatable(
            "toolwarn.gui.meta",
            Component.text(this.tool.getWarnAt()),
            this.tool.openChat() ? enabled : disabled,
            this.tool.lastHitWarn() ? enabled : disabled
        )).addId("meta-component");

        this.addChild(iconWidget);
        this.addChild(nameWidget);
        this.addChild(meta);
    }

    public WarnTool getTool() {
        return this.tool;
    }
    public void setTool(WarnTool tool) {
        this.tool = tool;
    }
}
