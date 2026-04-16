package com.rappytv.toolwarn.v1_12_2.mixins;

import com.rappytv.toolwarn.api.ItemStackDamageEvent;
import net.labymod.api.Laby;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinClientPacketListener {

    @Inject(
        method = "handleSetSlot",
        at = @At("HEAD")
    )
    private void onHandleContainerSetSlot(SPacketSetSlot packet, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft.player == null || packet.getWindowId() != 0) {
            return;
        }

        int hotbarSlot = 36 + minecraft.player.inventory.currentItem;
        if (packet.getSlot() != hotbarSlot) {
            return;
        }

        ItemStack incoming = packet.getStack();
        ItemStack current = minecraft.player.getHeldItemMainhand();

        if (current.isEmpty() || !incoming.isItemStackDamageable()) {
            return;
        }
        int maxDamage = incoming.getMaxDamage();
        int oldDurability = maxDamage - current.getItemDamage();
        int newDurability = maxDamage - incoming.getItemDamage();
        if (newDurability > oldDurability) {
            return;
        }

        Laby.fireEvent(new ItemStackDamageEvent(
            MinecraftUtil.fromMinecraft(incoming),
            oldDurability,
            newDurability,
            true
        ));
    }

}
