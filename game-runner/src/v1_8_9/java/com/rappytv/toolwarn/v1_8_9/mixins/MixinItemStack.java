package com.rappytv.toolwarn.v1_8_9.mixins;

import com.rappytv.toolwarn.api.ItemStackDamageEvent;
import net.labymod.api.Laby;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class MixinItemStack { // TODO: Test this

    @Inject(method = "setItemDamage", at = @At("HEAD"))
    public void onSetDamageValue(int newDamageValue, CallbackInfo ci) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;

        ItemStack self = (ItemStack) (Object) this;
        ItemStack held = player.getHeldItem();
        if(!ItemStack.areItemStacksEqual(self, held)) return;
        int maxDamage = self.getMaxDamage();
        int oldDurability = maxDamage - self.getItemDamage();
        int newDurability = maxDamage - newDamageValue;
        if(newDurability > oldDurability) return;

        Laby.fireEvent(new ItemStackDamageEvent(
            MinecraftUtil.fromMinecraft(self),
            oldDurability,
            newDurability
        ));
    }
}
