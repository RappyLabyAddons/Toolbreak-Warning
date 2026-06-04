package com.rappytv.toolwarn.core;

import com.rappytv.toolwarn.core.listener.ConfigMigrationListener;
import com.rappytv.toolwarn.core.listener.ToolListener;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.revision.SimpleRevision;
import net.labymod.api.util.version.SemanticVersion;

@AddonMain
public class ToolwarnAddon extends LabyAddon<ToolwarnConfig> {

  public static Component prefix = Component.empty()
      .append(Component.text("TBW", NamedTextColor.RED).decorate(TextDecoration.BOLD))
      .append(Component.space())
      .append(Component.text("» ", NamedTextColor.DARK_GRAY));
  private static ToolwarnAddon INSTANCE;

  public static ToolwarnAddon getInstance() {
    return INSTANCE;
  }

  @Override
  protected void preConfigurationLoad() {
    Laby.references().revisionRegistry().register(new SimpleRevision(
        "toolwarn",
        new SemanticVersion(1, 3, 4),
        "2024-01-26"
    ));
    Laby.references().revisionRegistry().register(new SimpleRevision(
        "toolwarn",
        new SemanticVersion(1, 4, 0),
        "2024-03-14"
    ));
    this.registerListener(new ConfigMigrationListener());
  }

  @Override
  protected void enable() {
    INSTANCE = this;

    this.registerSettingCategory();
    this.configuration().createDefaultTools();

    this.registerListener(new ToolListener(this));
  }

  @Override
  protected Class<? extends ToolwarnConfig> configurationClass() {
    return ToolwarnConfig.class;
  }
}
