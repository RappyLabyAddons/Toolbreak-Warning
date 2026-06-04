package com.rappytv.toolwarn.v1_21_5.mixins;

import com.rappytv.toolwarn.api.event.ItemStackDamageEvent;
import net.labymod.api.Laby;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {

  @Inject(
      method = "handleContainerSetSlot",
      at = @At("HEAD")
  )
  private void onHandleContainerSetSlot(ClientboundContainerSetSlotPacket packet,
      CallbackInfo ci) {
    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft.player == null || packet.getContainerId() != 0) {
      return;
    }

    int hotbarSlot = 36 + minecraft.player.getInventory().getSelectedSlot();
    if (packet.getSlot() != hotbarSlot) {
      return;
    }

    ItemStack incoming = packet.getItem();
    ItemStack current = minecraft.player.getMainHandItem();

    if (current.isEmpty() || !incoming.isDamageableItem()) {
      return;
    }
    int maxDamage = incoming.getMaxDamage();
    int oldDurability = maxDamage - current.getDamageValue();
    int newDurability = maxDamage - incoming.getDamageValue();
    if (newDurability >= oldDurability) {
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
