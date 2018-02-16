package io.github.ranolp.rattranslate;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import io.github.ranolp.rattranslate.abstraction.Platform;
import io.github.ranolp.rattranslate.abstraction.Player;
import io.github.ranolp.rattranslate.event.PlayerChatEvent;
import io.github.ranolp.rattranslate.lang.LangStorage;
import io.github.ranolp.rattranslate.lang.Variable;
import io.github.ranolp.rattranslate.translator.Translator;

class ChatHandler {
  // bukkit chat system is on async thread
  @AllowConcurrentEvents
  @Subscribe
  public void onPlayerChat(PlayerChatEvent e) {
    LangStorage langStorage = RatTranslate.getInstance().getLangStorage();
    Translator translator = RatTranslate.getInstance().getTranslator();
    Platform platform = RatTranslate.getInstance().getPlatform();
    Player player = e.getPlayer();
    for (Player recipient : e.getRecipients()) {
      String translated = String.format(e.getFormat(), player.getDisplayName(),
          translator.translate(e.getMessage(), player.getLocale(),
              recipient.getLocale()));
      if (platform.isJsonMessageAvailable()) {
        String hover = recipient.format(langStorage, "chat.original",
            Variable.ofAny("hover", "text", e.getMessage()),
            Variable.ofAny("hover", "lang", recipient.format(langStorage,
                player.getLocale()
                    .toPropertiesKey())));
        recipient.sendHoverableMessage(translated, hover);
      } else {
        recipient.sendMessage(translated);
      }
    }
  }
}
