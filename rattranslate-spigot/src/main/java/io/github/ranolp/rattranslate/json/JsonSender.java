package io.github.ranolp.rattranslate.json;

import io.github.ranolp.rattranslate.BukkitPlayer;

public interface JsonSender {
  void sendMessage(BukkitPlayer target, String message, String onHover);
}
