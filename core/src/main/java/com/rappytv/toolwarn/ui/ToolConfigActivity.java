package com.rappytv.toolwarn.ui;

import com.rappytv.toolwarn.TbwAddon;
import com.rappytv.toolwarn.util.WarnSound;
import com.rappytv.toolwarn.util.WarnTool;
import com.rappytv.toolwarn.util.WarnTool.Type;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget.State;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
@Links({@Link("manage.lss"), @Link("config.lss")})
@AutoActivity
public class ToolConfigActivity extends Activity {

    private final TbwAddon addon;
    private final VerticalListWidget<ToolWidget> toolList;
    private final List<ToolWidget> toolWidgets;
    private int sliderValue;

    private ToolWidget selectedTool;

    private ButtonWidget removeButton;
    private ButtonWidget editButton;

    private FlexibleContentWidget inputWidget;

    private Action action;

    public ToolConfigActivity() {
        this.addon = TbwAddon.get();

        this.toolWidgets = new ArrayList<>();
        this.addon.configuration().getTools().forEach((tool) ->
            this.toolWidgets.add(new ToolWidget(tool))
        );

        this.toolList = new VerticalListWidget<>()
            .addId("tool-list");
        this.toolList.setSelectCallback(nameTagWidget -> {
            ToolWidget selectedNameTag = this.toolList.session().getSelectedEntry();
            if (selectedNameTag == null || selectedNameTag.getTool() != nameTagWidget.getTool()) {
                this.editButton.setEnabled(true);
                this.removeButton.setEnabled(true);
            }
        });

        this.toolList.setDoubleClickCallback(nameTagWidget -> this.setAction(Action.EDIT));
    }

    @Override
    public void initialize(Parent parent) {
        super.initialize(parent);

        FlexibleContentWidget container = new FlexibleContentWidget()
            .addId("tool-container");
        for (ToolWidget nameTagWidget : this.toolWidgets) {
            this.toolList.addChild(nameTagWidget);
        }
        this.selectedTool = this.toolList.session().getSelectedEntry();

        HorizontalListWidget buttons = new HorizontalListWidget()
            .addId("overview-button-menu");
        this.editButton = ButtonWidget.i18n("labymod.ui.button.edit", () ->
            this.setAction(Action.EDIT)
        );
        this.editButton.setEnabled(this.selectedTool != null);
        this.removeButton = ButtonWidget.i18n("labymod.ui.button.remove", () ->
            this.setAction(Action.REMOVE)
        );
        this.removeButton.setEnabled(this.selectedTool != null);

        buttons.addEntry(ButtonWidget.i18n("labymod.ui.button.add", () ->
            this.setAction(Action.ADD))
        );
        buttons.addEntry(this.editButton);
        buttons.addEntry(this.removeButton);

        container.addFlexibleContent(new ScrollWidget(this.toolList));
        container.addContent(buttons);
        this.document().addChild(container);

        if(this.action == null) return;

        Widget overlayWidget = switch (this.action) {
            default -> {
                ToolWidget newCustomNameTag = new ToolWidget(new WarnTool());
                yield this.initializeManageContainer(newCustomNameTag);
            }
            case EDIT -> this.initializeManageContainer(this.selectedTool);
            case REMOVE -> this.initializeRemoveContainer(this.selectedTool);
        };

        DivWidget manageContainer = new DivWidget()
            .addId("manage-container");
        manageContainer.addChild(overlayWidget);
        this.document().addChild(manageContainer);
    }

    private FlexibleContentWidget initializeRemoveContainer(ToolWidget toolWidget) {
        this.inputWidget = new FlexibleContentWidget()
            .addId("remove-container");

        ComponentWidget confirmationWidget = ComponentWidget.i18n("toolwarn.gui.remove")
            .addId("remove-confirmation");

        ToolWidget previewWidget = new ToolWidget(toolWidget.getTool());
        previewWidget.addId("remove-preview");

        HorizontalListWidget menu = new HorizontalListWidget();
        menu.addId("remove-button-menu");

        menu.addEntry(ButtonWidget.i18n("labymod.ui.button.remove", () -> {
            this.addon.configuration().getTools().remove(toolWidget.getTool());
            this.toolWidgets.remove(toolWidget);
            this.toolList.session().setSelectedEntry(null);
            this.setAction(null);
        }));
        menu.addEntry(ButtonWidget.i18n("labymod.ui.button.cancel", () -> this.setAction(null)));

        this.inputWidget.addContent(confirmationWidget);
        this.inputWidget.addContent(previewWidget);
        this.inputWidget.addContent(menu);

        return this.inputWidget;
    }

    private DivWidget initializeManageContainer(ToolWidget toolWidget) {
        ButtonWidget doneButton = ButtonWidget.i18n("labymod.ui.button.done");

        DivWidget inputContainer = new DivWidget()
            .addId("input-container");

        IconWidget toolIconWidget = new IconWidget(toolWidget.getTool().getType().getIcon())
            .addId("tool-icon");

        DropdownWidget<WarnTool.Type> typeDropdown = new DropdownWidget<>()
            .addId("type-dropdown");
        for(WarnTool.Type type : WarnTool.Type.values()) {
            if(type == Type.NONE) continue;
            typeDropdown.add(type);
        }
        typeDropdown.setTranslationKeyPrefix("toolwarn.gui.dropdown.type");
        typeDropdown.setSelected(toolWidget.getTool().getType());
        typeDropdown.setChangeListener((type) -> {
            // Update toolIcon to type.getIcon()
        });

        ComponentWidget sliderText = ComponentWidget.i18n("toolwarn.gui.slider")
            .addId("slider-name");

        SliderWidget warnSlider = new SliderWidget(value -> sliderValue = (int) value)
            .addId("warn-slider");
        warnSlider.range(1, 25);
        warnSlider.setValue(toolWidget.getTool().getWarnAt());

        DivWidget soundDiv = new DivWidget()
            .addId("dropdown-div");
        DivWidget lastSoundDiv = new DivWidget()
            .addId("dropdown-div");

        ComponentWidget soundText = ComponentWidget.i18n("toolwarn.gui.dropdown.warnSound")
            .addId("dropdown-name");

        DropdownWidget<WarnSound> soundDropdown = new DropdownWidget<>()
            .addId("dropdown-item");
        soundDropdown.setTranslationKeyPrefix("toolwarn.gui.dropdown.sound");

        ComponentWidget lastSoundText = ComponentWidget.i18n("toolwarn.gui.dropdown.lastSound")
            .addId("dropdown-name");

        DropdownWidget<WarnSound> lastSoundDropdown = new DropdownWidget<>()
            .addId("dropdown-item");
        lastSoundDropdown.setTranslationKeyPrefix("toolwarn.gui.dropdown.sound");

        soundDiv.addChild(soundText);
        soundDiv.addChild(soundDropdown);
        lastSoundDiv.addChild(lastSoundText);
        lastSoundDiv.addChild(lastSoundDropdown);

        for(WarnSound sound : WarnSound.values()) {
            soundDropdown.add(sound);
            lastSoundDropdown.add(sound);
        }
        soundDropdown.setSelected(toolWidget.getTool().getSound());
        lastSoundDropdown.setSelected(toolWidget.getTool().getLastSound());

        DivWidget openChatDiv = new DivWidget()
            .addId("checkbox-div");
        DivWidget lastHitDiv = new DivWidget()
            .addId("checkbox-div");

        ComponentWidget openChatText = ComponentWidget.i18n("toolwarn.gui.checkbox.openChat")
            .addId("checkbox-name");

        CheckBoxWidget openChatCheck = new CheckBoxWidget()
            .addId("checkbox-item");
        openChatCheck.setState(toolWidget.getTool().openChat() ? State.CHECKED : State.UNCHECKED);

        ComponentWidget lastHitText = ComponentWidget.i18n("toolwarn.gui.checkbox.lastHit")
            .addId("checkbox-name");

        CheckBoxWidget lastHitCheck = new CheckBoxWidget()
            .addId("checkbox-item");
        lastHitCheck.setState(toolWidget.getTool().lastHitWarn() ? State.CHECKED : State.UNCHECKED);

        openChatDiv.addChild(openChatText);
        openChatDiv.addChild(openChatCheck);
        lastHitDiv.addChild(lastHitText);
        lastHitDiv.addChild(lastHitCheck);

        this.inputWidget = new FlexibleContentWidget();
        this.inputWidget.addId("input-list");

        HorizontalListWidget dropdownList = new HorizontalListWidget()
            .addId("dropdown-list");
        dropdownList.addEntry(soundDiv);
        dropdownList.addEntry(lastSoundDiv);

        HorizontalListWidget checkBoxList = new HorizontalListWidget()
            .addId("checkbox-list");
        checkBoxList.addEntry(openChatDiv);
        checkBoxList.addEntry(lastHitDiv);

        inputWidget.addContent(toolIconWidget);
        inputWidget.addContent(typeDropdown);
        inputWidget.addContent(sliderText);
        inputWidget.addContent(warnSlider);
        inputWidget.addContent(dropdownList);
        inputWidget.addContent(checkBoxList);

        HorizontalListWidget buttonList = new HorizontalListWidget()
            .addId("edit-button-menu");

        doneButton.setEnabled(true);
        doneButton.setPressable(() -> {
            WarnTool tool = toolWidget.getTool();
            tool.setType(typeDropdown.getSelected());
            tool.setWarnAt(sliderValue);
            tool.setSound(soundDropdown.getSelected());
            tool.setLastSound(lastSoundDropdown.getSelected());
            tool.setOpenChat(openChatCheck.state() == State.CHECKED);
            tool.setLastHitWarn(lastHitCheck.state() == State.CHECKED);
            this.addon.configuration().getTools().remove(toolWidget.getTool());
            this.addon.configuration().getTools().add(tool);
            this.addon.configuration().removeInvalidTools();

            toolWidget.setTool(tool);
            this.setAction(null);
            reload();
        });

        buttonList.addEntry(doneButton);
        buttonList.addEntry(ButtonWidget.i18n("labymod.ui.button.cancel", () -> this.setAction(null)));
        this.inputWidget.addContent(buttonList);

        inputContainer.addChild(this.inputWidget);
        return inputContainer;
    }

    @Override
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        try {
            if (this.action != null) {
                return this.inputWidget.mouseClicked(mouse, mouseButton);
            }

            return super.mouseClicked(mouse, mouseButton);
        } finally {
            this.selectedTool = this.toolList.session().getSelectedEntry();
            this.removeButton.setEnabled(this.selectedTool != null);
            this.editButton.setEnabled(this.selectedTool != null);
        }
    }

    @Override
    public boolean keyPressed(Key key, InputType type) {
        if (key.getId() == 256 && this.action != null) {
            this.setAction(null);
            return true;
        }

        return super.keyPressed(key, type);
    }

    private void setAction(Action action) {
        this.action = action;
        this.reload();
    }

    @Override
    public <T extends LabyScreen> @Nullable T renew() {
        return null;
    }

    private enum Action {
        ADD, EDIT, REMOVE
    }
}
