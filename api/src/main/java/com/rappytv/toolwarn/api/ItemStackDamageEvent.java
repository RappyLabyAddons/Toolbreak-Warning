package com.rappytv.toolwarn.api;

import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

public record ItemStackDamageEvent(
    @NotNull ItemStack itemStack,
    int oldDurability,
    int newDurability,
    boolean fromPacket
) implements Event {

}
