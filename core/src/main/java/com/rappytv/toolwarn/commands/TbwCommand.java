package com.rappytv.toolwarn.commands;

import com.rappytv.toolwarn.TbwAddon;
import com.rappytv.toolwarn.config.TbwConfiguration;
import com.rappytv.toolwarn.util.ToolType;
import com.rappytv.toolwarn.util.Util;
import com.rappytv.toolwarn.util.WarnSound;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.util.I18n;

public class TbwCommand extends Command {

    private final TbwConfiguration config;

    public TbwCommand(TbwAddon addon) {
        super("tbw");
        this.config = addon.configuration();
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if(arguments.length < 1) {
            Util.msg(I18n.translate("toolwarn.command.invalidSubcommand"), true);
        } else if(arguments[0].equalsIgnoreCase("test")) {
            if(arguments.length > 1 && arguments[1].equalsIgnoreCase("last")) {
                if(config.sounds().enabled().get() && config.sounds().lastHitSound().get() != WarnSound.NONE) {
                    Laby.labyAPI().minecraft().sounds().playSound(
                        config.sounds().lastHitSound().get().getResourceLocation(),
                        1f,
                        1f
                    );
                }
            } else {
                if(config.sounds().enabled().get() && config.sounds().warnSound().get() != WarnSound.NONE) {
                    Laby.labyAPI().minecraft().sounds().playSound(
                        config.sounds().warnSound().get().getResourceLocation(),
                        1f,
                        1f
                    );
                }
            }
        } else if(arguments[0].equalsIgnoreCase("disable")) {
            config.enabled().set(false);

            Util.msg(I18n.translate("toolwarn.command.addonDisabled"), true);
        } else if(arguments[0].equalsIgnoreCase("format")) {
            if(arguments.length < 2) {
                Util.msg(I18n.translate("toolwarn.command.onOff"), true);
                return true;
            }

            if(arguments[1].equalsIgnoreCase("on")) {
                config.format().set(true);

                Util.msg(I18n.translate("toolwarn.command.formattingEnabled"), true);
            } else if(arguments[1].equalsIgnoreCase("off")) {
                config.format().set(false);

                Util.msg(I18n.translate("toolwarn.command.formattingDisabled"), true);
            } else
                Util.msg(I18n.translate("toolwarn.command.onOff"), true);

        } else if(arguments[0].equalsIgnoreCase("debug")) {
            if(arguments.length < 2) {
                Util.msg(I18n.translate("toolwarn.command.onOnceOff"), true);
                return true;
            }

            if(arguments[1].equalsIgnoreCase("on")) {
                config.debug().set(true);

                Util.msg(I18n.translate("toolwarn.command.debugEnabled"), true);
            } else if(arguments[1].equalsIgnoreCase("once")) {
                ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
                if(player == null) return true;
                ItemStack itemStack = player.getMainHandItemStack();
                if(itemStack == null) return true;
                Util.msg(I18n.translate("toolwarn.command.debugOnce"), true);
                ToolType toolType = ToolType.getByItem(itemStack);

                if(toolType == ToolType.None)
                    Util.msg(I18n.translate("toolwarn.command.noEventTriggered"), false);
                else {
                    int itemWarnInt = (toolType.getWarnPercentage(config) * itemStack.getMaximumDamage()) / 100;
                    int itemUsedInt = itemStack.getMaximumDamage() - itemStack.getCurrentDamageValue();

                    Util.msg(I18n.translate("toolwarn.messages.debug", toolType.displayName(), (config.format().get() ? Util.formatNumber(itemStack.getMaximumDamage()) : itemStack.getMaximumDamage()), (config.format().get() ? Util.formatNumber(itemUsedInt) : itemUsedInt), (config.format().get() ? Util.formatNumber(itemWarnInt) : itemWarnInt)), false);
                }
            } else if(arguments[1].equalsIgnoreCase("off")) {
                config.debug().set(false);
                Util.msg(I18n.translate("toolwarn.command.debugDisabled"), true);
            } else {
                Util.msg(I18n.translate("toolwarn.command.onOnceOff"), true);
            }
        } else if(arguments[0].equalsIgnoreCase("config")) {
            String yes = "§a" + I18n.translate("toolwarn.command.yes");
            String no = "§c" + I18n.translate("toolwarn.command.no");

            Util.msg(I18n.translate("toolwarn.command.config",
                I18n.translate("toolwarn.settings.enabled.name"),
                config.enabled().get() ? yes : no,
                I18n.translate("toolwarn.settings.format.name"),
                config.format().get() ? yes : no,
                I18n.translate("toolwarn.settings.debug.name"),
                config.debug().get() ? yes : no,
                I18n.translate("toolwarn.settings.lastHit.name"),
                config.lastHit().get() ? yes : no,
                I18n.translate("toolwarn.settings.swordPercentage.name"),
                "§b" + config.swordPercentage().get() + "%",
                I18n.translate("toolwarn.settings.pickaxePercentage.name"),
                "§b" + config.pickAxePercentage().get() + "%",
                I18n.translate("toolwarn.settings.axePercentage.name"),
                "§b" + config.axePercentage().get() + "%",
                I18n.translate("toolwarn.settings.shovelPercentage.name"),
                "§b" + config.shovelPercentage().get() + "%"
            ), false);
        } else {
            Util.msg(I18n.translate("toolwarn.command.invalidSubcommand"), true);
        }
        return true;
    }
}
