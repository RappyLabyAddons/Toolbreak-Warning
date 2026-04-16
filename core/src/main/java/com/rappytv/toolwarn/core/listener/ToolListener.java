package com.rappytv.toolwarn.core.listener;

import com.rappytv.toolwarn.api.ItemStackDamageEvent;
import com.rappytv.toolwarn.api.WarnSound;
import com.rappytv.toolwarn.api.WarnTool;
import com.rappytv.toolwarn.api.WarnTool.Type;
import com.rappytv.toolwarn.core.ToolwarnAddon;
import com.rappytv.toolwarn.core.ToolwarnConfig;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;

public class ToolListener {

    private final ToolwarnConfig config;
    private final List<ItemStack> warns = new ArrayList<>();

    public ToolListener(ToolwarnAddon addon) {
        this.config = addon.configuration();
    }

    @Subscribe
    public void onItemStackSetDamage(ItemStackDamageEvent event) {
        boolean onRenderThread = Laby.labyAPI().minecraft().isOnRenderThread();
        ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
        if (player == null
            || player.gameMode() != GameMode.SURVIVAL
            && player.gameMode() != GameMode.ADVENTURE) {
            return;
        }
        if (Laby.labyAPI().minecraft().isSingleplayer()) {
            if (event.fromPacket()) {
                return;
            }
        } else if (!onRenderThread) {
            return;
        }

        ItemStack itemStack = event.itemStack();
        WarnTool.Type type = Type.getByItem(itemStack);
        if (type == Type.NONE) {
            return;
        }
        if (Laby.labyAPI().minecraft().minecraftWindow().isScreenOpened()) {
            return;
        }

        int itemUsedInt = itemStack.getMaximumDamage() - itemStack.getCurrentDamageValue();

        for (WarnTool tool : this.config.getTools()) {
            if (tool.getType() != type) {
                continue;
            }
            int itemWarnInt = (tool.getWarnAt() * itemStack.getMaximumDamage()) / 100;

            if (itemUsedInt == itemWarnInt) {
                if (!this.warns.contains(itemStack)) {
                    this.warns.add(itemStack);
                    if (tool.openChat()) {
                        this.openChatSync();
                    }
                    Laby.references().chatExecutor().displayClientMessage(
                        Component.empty()
                            .append(ToolwarnAddon.prefix)
                            .append(Component.translatable(
                                "toolwarn.warnings.firstWarning",
                                NamedTextColor.RED,
                                Component.text(tool.getWarnAt())
                            ))
                    );

                    if (tool.getSound() != WarnSound.NONE) {
                        Laby.labyAPI().minecraft().sounds().playSound(
                            tool.getSound().getLocation(),
                            1f,
                            1f
                        );
                    }
                }
            } else if (tool.lastHitWarn() && itemUsedInt <= 3) {
                if (!this.warns.contains(itemStack)) {
                    this.warns.add(itemStack);
                    if (tool.openChat()) {
                        this.openChatSync();
                    }
                    Laby.references().chatExecutor().displayClientMessage(
                        Component.empty()
                            .append(ToolwarnAddon.prefix)
                            .append(Component.translatable(
                                "toolwarn.warnings.lastHit",
                                NamedTextColor.RED
                            ))
                    );

                    if (tool.getLastSound() != WarnSound.NONE) {
                        Laby.labyAPI().minecraft().sounds().playSound(
                            tool.getSound().getLocation(),
                            1f,
                            1f
                        );
                    }
                }
            } else {
                this.warns.remove(itemStack);
            }
        }
    }

    private void openChatSync() {
        Runnable openChatRunnable = () -> Laby.labyAPI().minecraft().openChat("");
        if (Laby.labyAPI().minecraft().isOnRenderThread()) {
            openChatRunnable.run();
        } else {
            Laby.labyAPI().minecraft().executeOnRenderThread(openChatRunnable);
        }
    }

}
