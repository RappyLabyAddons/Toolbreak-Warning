package com.rappytv.toolwarn.v1_8_9.mixins;

import com.rappytv.toolwarn.api.event.ItemStackDamageEvent;
import net.labymod.api.Laby;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2FPacketSetSlot;
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
  private void onHandleContainerSetSlot(S2FPacketSetSlot packet, CallbackInfo ci) {
    Minecraft minecraft = Minecraft.getMinecraft();
    if (minecraft.thePlayer == null || packet.func_149175_c() != 0) {
      return;
    }

    int hotbarSlot = 36 + minecraft.thePlayer.inventory.currentItem;
    if (packet.func_149173_d() != hotbarSlot) {
      return;
    }

    ItemStack incoming = packet.func_149174_e();
    ItemStack current = minecraft.thePlayer.getHeldItem();

    if (current == null || incoming == null || !incoming.isItemStackDamageable()) {
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
