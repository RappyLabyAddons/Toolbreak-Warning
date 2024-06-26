package com.rappytv.toolwarn;

import com.rappytv.toolwarn.api.DefaultTbwSounds;
import com.rappytv.toolwarn.config.TbwConfiguration;
import com.rappytv.toolwarn.core.generated.DefaultReferenceStorage;
import com.rappytv.toolwarn.listener.ConfigMigrationListener;
import com.rappytv.toolwarn.listener.GameTickListener;
import com.rappytv.toolwarn.api.ITbwSounds;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.revision.SimpleRevision;
import net.labymod.api.util.version.SemanticVersion;

@SuppressWarnings("ConstantConditions")
@AddonMain
public class TbwAddon extends LabyAddon<TbwConfiguration> {

    public static Component prefix = Component
        .text("TBW ", Style.builder().color(NamedTextColor.RED).decorate(TextDecoration.BOLD).build())
        .append(Component.text("» ", NamedTextColor.DARK_GRAY));

    private static ITbwSounds sounds;
    private static TbwAddon instance;

    @Override
    protected void preConfigurationLoad() {
        Laby.references().revisionRegistry().register(new SimpleRevision("toolwarn", new SemanticVersion("1.3.4"), "2024-01-26"));
        Laby.references().revisionRegistry().register(new SimpleRevision("toolwarn", new SemanticVersion("1.4.0"), "2024-03-14"));
        registerListener(new ConfigMigrationListener());
    }

    @Override
    protected void enable() {
        registerSettingCategory();
        instance = this;
        sounds = ((DefaultReferenceStorage) this.referenceStorageAccessor()).iTbwSounds();
        if(sounds == null)
            sounds = new DefaultTbwSounds();

        registerListener(new GameTickListener(this));
    }

    public static TbwAddon get() {
        return instance;
    }

    public static ITbwSounds getSounds() {
        return sounds;
    }

    @Override
    protected Class<? extends TbwConfiguration> configurationClass() {
        return TbwConfiguration.class;
    }
}
