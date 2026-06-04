package com.rappytv.toolwarn.api;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.loader.MinecraftVersions;
import org.jetbrains.annotations.Nullable;

public enum WarnSound {

  NONE,
  PLING("note.pling", "block.note.pling", "block.note_block.pling"),
  LEVEL_UP("random.levelup", "entity.player.levelup", "entity.player.levelup"),
  GLASS_BREAK("game.potion.smash", "block.glass.break", "block.glass.break"),
  ANVIL_USE("random.anvil_use", "block.anvil.use", "block.anvil.use");

  private final ResourceLocation location;

  WarnSound() {
    this.location = null;
  }

  WarnSound(String v1_8_9, String v1_12_2, String above) {
    this.location = ResourceLocation.create(
        "minecraft",
        MinecraftVersions.V1_8_9.isCurrent()
            ? v1_8_9 :
            MinecraftVersions.V1_12_2.isCurrent() ? v1_12_2 : above
    );
  }

  /**
   * Get the resource location of the sound
   *
   * @return The {@link ResourceLocation} to play the sound. Returns {@link null} for {@link #NONE}
   */
  @Nullable
  public ResourceLocation getLocation() {
    return this.location;
  }
}
