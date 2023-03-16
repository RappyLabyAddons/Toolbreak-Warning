package com.rappytv.toolwarn.util;

import com.rappytv.toolwarn.TbwConfiguration;
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
        switch(this) {
            case Sword:
                return configuration.swordPercentage().get();
            case Pickaxe:
                return configuration.pickAxePercentage().get();
            case Axe:
                return configuration.axePercentage().get();
            case Shovel:
                return configuration.shovelPercentage().get();
            default:
                return -1;
        }
    }

    public String displayName() {
        return Util.getTranslation("tbw.tooltype." + this.name().toLowerCase());
    }
}
