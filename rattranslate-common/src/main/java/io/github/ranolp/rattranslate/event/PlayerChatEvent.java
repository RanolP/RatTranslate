package io.github.ranolp.rattranslate.event;

import io.github.ranolp.rattranslate.abstraction.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PlayerChatEvent extends Event {
  private final Player player;
  private final String format;
  private final Set<Player> recipients = new HashSet<>();
  private String message;

  public PlayerChatEvent(Player player, String format, Collection<Player> recipients, String message) {
    this.player = Objects.requireNonNull(player, "player");
    this.format = Objects.requireNonNull(format, "format");
    this.recipients.addAll(Objects.requireNonNull(recipients, "recipients"));
    this.message = Objects.requireNonNull(message, "message");
  }

  public Player getPlayer() {
    return player;
  }

  public String getFormat() {
    return format;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Set<Player> getRecipients() {
    return new HashSet<>(recipients);
  }
}
