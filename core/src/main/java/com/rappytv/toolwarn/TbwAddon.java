package com.rappytv.toolwarn;

import com.rappytv.toolwarn.config.TbwConfiguration;
import com.rappytv.toolwarn.core.generated.DefaultReferenceStorage;
import com.rappytv.toolwarn.listener.GameTickListener;
import com.rappytv.toolwarn.util.ITbwSounds;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.revision.SimpleRevision;
import net.labymod.api.util.version.SemanticVersion;

@AddonMain
public class TbwAddon extends LabyAddon<TbwConfiguration> {

    public static Component prefix;
    private static ITbwSounds sounds;
    private static TbwAddon instance;

    @Override
    protected void preConfigurationLoad() {
        Laby.references().revisionRegistry().register(new SimpleRevision("toolwarn", new SemanticVersion("1.3.4"), "2024-01-26"));
        Laby.references().revisionRegistry().register(new SimpleRevision("toolwarn", new SemanticVersion("1.4.0"), "2024-03-09"));
    }

    @Override
    protected void enable() {
        prefix = Component
            .text("TBW ", Style.builder().color(NamedTextColor.RED).decorate(TextDecoration.BOLD).build())
            .append(Component.text("» ", NamedTextColor.DARK_GRAY));
        sounds = ((DefaultReferenceStorage) this.referenceStorageAccessor()).iTbwSounds();
        registerSettingCategory();
        instance = this;

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
