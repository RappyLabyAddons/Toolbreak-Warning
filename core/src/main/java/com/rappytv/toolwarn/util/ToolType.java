package com.rappytv.toolwarn.util;

import com.rappytv.toolwarn.config.TbwConfiguration;
import net.labymod.api.client.world.item.ItemStack;

public enum ToolType {
    None, Sword, Pickaxe, Axe, Shovel, Crossbow, Lighter, Shears, Trident;

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
        else if(path.equalsIgnoreCase("crossbow"))
            return Crossbow;
        else if(path.equalsIgnoreCase("flint_and_steel"))
            return Lighter;
        else if(path.equalsIgnoreCase("shears"))
            return Shears;
        else if(path.equalsIgnoreCase("trident"))
            return Trident;
        return None;
    }

    public int getWarnPercentage(TbwConfiguration configuration) {
        return switch (this) {
            case Sword -> configuration.swordPercentage().get();
            case Pickaxe -> configuration.pickAxePercentage().get();
            case Axe -> configuration.axePercentage().get();
            case Shovel -> configuration.shovelPercentage().get();
            case Crossbow -> configuration.crossbowPercentage().get();
            case Lighter -> configuration.lighterPercentage().get();
            case Shears -> configuration.shearsPercentage().get();
            case Trident -> configuration.tridentPercentage().get();
            default -> -1;
        };
    }
}
