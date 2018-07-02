package io.github.ranolp.rattranslate.json;

import io.github.ranolp.rattranslate.BukkitPlayer;
import org.bukkit.ChatColor;

public final class NoJsonSender implements JsonSender {
  private NoJsonSender() {
  }

  public static final NoJsonSender INSTANCE = new NoJsonSender();

  @Override
  public void sendMessage(BukkitPlayer target, String message, String onHover) {
    target.sendMessage(message + ChatColor.GRAY + ChatColor.ITALIC + "(" + onHover + ")");
  }
}
