package io.github.ranolp.rattranslate.json;

import io.github.ranolp.rattranslate.RatPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public final class TellrawJsonSender implements JsonSender {
    public static final TellrawJsonSender INSTANCE = new TellrawJsonSender();

    private TellrawJsonSender() {
    }

    @Override
    public void sendMessage(RatPlayer target, String message, String onHover) {
        if (!Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(
                "tellraw %s {\"text\":\"%s\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"%s\",\"italic\":true,\"color\":\"gray\"}}}",
                target.getNickname(), ChatColor.translateAlternateColorCodes('&', message),
                ChatColor.translateAlternateColorCodes('&', onHover)))) {
            // fail fallback
            target.sendMessage(message);
        }
    }
}
