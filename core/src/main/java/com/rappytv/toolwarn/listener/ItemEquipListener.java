package com.rappytv.toolwarn.listener;

import com.rappytv.toolwarn.TbwAddon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientHotbarSlotChangeEvent;

public class ItemEquipListener {

    private final TbwAddon addon;

    public ItemEquipListener(TbwAddon addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onItemEquip(ClientHotbarSlotChangeEvent event) {

    }

}
