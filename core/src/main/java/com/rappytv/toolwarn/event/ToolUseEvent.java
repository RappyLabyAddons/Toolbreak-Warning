package com.rappytv.toolwarn.event;

import com.rappytv.toolwarn.util.WarnTool;
import com.rappytv.toolwarn.util.WarnTool.Type;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Event;

public class ToolUseEvent implements Event {

    private final ItemStack itemStack;
    private final WarnTool.Type type;

    public ToolUseEvent(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.type = WarnTool.Type.getByItem(itemStack);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    public Type getType() {
        return type;
    }
}
