package com.rappytv.toolwarn.ui;

import com.rappytv.toolwarn.util.ToolType;
import com.rappytv.toolwarn.util.WarnSound;
import com.rappytv.toolwarn.util.WarnTool;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.resources.ResourceLocation;

@Link("tool.lss")
@AutoWidget
public class ToolWidget extends SimpleWidget {

    private final ToolType toolType;
    private final WarnSound warnSound;
    private final int warnAt;
    private final boolean openChat;
    private final boolean lastHitWarn;

    public ToolWidget(ToolType toolType, WarnTool warnTool) {
        this.toolType = toolType;

    }

    @Override
    public void initialize(Parent parent) {
        super.initialize(parent);
        IconWidget iconWidget = new IconWidget(Icon.texture(ResourceLocation.create("minecraft", "")))
            .addId("tool-icon");

        ComponentWidget nameWidget = ComponentWidget.text(this.toolType.name())
            .addId("name-component");

        ComponentWidget meta = ComponentWidget.i18n("", warnAt, warnSound, openChat, lastHitWarn)
            .addId("meta-component");

        this.addChild(iconWidget);
        this.addChild(nameWidget);
        this.addChild(meta);
    }
}
