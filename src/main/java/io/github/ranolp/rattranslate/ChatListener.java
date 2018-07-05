package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.lang.LangStorage;
import io.github.ranolp.rattranslate.lang.Variable;
import io.github.ranolp.rattranslate.translator.Translator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Set<RatPlayer> recipients = new HashSet<>();
        Set<Player> toRemove = new HashSet<>();
        for (Player bukkitPlayer: e.getRecipients()) {
            RatPlayer player = RatPlayer.of(bukkitPlayer);
            if (player.getTranslateMode() && !e.getPlayer().equals(bukkitPlayer)) {
                toRemove.add(bukkitPlayer);
                recipients.add(RatPlayer.of(bukkitPlayer));
            }
        }
        e.getRecipients().removeAll(toRemove);


        LangStorage langStorage = RatTranslate.getInstance().getLangStorage();
        Translator translator = RatTranslate.getInstance().getTranslator();
        RatPlayer player = RatPlayer.of(e.getPlayer());

        String message = e.getMessage();
        boolean auto = translator.isAutoSupported() && player.getLocale().isAmbiguousSentence(message);

        Map<Locale, String> translateMap = recipients.
                                                             stream().
                                                             map(RatPlayer::getLocale).
                                                             distinct().
                                                             collect(Collectors.toMap(locale -> locale,
                                                                                      locale -> String.format(
                                                                                              e.getFormat(),
                                                                                              player.getDisplayName(),
                                                                                              auto
                                                                                                      ? translator.translateAuto(
                                                                                                      message, locale)
                                                                                                      : translator.translate(
                                                                                                              message,
                                                                                                              player.getLocale(),
                                                                                                              locale))));
        for (RatPlayer recipient: recipients) {
            String translated = translateMap.get(recipient.getLocale());
            if (RatTranslate.getInstance().isJsonMessageAvailable()) {
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
