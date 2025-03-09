package com.rappytv.toolwarn.api;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WarnTool {

    private static final ResourceLocation ICON_SPRITE = ResourceLocation.create("toolwarn",
        "textures/tools.png");
    private Type type;
    private WarnSound sound;
    private WarnSound lastSound;
    private int warnAt;
    private boolean openChat;
    private boolean lastHitWarn;

    public WarnTool() {
        this(Type.SWORD, WarnSound.NONE, WarnSound.NONE, 5, true, true);
    }

    public WarnTool(Type type, WarnSound sound, WarnSound lastSound, int warnAt, boolean openChat, boolean lastHitWarn) {
        this.type = type;
        this.sound = sound;
        this.lastSound = lastSound;
        this.warnAt = warnAt;
        this.openChat = openChat;
        this.lastHitWarn = lastHitWarn;
    }

    public Type getType() {
        return this.type;
    }
    public WarnSound getSound() {
        return this.sound;
    }
    public WarnSound getLastSound() {
        return this.lastSound;
    }
    public int getWarnAt() {
        return this.warnAt;
    }
    public boolean openChat() {
        return this.openChat;
    }
    public boolean lastHitWarn() {
        return this.lastHitWarn;
    }

    public void setType(Type type) {
        this.type = type;
    }
    public void setSound(WarnSound sound) {
        this.sound = sound;
    }
    public void setLastSound(WarnSound lastSound) {
        this.lastSound = lastSound;
    }
    public void setWarnAt(int warnAt) {
        this.warnAt = warnAt;
    }
    public void setOpenChat(boolean openChat) {
        this.openChat = openChat;
    }
    public void setLastHitWarn(boolean lastHitWarn) {
        this.lastHitWarn = lastHitWarn;
    }

    public enum Type {
        NONE(-1, -1),
        SWORD(0, 0),
        PICKAXE(1, 0),
        AXE(2, 0),
        SHOVEL(3, 0),
        HOE(0, 1),
        BOW(1, 1),
        CROSSBOW(2, 1),
        LIGHTER(3, 1),
        SHEARS(0, 2),
        TRIDENT(1, 2);

        private final Icon icon;

        Type(int x, int y) {
            this.icon = Icon.sprite32(ICON_SPRITE, x, y);
        }

        public Icon getIcon() {
            return this.icon;
        }

        public static Type getByItem(@Nullable ItemStack itemStack) {
            if(itemStack == null) return NONE;
            String path = itemStack.getIdentifier().getPath();
            if (path.endsWith("_sword")) {
                return SWORD;
            } else if (path.endsWith("_pickaxe")) {
                return PICKAXE;
            } else if (path.endsWith("_axe")) {
                return AXE;
            } else if (path.endsWith("_shovel")) {
                return SHOVEL;
            } else if (path.endsWith("_hoe")) {
                return HOE;
            } else if (path.equalsIgnoreCase("bow")) {
                return BOW;
            } else if (path.equalsIgnoreCase("crossbow")) {
                return CROSSBOW;
            } else if (path.equalsIgnoreCase("flint_and_steel")) {
                return LIGHTER;
            } else if (path.equalsIgnoreCase("shears")) {
                return SHEARS;
            } else if (path.equalsIgnoreCase("trident")) {
                return TRIDENT;
            }
            return NONE;
        }
    }
}
