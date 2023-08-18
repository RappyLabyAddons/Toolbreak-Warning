package com.rappytv.toolwarn;

import com.rappytv.toolwarn.config.TbwConfiguration;
import com.rappytv.toolwarn.core.generated.DefaultReferenceStorage;
import com.rappytv.toolwarn.listener.GameTickListener;
import com.rappytv.toolwarn.util.ITbwSounds;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class TbwAddon extends LabyAddon<TbwConfiguration> {

    public static Component prefix;
    private static ITbwSounds sounds;

    @Override
    protected void enable() {
        //  "§c§lTBW §8» §7"
        prefix = Component
            .text("TBW ", Style.builder().color(NamedTextColor.RED).decorate(TextDecoration.BOLD).build())
            .append(Component.text("» ", NamedTextColor.DARK_GRAY));
        sounds = ((DefaultReferenceStorage) this.referenceStorageAccessor()).iTbwSounds();
        registerSettingCategory();

        registerListener(new GameTickListener(this));
    }

    public static ITbwSounds getSounds() {
        return sounds;
    }

    @Override
    protected Class<? extends TbwConfiguration> configurationClass() {
        return TbwConfiguration.class;
    }
}
