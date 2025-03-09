package com.rappytv.toolwarn.listener;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rappytv.toolwarn.ToolwarnConfig;
import com.rappytv.toolwarn.api.WarnSound;
import com.rappytv.toolwarn.api.WarnTool;
import com.rappytv.toolwarn.api.WarnTool.Type;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.ConfigurationVersionUpdateEvent;

@SuppressWarnings("FieldCanBeLocal")
public class ConfigMigrationListener {

    private static final Gson gson = new Gson();
    private final int defaultPercentage = 5;

    @Subscribe
    public void onConfigVersionUpdate(ConfigurationVersionUpdateEvent event) {
        Class<? extends Config> configClass = event.getConfigClass();
        int usedVersion = event.getUsedVersion();

        if (configClass == ToolwarnConfig.class) {
            if (usedVersion == 1) {
                this.migrateFromOne(event);
            }
        }
    }

    private void migrateFromOne(ConfigurationVersionUpdateEvent event) {
        JsonObject config = event.getJsonObject();
        if(!config.has("sounds")) return;

        JsonObject sounds = config.get("sounds").getAsJsonObject();

        WarnSound warnSound = WarnSound.NONE;
        WarnSound lastHitSound = WarnSound.NONE;
        boolean openChat = true;
        boolean lastHitWarn = true;
        int sword = this.defaultPercentage;
        int pickaxe = this.defaultPercentage;
        int axe = this.defaultPercentage;
        int shovel = this.defaultPercentage;
        int crossbow = this.defaultPercentage;
        int lighter = this.defaultPercentage;
        int shears = this.defaultPercentage;
        int trident = this.defaultPercentage;

        try {
            if (sounds.has("warnSound")) {
                warnSound = WarnSound.valueOf(sounds.get("warnSound").getAsString());
            }
            if (sounds.has("lastHitSound")) {
                lastHitSound = WarnSound.valueOf(sounds.get("lastHitSound").getAsString());
            }
        } catch (IllegalArgumentException ignored) {
        }

        if (config.has("openChat")) {
            openChat = config.get("openChat").getAsBoolean();
        }
        if (config.has("lastHit")) {
            lastHitWarn = config.get("lastHit").getAsBoolean();
        }
        if (config.has("swordPercentage")) {
            sword = config.get("swordPercentage").getAsInt();
        }
        if (config.has("pickaxePercentage")) {
            pickaxe = config.get("pickaxePercentage").getAsInt();
        }
        if (config.has("axePercentage")) {
            axe = config.get("axePercentage").getAsInt();
        }
        if (config.has("shovelPercentage")) {
            shovel = config.get("shovelPercentage").getAsInt();
        }
        if (config.has("crossbowPercentage")) {
            crossbow = config.get("crossbowPercentage").getAsInt();
        }
        if (config.has("lighterPercentage")) {
            lighter = config.get("lighterPercentage").getAsInt();
        }
        if (config.has("shearsPercentage")) {
            shears = config.get("shearsPercentage").getAsInt();
        }
        if (config.has("tridentPercentage")) {
            trident = config.get("tridentPercentage").getAsInt();
        }

        JsonArray tools = new JsonArray();
        tools.add(gson.toJsonTree(new WarnTool(Type.SWORD, warnSound, lastHitSound, sword, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.PICKAXE, warnSound, lastHitSound, pickaxe, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.AXE, warnSound, lastHitSound, axe, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.SHOVEL, warnSound, lastHitSound, shovel, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.CROSSBOW, warnSound, lastHitSound, crossbow, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.LIGHTER, warnSound, lastHitSound, lighter, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.SHEARS, warnSound, lastHitSound, shears, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.TRIDENT, warnSound, lastHitSound, trident, openChat, lastHitWarn)));

        config.add("tools", tools);
        event.setJsonObject(config);
    }
}
