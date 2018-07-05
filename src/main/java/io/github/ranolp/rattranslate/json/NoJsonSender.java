package io.github.ranolp.rattranslate.json;

import io.github.ranolp.rattranslate.RatPlayer;
import org.bukkit.ChatColor;

public final class NoJsonSender implements JsonSender {
    public static final NoJsonSender INSTANCE = new NoJsonSender();

    private NoJsonSender() {
    }

    @Override
    public void sendMessage(RatPlayer target, String message, String onHover) {
        target.sendMessage(message + ChatColor.GRAY + ChatColor.ITALIC + "(" + onHover + ")");
    }
}
