package io.github.ranolp.rattranslate.json;

import io.github.ranolp.rattranslate.RatPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public final class BungeeJsonSender implements JsonSender {
    public static final BungeeJsonSender INSTANCE = new BungeeJsonSender();

    private BungeeJsonSender() {
    }

    @Override
    public void sendMessage(RatPlayer target, String message, String onHover) {
        TextComponent component = new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
        TextComponent hover = new TextComponent(ChatColor.translateAlternateColorCodes('&', onHover));
        hover.setItalic(true);
        hover.setColor(ChatColor.GRAY);
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[] {hover}));
        target.getPlayer().ifPresent(player -> player.spigot().sendMessage(component));
    }
}
