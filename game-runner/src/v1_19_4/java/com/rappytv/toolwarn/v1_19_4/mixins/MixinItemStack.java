package com.rappytv.toolwarn.v1_19_4.mixins;

import com.rappytv.toolwarn.api.ItemStackDamageEvent;
import net.labymod.api.Laby;
import net.labymod.v1_19_4.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class MixinItemStack {

    // Minecraft#getInstance is marked as NotNull but is still null on startup for whatever reason
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "setDamageValue", at = @At("HEAD"))
    public void onSetDamageValue(int newDamageValue, CallbackInfo ci) {
        if (Minecraft.getInstance() == null) {
            return;
        }
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemStack self = (ItemStack) (Object) this;
        ItemStack held = player.getMainHandItem();
        if(!ItemStack.isSame(self, held)) return;
        int maxDamage = self.getMaxDamage();
        int oldDurability = maxDamage - self.getDamageValue();
        int newDurability = maxDamage - newDamageValue;
        if(newDurability > oldDurability) return;

        Laby.fireEvent(new ItemStackDamageEvent(
            MinecraftUtil.fromMinecraft(self),
            oldDurability,
            newDurability,
            false
        ));
    }
}
