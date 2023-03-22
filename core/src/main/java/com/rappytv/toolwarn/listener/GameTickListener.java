package com.rappytv.toolwarn.listener;

import com.rappytv.toolwarn.TbwAddon;
import com.rappytv.toolwarn.TbwConfiguration;
import com.rappytv.toolwarn.util.ToolType;
import com.rappytv.toolwarn.util.Util;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.util.I18n;
import java.util.ArrayList;
import java.util.List;

public class GameTickListener {

    private final TbwAddon addon;
    private final TbwConfiguration config;
    private static final List<ItemStack> warns = new ArrayList<>();

    public GameTickListener(TbwAddon addon) {
        this.addon = addon;
        this.config = addon.configuration();
    }

    @Subscribe
    public void onTick(GameTickEvent event) {
        if(!addon.configuration().enabled().get()) return;
        ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
        if(player == null) return;
        ItemStack itemStack = player.getMainHandItemStack();

        if(itemStack == null) return;
        if(itemStack.getMaximumDamage() == 0) return;
        if(player.gameMode() != GameMode.SURVIVAL && player.gameMode() != GameMode.ADVENTURE) return;

        toolUsed(itemStack, ToolType.getByItem(itemStack));
    }

    public void toolUsed(ItemStack itemStack, ToolType toolType) {
        int itemWarnInt = (toolType.getWarnPercentage(config) * itemStack.getMaximumDamage()) / 100;
        int itemUsedInt = itemStack.getMaximumDamage() - itemStack.getCurrentDamageValue();
        if(config.debug().get())
            Util.msg(Util.getTranslation("toolwarn.messages.debug", toolType.displayName(), (config.format().get() ? Util.formatNumber(itemStack.getMaximumDamage()) : itemStack.getMaximumDamage()), (config.format().get() ? Util.formatNumber(itemUsedInt) : itemUsedInt), (config.format().get() ? Util.formatNumber(itemWarnInt) : itemWarnInt)), false);

        if(itemUsedInt == itemWarnInt) {
            if(!warns.contains(itemStack)) {
                Laby.labyAPI().minecraft().openChat("");
                Util.msg(I18n.getTranslation("toolwarn.messages.warning", toolType.getWarnPercentage(config)), true);
                warns.add(itemStack);
            }
        } else if(isLastHit(itemStack)) {
            if(!warns.contains(itemStack)) {
                Laby.labyAPI().minecraft().openChat("");
                Util.msg(Util.getTranslation("toolwarn.messages.lastHit"), true);
                warns.add(itemStack);
            }
        } else {
            warns.remove(itemStack);
        }
    }

    public boolean isLastHit(ItemStack i) {
        if (!addon.configuration().lastHit().get()) return false;
        return (i.getMaximumDamage() - i.getCurrentDamageValue()) == 1;
    }
}
