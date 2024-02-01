package com.rappytv.toolwarn.ui;

import com.rappytv.toolwarn.TbwAddon;
import net.labymod.api.Laby;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget.State;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.render.font.TextColorStripper;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Link("config.lss")
@AutoActivity
public class ToolConfigActivity extends SimpleActivity {
    private static final Pattern NAME_PATTERN = Pattern.compile("[\\w]{0,16}");
    private static final TextColorStripper TEXT_COLOR_STRIPPER = Laby.references()
        .textColorStripper();

    private final TbwAddon addon;
    private final VerticalListWidget<ToolWidget> toolList;
    private final Map<String, ToolWidget> toolWidgets;

    private ToolWidget selectedTool;

    private ButtonWidget removeButton;
    private ButtonWidget editButton;

    private FlexibleContentWidget inputWidget;
    private String lastUserName;
    private String lastCustomName;

    private Action action;

    public ToolConfigActivity() {
        this.addon = TbwAddon.get();

        this.toolWidgets = new HashMap<>();
        this.addon.configuration().getTools().forEach((toolType, customTag) -> {
            this.toolWidgets.put(toolType, new ToolWidget(toolType, customTag));
        });

        this.toolList = new VerticalListWidget<>();
        this.toolList.addId("name-tag-list");
        this.toolList.setSelectCallback(nameTagWidget -> {
            NameTagWidget selectedNameTag = this.toolList.session().getSelectedEntry();
            if (selectedNameTag == null
                || selectedNameTag.getCustomTag() != nameTagWidget.getCustomTag()) {
                this.editButton.setEnabled(true);
                this.removeButton.setEnabled(true);
            }
        });

        this.toolList.setDoubleClickCallback(nameTagWidget -> this.setAction(Action.EDIT));
    }

    @Override
    public void initialize(Parent parent) {
        super.initialize(parent);

        FlexibleContentWidget container = new FlexibleContentWidget();
        container.addId("name-tag-container");
        for (NameTagWidget nameTagWidget : this.toolWidgets.values()) {
            this.toolList.addChild(nameTagWidget);
        }

        container.addFlexibleContent(new ScrollWidget(this.toolList));

        this.selectedTool = this.toolList.session().getSelectedEntry();
        HorizontalListWidget menu = new HorizontalListWidget();
        menu.addId("overview-button-menu");

        menu.addEntry(ButtonWidget.i18n("labymod.ui.button.add", () -> this.setAction(Action.ADD)));

        this.editButton = ButtonWidget.i18n("labymod.ui.button.edit",
            () -> this.setAction(Action.EDIT));
        this.editButton.setEnabled(this.selectedTool != null);
        menu.addEntry(this.editButton);

        this.removeButton = ButtonWidget.i18n("labymod.ui.button.remove",
            () -> this.setAction(Action.REMOVE));
        this.removeButton.setEnabled(this.selectedTool != null);
        menu.addEntry(this.removeButton);

        container.addContent(menu);
        this.document().addChild(container);
        if (this.action == null) {
            return;
        }

        DivWidget manageContainer = new DivWidget();
        manageContainer.addId("manage-container");

        Widget overlayWidget;
        switch (this.action) {
            default:
            case ADD:
                NameTagWidget newCustomNameTag = new NameTagWidget("", CustomNameTag.createDefault());
                overlayWidget = this.initializeManageContainer(newCustomNameTag);
                break;
            case EDIT:
                overlayWidget = this.initializeManageContainer(this.selectedTool);
                break;
            case REMOVE:
                overlayWidget = this.initializeRemoveContainer(this.selectedTool);
                break;
        }

        manageContainer.addChild(overlayWidget);
        this.document().addChild(manageContainer);
    }

    private FlexibleContentWidget initializeRemoveContainer(NameTagWidget nameTagWidget) {
        this.inputWidget = new FlexibleContentWidget();
        this.inputWidget.addId("remove-container");

        ComponentWidget confirmationWidget = ComponentWidget.i18n(
            "customnametags.gui.manage.remove.title");
        confirmationWidget.addId("remove-confirmation");
        this.inputWidget.addContent(confirmationWidget);

        NameTagWidget previewWidget = new NameTagWidget(nameTagWidget.getUserName(),
            nameTagWidget.getCustomTag());
        previewWidget.addId("remove-preview");
        this.inputWidget.addContent(previewWidget);

        HorizontalListWidget menu = new HorizontalListWidget();
        menu.addId("remove-button-menu");

        menu.addEntry(ButtonWidget.i18n("labymod.ui.button.remove", () -> {
            this.addon.configuration().getCustomTags().remove(nameTagWidget.getUserName());
            this.toolWidgets.remove(nameTagWidget.getUserName());
            this.toolList.session().setSelectedEntry(null);
            this.setAction(null);
            this.updateRequired = true;
        }));

        menu.addEntry(ButtonWidget.i18n("labymod.ui.button.cancel", () -> this.setAction(null)));
        this.inputWidget.addContent(menu);

        return this.inputWidget;
    }

    private DivWidget initializeManageContainer(NameTagWidget nameTagWidget) {
        TextFieldWidget customTextField = new TextFieldWidget();
        ButtonWidget doneButton = ButtonWidget.i18n("labymod.ui.button.done");

        DivWidget inputContainer = new DivWidget();
        inputContainer.addId("input-container");

        ComponentWidget customNameWidget = ComponentWidget.component(
            nameTagWidget.getCustomTag().displayName());
        customNameWidget.addId("custom-preview");
        inputContainer.addChild(customNameWidget);

        this.inputWidget = new FlexibleContentWidget();
        this.inputWidget.addId("input-list");

        ComponentWidget labelName = ComponentWidget.i18n("customnametags.gui.manage.name");
        labelName.addId("label-name");
        this.inputWidget.addContent(labelName);

        HorizontalListWidget nameList = new HorizontalListWidget();
        nameList.addId("input-name-list");

        IconWidget iconWidget = new IconWidget(
            nameTagWidget.getIconWidget(nameTagWidget.getUserName()));
        iconWidget.addId("input-avatar");
        nameList.addEntry(iconWidget);

        TextFieldWidget nameTextField = new TextFieldWidget();
        nameTextField.maximalLength(16);
        nameTextField.setText(nameTagWidget.getUserName());
        nameTextField.validator(newValue -> NAME_PATTERN.matcher(newValue).matches());
        nameTextField.updateListener(newValue -> {
            doneButton.setEnabled(
                !newValue.trim().isEmpty() && !this.getStrippedText(customTextField.getText()).isEmpty()
            );
            if (newValue.equals(this.lastUserName)) {
                return;
            }

            this.lastUserName = newValue;
            iconWidget.icon().set(nameTagWidget.getIconWidget(newValue));
        });

        nameList.addEntry(nameTextField);
        this.inputWidget.addContent(nameList);

        ComponentWidget labelCustomName = ComponentWidget.i18n("customnametags.gui.manage.custom.name");
        labelCustomName.addId("label-name");
        this.inputWidget.addContent(labelCustomName);

        HorizontalListWidget customNameList = new HorizontalListWidget();
        customNameList.addId("input-name-list");

        DivWidget placeHolder = new DivWidget();
        placeHolder.addId("input-avatar");
        customNameList.addEntry(placeHolder);

        customTextField.maximalLength(64);
        customTextField.setText(nameTagWidget.getCustomTag().getCustomName());
        customTextField.updateListener(newValue -> {
            doneButton.setEnabled(
                !this.getStrippedText(newValue).isEmpty() && !nameTextField.getText().trim().isEmpty()
            );
            if (newValue.equals(this.lastCustomName)) {
                return;
            }

            this.lastCustomName = newValue;
            customNameWidget.setComponent(
                LegacyComponentSerializer.legacyAmpersand().deserialize(newValue));
        });

        customNameList.addEntry(customTextField);
        this.inputWidget.addContent(customNameList);

        HorizontalListWidget checkBoxList = new HorizontalListWidget();
        checkBoxList.addId("checkbox-list");

        DivWidget enabledDiv = new DivWidget();
        enabledDiv.addId("checkbox-div");

        ComponentWidget enabledText = ComponentWidget.i18n("customnametags.gui.manage.enabled.name");
        enabledText.addId("checkbox-name");
        enabledDiv.addChild(enabledText);

        CheckBoxWidget enabledWidget = new CheckBoxWidget();
        enabledWidget.addId("checkbox-item");
        enabledWidget.setState(
            nameTagWidget.getCustomTag().isEnabled() ? State.CHECKED : State.UNCHECKED);
        enabledDiv.addChild(enabledWidget);
        checkBoxList.addEntry(enabledDiv);

        DivWidget replaceDiv = new DivWidget();
        replaceDiv.addId("checkbox-div");

        ComponentWidget replaceText = ComponentWidget.i18n("customnametags.gui.manage.replace.name");
        replaceText.addId("checkbox-name");
        replaceDiv.addChild(replaceText);

        CheckBoxWidget replaceWidget = new CheckBoxWidget();
        replaceWidget.addId("checkbox-item");
        replaceWidget.setState(
            nameTagWidget.getCustomTag().isReplaceScoreboard() ? State.CHECKED : State.UNCHECKED);
        replaceDiv.addChild(replaceWidget);
        checkBoxList.addEntry(replaceDiv);
        this.inputWidget.addContent(checkBoxList);

        HorizontalListWidget buttonList = new HorizontalListWidget();
        buttonList.addId("edit-button-menu");

        doneButton.setEnabled(
            !nameTextField.getText().trim().isEmpty() && !this.getStrippedText(
                customTextField.getText()).isEmpty()
        );
        doneButton.setPressable(() -> {
            if (nameTagWidget.getUserName().length() == 0) {
                this.toolWidgets.put(nameTextField.getText(), nameTagWidget);
                this.toolList.session().setSelectedEntry(nameTagWidget);
            }

            this.addon.configuration().getCustomTags().remove(nameTagWidget.getUserName());
            CustomNameTag customNameTag = nameTagWidget.getCustomTag();
            customNameTag.setCustomName(customTextField.getText());
            customNameTag.setEnabled(enabledWidget.state() == State.CHECKED);
            customNameTag.setReplaceScoreboard(replaceWidget.state() == State.CHECKED);
            this.addon.configuration().getCustomTags().put(nameTextField.getText(), customNameTag);
            this.addon.configuration().removeInvalidNameTags();

            nameTagWidget.setUserName(nameTextField.getText());
            nameTagWidget.setCustomTag(customNameTag);
            this.setAction(null);

            this.updateRequired = true;
        });

        buttonList.addEntry(doneButton);

        buttonList.addEntry(ButtonWidget.i18n("labymod.ui.button.cancel", () -> this.setAction(null)));
        inputContainer.addChild(this.inputWidget);
        this.inputWidget.addContent(buttonList);
        return inputContainer;
    }

    private String getStrippedText(String text) {
        text = text.trim();
        if (text.isEmpty()) {
            return text;
        }

        return TEXT_COLOR_STRIPPER.stripColorCodes(text, '&');
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
