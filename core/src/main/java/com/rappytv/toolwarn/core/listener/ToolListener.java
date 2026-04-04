package com.rappytv.toolwarn.core.listener;

import com.rappytv.toolwarn.api.ItemStackDamageEvent;
import com.rappytv.toolwarn.core.ToolwarnAddon;
import net.labymod.api.event.Subscribe;

public class ToolListener {

    private final ToolwarnAddon addon;

    public ToolListener(ToolwarnAddon addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onItemStackSetDamage(ItemStackDamageEvent event) {
        this.addon.logger().info(String.format(
            "Set durability from %s to %s",
            event.oldDurability(),
            event.newDurability()
        ));
    }

}
