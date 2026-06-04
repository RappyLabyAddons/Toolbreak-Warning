package com.rappytv.toolwarn.core.ui.popup;

import com.rappytv.toolwarn.api.WarnSound;
import com.rappytv.toolwarn.api.item.WarnTool;
import java.util.ArrayList;
import java.util.function.Consumer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget.State;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;
import net.labymod.api.util.TextFormat;
import org.jetbrains.annotations.Nullable;

@Link("popup.lss")
public class EditToolPopup extends SimpleAdvancedPopup {

  private final WarnTool tool;

  private CheckBoxWidget enabledCheckbox;
  private DropdownWidget<WarnTool.Type> toolTypeDropdown;
  private SliderWidget warnAtSlider;
  private CheckBoxWidget openChatCheckbox;
  private CheckBoxWidget lastHitWarnCheckbox;
  private DropdownWidget<WarnSound> soundDropdown;
  private DropdownWidget<WarnSound> lastSoundDropdown;

  public EditToolPopup(WarnTool tool, boolean create, Consumer<@Nullable WarnTool> onSubmit) {
    this.tool = tool;

    super.title = Component.translatable(
        "toolwarn.ui.popup.settings.component." + (create ? "add" : "edit"));
    super.buttons = new ArrayList<>();
    super.buttons.add(SimplePopupButton.confirm((ignored) -> {
      tool.setEnabled(this.enabledCheckbox.state() == State.CHECKED);
      tool.setType(this.toolTypeDropdown.getSelected());
      tool.setWarnAt((int) this.warnAtSlider.getValue());
      tool.setOpenChat(this.openChatCheckbox.state() == State.CHECKED);
      tool.setLastHitWarn(this.lastHitWarnCheckbox.state() == State.CHECKED);
      tool.setSound(this.soundDropdown.getSelected());
      tool.setLastSound(this.lastSoundDropdown.getSelected());
      onSubmit.accept(tool);
    }));
    super.buttons.add(SimplePopupButton.cancel());
    super.widgetFunction = document ->
        document.addChild(this.buildOptionArea());
  }

  private VerticalListWidget<FlexibleContentWidget> buildOptionArea() {
    VerticalListWidget<FlexibleContentWidget> container = new VerticalListWidget<>();
    container.addId("config-container");

    container.addChild(this.buildTypeSelectionArea());
    container.addChild(this.buildSliderArea(
        this.tool.getWarnAt(),
        slider -> this.warnAtSlider = slider
    ));
    container.addChild(this.buildBooleanOptionArea());
    container.addChild(this.buildSoundOptionArea());

    return container;
  }

  private FlexibleContentWidget buildTypeSelectionArea() {
    FlexibleContentWidget typeSelectionArea = new FlexibleContentWidget();
    typeSelectionArea.addId("config-area");
    typeSelectionArea.addId("type-selection-area");

    typeSelectionArea.addContent(this.buildCheckboxContainer(
        "enabled",
        this.tool.isEnabled(),
        (box) -> this.enabledCheckbox = box
    ));
    typeSelectionArea.addContent(this.buildDropdownContainer(
        "type",
        "type",
        WarnTool.Type.values(),
        this.tool.getType(),
        (dropdown) -> this.toolTypeDropdown = dropdown
    ));

    return typeSelectionArea;
  }

  private FlexibleContentWidget buildSliderArea(int initialValue, Consumer<SliderWidget> consumer) {
    FlexibleContentWidget sliderArea = new FlexibleContentWidget();
    sliderArea.addId("config-area");
    sliderArea.addId("slider-area");

    ComponentWidget sliderLabel = ComponentWidget.i18n(
        "toolwarn.ui.popup.settings.component.warnAt");
    sliderLabel.addId("slider-label-warn-at");

    SliderWidget slider = new SliderWidget();
    slider.addId("slider-warn-at");
    slider.range(1, 25);
    slider.setValue(initialValue);

    sliderArea.addContent(sliderLabel);
    sliderArea.addContent(slider);

    consumer.accept(slider);
    return sliderArea;
  }

  private FlexibleContentWidget buildBooleanOptionArea() {
    FlexibleContentWidget booleanOptionArea = new FlexibleContentWidget();
    booleanOptionArea.addId("config-area");
    booleanOptionArea.addId("boolean-option-area");

    booleanOptionArea.addContent(this.buildCheckboxContainer(
        "openChat",
        this.tool.openChat(),
        (box) -> this.openChatCheckbox = box
    ));
    booleanOptionArea.addContent(this.buildCheckboxContainer(
        "lastHitWarning",
        this.tool.lastHitWarn(),
        (box) -> this.lastHitWarnCheckbox = box
    ));

    return booleanOptionArea;
  }

  private FlexibleContentWidget buildSoundOptionArea() {
    FlexibleContentWidget soundOptionArea = new FlexibleContentWidget();
    soundOptionArea.addId("config-area");
    soundOptionArea.addId("sound-option-area");

    soundOptionArea.addContent(this.buildDropdownContainer(
        "warnSound",
        "sound",
        WarnSound.values(),
        this.tool.getSound(),
        (box) -> this.soundDropdown = box
    ));
    soundOptionArea.addContent(this.buildDropdownContainer(
        "lastWarnSound",
        "sound",
        WarnSound.values(),
        this.tool.getLastSound(),
        (box) -> this.lastSoundDropdown = box
    ));

    return soundOptionArea;
  }

  private FlexibleContentWidget buildCheckboxContainer(String name, boolean initialValue,
      Consumer<CheckBoxWidget> consumer) {
    FlexibleContentWidget checkboxArea = new FlexibleContentWidget();

    String id = TextFormat.CAMEL_CASE.toDashCase(name);
    ComponentWidget checkboxLabel = ComponentWidget.i18n(
        "toolwarn.ui.popup.settings.component." + name);
    checkboxLabel.addId("checkbox-label-" + id);

    CheckBoxWidget checkbox = new CheckBoxWidget();
    checkbox.addId("checkbox-" + id);
    checkbox.setState(initialValue ? State.CHECKED : State.UNCHECKED);

    checkboxArea.addContent(checkboxLabel);
    checkboxArea.addContent(checkbox);

    consumer.accept(checkbox);
    return checkboxArea;
  }

  private <T> FlexibleContentWidget buildDropdownContainer(
      String name,
      String dropdownKey,
      T[] values,
      T initialValue,
      Consumer<DropdownWidget<T>> consumer
  ) {
    FlexibleContentWidget dropdownArea = new FlexibleContentWidget();

    String id = TextFormat.CAMEL_CASE.toDashCase(name);
    ComponentWidget dropdownLabel = ComponentWidget.i18n(
        "toolwarn.ui.popup.settings.component." + name);
    dropdownLabel.addId("dropdown-label-" + id);

    DropdownWidget<T> dropdown = new DropdownWidget<>();
    dropdown.addId("dropdown-" + id);
    for (T value : values) {
      dropdown.add(value);
    }
    dropdown.setTranslationKeyPrefix("toolwarn.ui.popup.settings.dropdown." + dropdownKey);
    dropdown.setSelected(initialValue);

    dropdownArea.addContent(dropdownLabel);
    dropdownArea.addContent(dropdown);

    consumer.accept(dropdown);
    return dropdownArea;
  }
}
