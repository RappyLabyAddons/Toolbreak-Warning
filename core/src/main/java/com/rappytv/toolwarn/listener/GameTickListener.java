package com.rappytv.toolwarn.listener;

import com.rappytv.toolwarn.TbwAddon;
import com.rappytv.toolwarn.config.TbwConfiguration;
import com.rappytv.toolwarn.util.Util;
import com.rappytv.toolwarn.util.WarnSound;
import com.rappytv.toolwarn.util.WarnTool;
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

        checkForWarn(itemStack, Type.getByItem(itemStack));
    }

    private void checkForWarn(ItemStack itemStack, WarnTool.Type type) {
        if(type == Type.NONE) return;
        if(Laby.labyAPI().minecraft().minecraftWindow().isScreenOpened()) return;

        int itemUsedInt = itemStack.getMaximumDamage() - itemStack.getCurrentDamageValue();

        for(WarnTool tool : config.getTools()) {
            if(tool.getType() != type) continue;
            int itemWarnInt = (tool.getWarnAt() * itemStack.getMaximumDamage()) / 100;

            if(itemUsedInt == itemWarnInt) {
                if(!warns.contains(itemStack)) {
                    if(tool.openChat()) Laby.labyAPI().minecraft().openChat("");
                    Util.msg(Component.translatable(
                        "toolwarn.messages.warning",
                        NamedTextColor.RED,
                        Component.text(tool.getWarnAt())
                    ), true);
                    warns.add(itemStack);

                    if(tool.getSound() != WarnSound.NONE) {
                        Laby.labyAPI().minecraft().sounds().playSound(
                            tool.getSound().getResourceLocation(),
                            1f,
                            1f
                        );
                    }
                }
            } else if(tool.lastHitWarn() && itemUsedInt <= 1) {
                if(!warns.contains(itemStack)) {
                    if(tool.openChat()) Laby.labyAPI().minecraft().openChat("");
                    Util.msg(
                        Component.translatable("toolwarn.messages.lastHit", NamedTextColor.RED),
                        true
                    );
                    warns.add(itemStack);
                    if(tool.getLastSound() != WarnSound.NONE) {
                        Laby.labyAPI().minecraft().sounds().playSound(
                            tool.getLastSound().getResourceLocation(),
                            1f,
                            1f
                        );
                    }
                }
            } else {
                warns.remove(itemStack);
            }
        }
    }
}
