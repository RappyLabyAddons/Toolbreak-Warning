package com.rappytv.toolwarn.listener;

import com.rappytv.toolwarn.TbwAddon;
import com.rappytv.toolwarn.config.TbwConfiguration;
import com.rappytv.toolwarn.event.ToolUseEvent;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class ToolUseListener {

    private final TbwAddon addon;
    private final TbwConfiguration config;
    private static final List<ItemStack> warns = new ArrayList<>();

    public ToolUseListener(TbwAddon addon) {
        this.addon = addon;
        this.config = addon.configuration();
    }

    @Subscribe
    public void onToolUse(ToolUseEvent event) {
        System.out.println("Tool use event");
    }
}
