package com.rappytv.toolwarn.util;

import com.rappytv.toolwarn.config.TbwConfiguration;
import net.labymod.api.client.world.item.ItemStack;

public enum ToolType {
    None, Sword, Pickaxe, Axe, Shovel;

    public static ToolType getByItem(ItemStack itemStack) {
        String path = itemStack.getIdentifier().getPath();
        if(path.endsWith("_sword"))
            return Sword;
        else if(path.endsWith("_pickaxe"))
            return Pickaxe;
        else if(path.endsWith("_axe"))
            return Axe;
        else if(path.endsWith("_shovel"))
            return Shovel;
        return None;
    }

    public int getWarnPercentage(TbwConfiguration configuration) {
        return switch (this) {
            case Sword -> configuration.swordPercentage().get();
            case Pickaxe -> configuration.pickAxePercentage().get();
            case Axe -> configuration.axePercentage().get();
            case Shovel -> configuration.shovelPercentage().get();
            default -> -1;
        };
    }

    public String displayName() {
        return Util.getTranslation("toolwarn.tooltype." + this.name().toLowerCase());
    }
}
