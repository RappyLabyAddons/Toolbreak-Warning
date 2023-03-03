package com.rappytv.tbw.commands;

import com.rappytv.tbw.TbwAddon;
import net.labymod.api.client.chat.command.Command;

public class TbwCommand extends Command {

    private final TbwAddon addon;

    public TbwCommand(TbwAddon addon) {
        super("tbw");
        this.addon = addon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        this.displayMessage(TbwAddon.prefix + "§b/tbw §7was executed!");
        return false;
    }
}
