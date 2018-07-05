package io.github.ranolp.rattranslate.json;

import io.github.ranolp.rattranslate.RatPlayer;

public interface JsonSender {
  void sendMessage(RatPlayer target, String message, String onHover);
}
