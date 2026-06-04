package com.rappytv.toolwarn.core.ui.activities;

import com.rappytv.toolwarn.api.item.WarnTool;
import com.rappytv.toolwarn.core.ui.widgets.ToolWidget;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup.SimplePopupButton;

@AutoActivity
@Link("tool-manager.lss")
public class ToolManagerActivity extends Activity {

  private final List<WarnTool> tools;
  private final Map<WarnTool, ToolWidget> toolWidgets = new HashMap<>();
  private final VerticalListWidget<ToolWidget> toolList;

  private WarnTool selectedTool;

  private ButtonWidget editButton;
  private ButtonWidget removeButton;

  public ToolManagerActivity(List<WarnTool> tools) {
    this.tools = tools;
    this.toolList = new VerticalListWidget<>();
    this.loadTools();

    this.toolList.addId("tool-list");
    this.toolList.setSelectCallback(selected -> {
      this.selectedTool = selected.getTool();
      this.editButton.setEnabled(true);
      this.removeButton.setEnabled(true);
    });
    this.toolList.setDoubleClickCallback(ignored -> this.performAction(Action.EDIT));
  }

  @Override
  public void initialize(Parent parent) {
    super.initialize(parent);

    FlexibleContentWidget container = new FlexibleContentWidget();
    container.addId("tool-container");
    for (ToolWidget toolWidget : this.toolWidgets.values()) {
      this.toolList.addChild(toolWidget);
    }

    HorizontalListWidget buttons = new HorizontalListWidget();
    buttons.addId("buttons");

    ButtonWidget addButton = ButtonWidget.i18n(
        "labymod.ui.button.add",
        () -> this.performAction(Action.ADD)
    );

    this.editButton = ButtonWidget.i18n(
        "labymod.ui.button.edit",
        () -> this.performAction(Action.EDIT)
    );
    this.editButton.setEnabled(false);

    this.removeButton = ButtonWidget.i18n(
        "labymod.ui.button.remove",
        () -> this.performAction(Action.REMOVE)
    );
    this.removeButton.setEnabled(false);

    buttons.addEntry(addButton);
    buttons.addEntry(this.editButton);
    buttons.addEntry(this.removeButton);

    container.addFlexibleContent(new ScrollWidget(this.toolList));
    container.addContent(buttons);

    this.document.addChild(container);
  }

  @Override
  public void reload() {
    this.loadTools();
    super.reload();
  }

  private void loadTools() {
    this.toolWidgets.clear();
    this.tools.forEach((tool) ->
        this.toolWidgets.put(tool, new ToolWidget(tool))
    );
  }

  private void performAction(Action action) {
    switch (action) { // TODO: implement add and edit actions
      case ADD -> {
        // add
      }
      case EDIT -> {
        // edit
      }
      case REMOVE -> SimpleAdvancedPopup.builder()
          .title(Component.translatable("toolwarn.ui.popup.remove.title"))
          .description(Component.translatable(
              "toolwarn.ui.popup.remove.description",
              Component.text(this.selectedTool.getType().getTranslation(), NamedTextColor.AQUA)
          ))
          .addButton(SimplePopupButton.confirm(simplePopupButton -> {
            this.tools.remove(this.selectedTool);
            this.reload();
          }))
          .addButton(SimplePopupButton.cancel())
          .build()
          .displayInOverlay();
    }
  }

  private enum Action {
    ADD,
    EDIT,
    REMOVE
  }
}
