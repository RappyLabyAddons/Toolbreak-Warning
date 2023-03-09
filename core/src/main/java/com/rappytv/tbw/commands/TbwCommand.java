package com.rappytv.tbw.commands;

import com.rappytv.tbw.TbwAddon;
import com.rappytv.tbw.TbwConfiguration;
import com.rappytv.tbw.util.ToolType;
import com.rappytv.tbw.util.Util;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.world.item.ItemStack;

public class TbwCommand extends Command {

    private final TbwConfiguration config;

    public TbwCommand(TbwAddon addon) {
        super("tbw");
        this.config = addon.configuration();
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if(arguments.length < 1) {
            Util.msg(Util.getTranslation("tbw.command.invalidSubcommand"), true);
            return true;
        } else if(arguments[0].equalsIgnoreCase("disable")) {
            config.enabled().set(false);

            Util.msg(Util.getTranslation("tbw.command.addonDisabled"), true);
        } else if(arguments[0].equalsIgnoreCase("format")) {
            if(arguments.length < 2) {
                Util.msg(Util.getTranslation("tbw.command.onOff"), true);
                return true;
            }

            if(arguments[1].equalsIgnoreCase("on")) {
                config.format().set(true);

                Util.msg(Util.getTranslation("tbw.command.formattingEnabled"), true);
            } else if(arguments[1].equalsIgnoreCase("off")) {
                config.format().set(false);

                Util.msg(Util.getTranslation("tbw.command.formattingDisabled"), true);
            } else
                Util.msg(Util.getTranslation("tbw.command.onOff"), true);

        } else if(arguments[0].equalsIgnoreCase("debug")) {
            if(arguments.length < 2) {
                Util.msg(Util.getTranslation("tbw.command.onOnceOff"), true);
                return true;
            }

            if(arguments[1].equalsIgnoreCase("on")) {
                config.debug().set(true);

                Util.msg(Util.getTranslation("tbw.command.debugEnabled"), true);
            } else if(arguments[1].equalsIgnoreCase("once")) {
                ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
                if(player == null) return true;
                ItemStack itemStack = player.getMainHandItemStack();
                if(itemStack == null) return true;
                Util.msg(Util.getTranslation("tbw.command.debugOnce"), true);
                ToolType toolType = ToolType.getByItem(itemStack);

                if(toolType == ToolType.None)
                    Util.msg(Util.getTranslation("tbw.command.noEventTriggered"), false);
                else {
                    int itemWarnInt = (toolType.getWarnPercentage(config) * itemStack.getMaximumDamage()) / 100;
                    int itemUsedInt = itemStack.getMaximumDamage() - itemStack.getCurrentDamageValue();

                    Util.msg(Util.getTranslation("tbw.messages.debug", toolType.displayName(), (config.format().get() ? Util.formatNumber(itemStack.getMaximumDamage()) : itemStack.getMaximumDamage()), (config.format().get() ? Util.formatNumber(itemUsedInt) : itemUsedInt), (config.format().get() ? Util.formatNumber(itemWarnInt) : itemWarnInt)), false);
                }
            } else if(arguments[1].equalsIgnoreCase("off")) {
                config.debug().set(false);
                Util.msg(Util.getTranslation("tbw.command.debugDisabled"), true);
            } else {
                Util.msg(Util.getTranslation("tbw.command.onOnceOff"), true);
            }
        } else if(arguments[0].equalsIgnoreCase("config")) {
            String yes = "§a" + Util.getTranslation("tbw.command.yes");
            String no = "§c" + Util.getTranslation("tbw.command.no");

            Util.msg(Util.getTranslation("tbw.command.config",
                Util.getTranslation("tbw.settings.enabled.name"),
                config.enabled().get() ? yes : no,
                Util.getTranslation("tbw.settings.format.name"),
                config.format().get() ? yes : no,
                Util.getTranslation("tbw.settings.debug.name"),
                config.debug().get() ? yes : no,
                Util.getTranslation("tbw.settings.lastHit.name"),
                config.lastHit().get() ? yes : no,
                Util.getTranslation("tbw.settings.swordPercentage.name"),
                "§b" + config.swordPercentage().get() + "%",
                Util.getTranslation("tbw.settings.pickaxePercentage.name"),
                "§b" + config.pickAxePercentage().get() + "%",
                Util.getTranslation("tbw.settings.axePercentage.name"),
                "§b" + config.axePercentage().get() + "%",
                Util.getTranslation("tbw.settings.shovelPercentage.name"),
                "§b" + config.shovelPercentage().get() + "%"
            ), false);
        } else {
            Util.msg(Util.getTranslation("tbw.command.invalidSubcommand"), true);
        }
        return true;
    }
}
