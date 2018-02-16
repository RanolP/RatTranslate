package io.github.ranolp.rattranslate;


import io.github.ranolp.rattranslate.locale.LocaleDelegate;
import io.github.ranolp.rattranslate.locale.ReflectionLocaleDelegate;
import io.github.ranolp.rattranslate.locale.SimpleLocaleDelegate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class BukkitPlayer extends io.github.ranolp.rattranslate.abstraction.Player implements ConfigurationSerializable {
  static final Map<String, BukkitPlayer> PLAYER_MAP = new HashMap<>();
  private static final LocaleDelegate DELEGATE;

  static {
    LocaleDelegate delegate = ReflectionLocaleDelegate.getInstance();
    try {
      Player.class.getMethod("getLocale");
      delegate = SimpleLocaleDelegate.getInstance();
    } catch (NoSuchMethodException e) {
      // ignore, getLocale method not supported
    }
    DELEGATE = delegate;
  }

  private String nickname;

  private BukkitPlayer(String nickname) {
    this.nickname = nickname;
  }

  public static BukkitPlayer of(Player player) {
    if (!PLAYER_MAP.containsKey(player.getName())) {
      PLAYER_MAP.put(player.getName(), new BukkitPlayer(player.getName()));
    }
    return PLAYER_MAP.get(player.getName());
  }

  public static BukkitPlayer of(String nickname) {
    if (!PLAYER_MAP.containsKey(nickname)) {
      PLAYER_MAP.put(nickname, new BukkitPlayer(nickname));
    }
    return PLAYER_MAP.get(nickname);
  }

  /**
   * Required method for deserialization
   *
   * @param args map to deserialize
   * @return deserialized location
   * @throws IllegalArgumentException if the world don't exists
   * @see ConfigurationSerializable
   */
  public static BukkitPlayer deserialize(Map<String, Object> args) {
    BukkitPlayer result = BukkitPlayer.of((String) args.get("nickname"));
    result.setTranslateMode((boolean) args.get("translate"));
    Object customLocale = args.get("custom-locale");
    if (customLocale instanceof String) {
      result.setCustomLocale(Locale.getByCode((String) customLocale));
    }
    return result;
  }

  public Optional<Player> getPlayer() {
    return Optional.ofNullable(Bukkit.getPlayerExact(nickname));
  }

  @Override
  public String getDisplayName() {
    return getPlayer().map(Player::getDisplayName).orElse(nickname);
  }

  @Override
  public void sendMessage(String message) {
    getPlayer().ifPresent(p -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
  }

  @Override
  public void sendHoverableMessage(String message, String onHover) {
    if (!Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(
        "tellraw %s {\"text\":\"%s\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"%s\",\"italic\":true,\"color\":\"gray\"}}}",
        nickname, ChatColor.translateAlternateColorCodes('&', message),
        ChatColor.translateAlternateColorCodes('&', onHover)))) {
      // fail fallback
      sendMessage(message);
    }
  }

  @Override
  public Locale getRealLocale() {
    return getPlayer().map(DELEGATE::getLocale).map(Locale::getByCode).orElse(Locale.AMERICAN_ENGLISH);
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    result.put("nickname", nickname);
    result.put("translate", getTranslateMode());
    result.put("custom-locale", getCustomLocale() != null ? getCustomLocale().getCode() : null);
    return result;
  }
}
