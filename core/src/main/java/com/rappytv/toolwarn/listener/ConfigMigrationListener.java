package com.rappytv.toolwarn.listener;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rappytv.toolwarn.config.TbwConfiguration;
import com.rappytv.toolwarn.util.WarnSound;
import com.rappytv.toolwarn.util.WarnTool;
import com.rappytv.toolwarn.util.WarnTool.Type;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.ConfigurationVersionUpdateEvent;

@SuppressWarnings("FieldCanBeLocal")
public class ConfigMigrationListener {

    private final Gson gson = new Gson();
    private final int defaultPercentage = 5;

    @Subscribe
    public void onConfigVersionUpdate(ConfigurationVersionUpdateEvent event) {
        Class<? extends Config> configClass = event.getConfigClass();
        int usedVersion = event.getUsedVersion();

        if(configClass == TbwConfiguration.class) {
            if(usedVersion == 1) migrateFromOne(event);
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
        int sword = defaultPercentage;
        int pickaxe = defaultPercentage;
        int axe = defaultPercentage;
        int shovel = defaultPercentage;
        int bow = defaultPercentage;
        int crossbow = defaultPercentage;
        int lighter = defaultPercentage;
        int shears = defaultPercentage;
        int trident = defaultPercentage;

        try {
            warnSound = WarnSound.valueOf(sounds.get("warnSound").getAsString());
            lastHitSound = WarnSound.valueOf(sounds.get("lastHitSound").getAsString());
            openChat = config.get("openChat").getAsBoolean();
            lastHitWarn = config.get("lastHit").getAsBoolean();
            sword = config.get("swordPercentage").getAsInt();
            pickaxe = config.get("pickaxePercentage").getAsInt();
            axe = config.get("axePercentage").getAsInt();
            shovel = config.get("shovelPercentage").getAsInt();
            bow = config.get("bowPercentage").getAsInt();
            crossbow = config.get("crossbowPercentage").getAsInt();
            lighter = config.get("lighterPercentage").getAsInt();
            shears = config.get("shearsPercentage").getAsInt();
            trident = config.get("tridentPercentage").getAsInt();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        JsonArray tools = new JsonArray();
        tools.add(gson.toJsonTree(new WarnTool(Type.SWORD, warnSound, lastHitSound, sword, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.PICKAXE, warnSound, lastHitSound, pickaxe, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.AXE, warnSound, lastHitSound, axe, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.SHOVEL, warnSound, lastHitSound, shovel, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.BOW, warnSound, lastHitSound, bow, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.CROSSBOW, warnSound, lastHitSound, crossbow, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.LIGHTER, warnSound, lastHitSound, lighter, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.SHEARS, warnSound, lastHitSound, shears, openChat, lastHitWarn)));
        tools.add(gson.toJsonTree(new WarnTool(Type.TRIDENT, warnSound, lastHitSound, trident, openChat, lastHitWarn)));

        config.add("tools", tools);
        event.setJsonObject(config);
    }
}
