package com.rappytv.toolwarn.listener;

import com.rappytv.toolwarn.ToolwarnAddon;
import com.rappytv.toolwarn.ToolwarnConfig;
import com.rappytv.toolwarn.api.WarnSound;
import com.rappytv.toolwarn.api.WarnTool;
import com.rappytv.toolwarn.api.WarnTool.Type;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;

public class GameTickListener {

    private final ToolwarnAddon addon;
    private final ToolwarnConfig config;
    private static final List<ItemStack> warns = new ArrayList<>();

    public GameTickListener(ToolwarnAddon addon) {
        this.addon = addon;
        this.config = addon.configuration();
    }

    @Subscribe
    public void onTick(GameTickEvent event) {
        if (!this.addon.configuration().enabled().get()) {
            return;
        }
        ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
        if(player == null) return;
        ItemStack itemStack = player.getMainHandItemStack();

        if(itemStack == null) return;
        if(itemStack.getMaximumDamage() == 0) return;
        if(player.gameMode() != GameMode.SURVIVAL && player.gameMode() != GameMode.ADVENTURE) return;

        this.checkForWarn(itemStack, Type.getByItem(itemStack));
    }

    private void checkForWarn(ItemStack itemStack, WarnTool.Type type) {
        if(type == Type.NONE) return;
        if(Laby.labyAPI().minecraft().minecraftWindow().isScreenOpened()) return;

        int itemUsedInt = itemStack.getMaximumDamage() - itemStack.getCurrentDamageValue();

        for (WarnTool tool : this.config.getTools()) {
            if(tool.getType() != type) continue;
            int itemWarnInt = (tool.getWarnAt() * itemStack.getMaximumDamage()) / 100;

            if(itemUsedInt <= itemWarnInt) {
                if(!warns.contains(itemStack)) {
                    if(tool.openChat()) Laby.labyAPI().minecraft().openChat("");
                    Laby.references().chatExecutor().displayClientMessage(
                        Component.empty()
                            .append(ToolwarnAddon.prefix)
                            .append(Component.translatable(
                                "toolwarn.messages.warning",
                                NamedTextColor.RED,
                                Component.text(tool.getWarnAt())
                            ))
                    );
                    warns.add(itemStack);

                    if(tool.getSound() != WarnSound.NONE) {
                        Laby.labyAPI().minecraft().sounds().playSound(
                            this.addon.getWarnSound(tool.getSound()),
                            1f,
                            1f
                        );
                    }
                }
            } else if(tool.lastHitWarn() && itemUsedInt <= 1) {
                if(!warns.contains(itemStack)) {
                    if(tool.openChat()) Laby.labyAPI().minecraft().openChat("");
                    Laby.references().chatExecutor().displayClientMessage(
                        Component.empty()
                            .append(ToolwarnAddon.prefix)
                            .append(Component.translatable(
                                "toolwarn.messages.lastHit",
                                NamedTextColor.RED
                            ))
                    );
                    warns.add(itemStack);
                    if(tool.getLastSound() != WarnSound.NONE) {
                        Laby.labyAPI().minecraft().sounds().playSound(
                            this.addon.getWarnSound(tool.getLastSound()),
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
