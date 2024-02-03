package com.rappytv.toolwarn.listener;

import com.rappytv.toolwarn.TbwAddon;
import com.rappytv.toolwarn.config.TbwConfiguration;
import com.rappytv.toolwarn.util.Util;
import com.rappytv.toolwarn.util.WarnSound;
import com.rappytv.toolwarn.util.WarnTool.Type;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
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

        toolUsed(itemStack, Type.getByItem(itemStack));
    }

    public void toolUsed(ItemStack itemStack, Type toolType) {
        if(toolType == Type.NONE) return;
        if(Laby.labyAPI().minecraft().minecraftWindow().isScreenOpened()) return;

        int itemWarnInt = (toolType.getWarnPercentage(config) * itemStack.getMaximumDamage()) / 100;
        int itemUsedInt = itemStack.getMaximumDamage() - itemStack.getCurrentDamageValue();

        if(itemUsedInt == itemWarnInt) {
            if(!warns.contains(itemStack)) {
                if(this.config.openChat().get()) Laby.labyAPI().minecraft().openChat("");
                Util.msg(Component.translatable("toolwarn.messages.warning", NamedTextColor.RED, Component.text(toolType.getWarnPercentage(config))), true);
                warns.add(itemStack);

                if(config.sounds().enabled().get() && config.sounds().warnSound().get() != WarnSound.NONE) {
                    Laby.labyAPI().minecraft().sounds().playSound(
                        config.sounds().warnSound().get().getResourceLocation(),
                        1f,
                        1f
                    );
                }
            }
        } else if(isLastHit(itemStack)) {
            if(!warns.contains(itemStack)) {
                if(this.config.openChat().get()) Laby.labyAPI().minecraft().openChat("");
                Util.msg(Component.translatable("toolwarn.messages.lastHit", NamedTextColor.RED), true);
                warns.add(itemStack);

                if(config.sounds().enabled().get() && config.sounds().lastHitSound().get() != WarnSound.NONE) {
                    Laby.labyAPI().minecraft().sounds().playSound(
                        config.sounds().lastHitSound().get().getResourceLocation(),
                        1f,
                        1f
                    );
                }
            }
        } else {
            warns.remove(itemStack);
        }
    }

    public boolean isLastHit(ItemStack i) {
        if (!addon.configuration().lastHit().get()) return false;
        return (i.getMaximumDamage() - i.getCurrentDamageValue()) <= 1;
    }
}
