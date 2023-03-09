package com.rappytv.tbw.listener;

import com.rappytv.tbw.TbwAddon;
import com.rappytv.tbw.TbwConfiguration;
import com.rappytv.tbw.util.ToolType;
import com.rappytv.tbw.util.Util;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.util.I18n;
import java.util.ArrayList;
import java.util.Arrays;
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
        ClientPlayer player = Laby.labyAPI().minecraft().clientPlayer();
        if(player == null) return;
        ItemStack itemStack = player.getMainHandItemStack();

        if(itemStack == null) return;
        if(itemStack.getMaximumDamage() == 0) return;
        if(!Arrays.asList(GameMode.SURVIVAL, GameMode.ADVENTURE).contains(player.gameMode())) return;

        toolUsed(itemStack, ToolType.getByItem(itemStack));
    }

    public void toolUsed(ItemStack itemStack, ToolType toolType) {
        int itemWarnInt = (toolType.getWarnPercentage(config) * itemStack.getMaximumDamage()) / 100;
        int itemUsedInt = itemStack.getMaximumDamage() - itemStack.getCurrentDamageValue();
        if(config.debug().get())
            Util.msg(Util.getTranslation("tbw.messages.debug", toolType.displayName(), (config.format().get() ? Util.formatNumber(itemStack.getMaximumDamage()) : itemStack.getMaximumDamage()), (config.format().get() ? Util.formatNumber(itemUsedInt) : itemUsedInt), (config.format().get() ? Util.formatNumber(itemWarnInt) : itemWarnInt)), false);

        if(itemUsedInt == itemWarnInt) {
            if(!warns.contains(itemStack)) {
                Laby.labyAPI().minecraft().openChat("");
                Util.msg(I18n.getTranslation("tbw.messages.warning", toolType.getWarnPercentage(config)), true);
                warns.add(itemStack);
            }
        } else if(isLastHit(itemStack)) {
            if(!warns.contains(itemStack)) {
                Laby.labyAPI().minecraft().openChat("");
                Util.msg(Util.getTranslation("tbw.messages.lastHit"), true);
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
