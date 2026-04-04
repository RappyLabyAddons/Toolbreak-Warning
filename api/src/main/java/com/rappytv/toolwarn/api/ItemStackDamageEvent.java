package com.rappytv.toolwarn.api;

import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Event;

public record ItemStackDamageEvent(ItemStack itemStack, int oldDurability, int newDurability) implements Event {

}
