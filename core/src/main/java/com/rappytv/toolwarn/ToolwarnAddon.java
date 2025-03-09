package com.rappytv.toolwarn;

import com.rappytv.toolwarn.api.IToolwarnSounds;
import com.rappytv.toolwarn.api.WarnSound;
import com.rappytv.toolwarn.core.generated.DefaultReferenceStorage;
import com.rappytv.toolwarn.listener.ConfigMigrationListener;
import com.rappytv.toolwarn.listener.GameTickListener;
import com.rappytv.toolwarn.util.DefaultToolwarnSounds;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.revision.SimpleRevision;
import net.labymod.api.util.version.SemanticVersion;

@AddonMain
public class ToolwarnAddon extends LabyAddon<ToolwarnConfig> {

    public static Component prefix = Component.empty()
        .append(Component.text("TBW", NamedTextColor.RED).decorate(TextDecoration.BOLD))
        .append(Component.space())
        .append(Component.text("» ", NamedTextColor.DARK_GRAY));
    private static ToolwarnAddon instance;
    private IToolwarnSounds sounds;

    public static ToolwarnAddon getInstance() {
        return instance;
    }

    @Override
    protected void preConfigurationLoad() {
        Laby.references().revisionRegistry().register(new SimpleRevision("toolwarn", new SemanticVersion("1.3.4"), "2024-01-26"));
        Laby.references().revisionRegistry().register(new SimpleRevision("toolwarn", new SemanticVersion("1.4.0"), "2024-03-14"));
        this.registerListener(new ConfigMigrationListener());
    }

    @Override
    protected void enable() {
        instance = this;

        this.registerSettingCategory();
        this.sounds = ((DefaultReferenceStorage) this.referenceStorageAccessor()).getIToolwarnSounds();
        if (this.sounds == null) {
            this.sounds = new DefaultToolwarnSounds();
        }

        this.registerListener(new GameTickListener(this));
    }

    public ResourceLocation getWarnSound(WarnSound sound) {
        return switch (sound) {
            case NONE -> null;
            case PLING -> this.sounds.getPlingSound();
            case LEVEL_UP -> this.sounds.getLevelUpSound();
            case GLASS_BREAK -> this.sounds.getGlassBreakSound();
            case ANVIL_USE -> this.sounds.getAnvilUseSound();
        };
    }

    @Override
    protected Class<? extends ToolwarnConfig> configurationClass() {
        return ToolwarnConfig.class;
    }
}
