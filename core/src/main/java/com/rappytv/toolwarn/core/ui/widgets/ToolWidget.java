package com.rappytv.toolwarn.core.ui.widgets;

import com.rappytv.toolwarn.api.item.WarnTool;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;

@AutoWidget
public class ToolWidget extends HorizontalListWidget {

  private static final Component enabled = Component.text("✔", NamedTextColor.GREEN);
  private static final Component disabled = Component.text("✘", NamedTextColor.RED);

  private WarnTool tool;

  public ToolWidget(WarnTool tool) {
    this.tool = tool;
  }

  @Override
  public void initialize(Parent parent) {
    super.initialize(parent);

    if (this.tool.isEnabled()) {
      this.removeId("disabled");
    } else {
      this.addId("disabled");
    }

    IconWidget iconWidget = new IconWidget(this.tool.getType().getIcon());
    iconWidget.addId("tool-icon");

    FlexibleContentWidget componentContainer = new FlexibleContentWidget();
    componentContainer.addId("components");

    ComponentWidget nameWidget = ComponentWidget.i18n(this.tool.getType().translationKey());
    nameWidget.addId("name-component");

    ComponentWidget meta = ComponentWidget.component(Component.translatable(
        "toolwarn.ui.tool.meta",
        Component.text(this.tool.getWarnAt()),
        this.tool.openChat() ? enabled : disabled,
        this.tool.lastHitWarn() ? enabled : disabled
    ));
    meta.addId("meta-component");

    componentContainer.addContent(nameWidget);
    componentContainer.addContent(meta);

    this.addEntry(iconWidget);
    this.addEntry(componentContainer);
  }

  public WarnTool getTool() {
    return this.tool;
  }

  public void setTool(WarnTool tool) {
    this.tool = tool;
  }
}
