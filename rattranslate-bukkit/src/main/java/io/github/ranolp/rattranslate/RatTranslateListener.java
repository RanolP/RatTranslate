package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.abstraction.Player;
import io.github.ranolp.rattranslate.event.PlayerChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

class RatTranslateListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onChat(org.bukkit.event.player.PlayerChatEvent e) {
    Set<Player> set = new HashSet<>();
    Set<org.bukkit.entity.Player> toRemove = new HashSet<>();
    for (org.bukkit.entity.Player bukkitPlayer : e.getRecipients()) {
      Player player = BukkitPlayer.of(bukkitPlayer);
      if (player.getTranslateMode() && !e.getPlayer().equals(bukkitPlayer)) {
        set.add(player);
        toRemove.add(bukkitPlayer);
      }
    }
    e.getRecipients().removeAll(toRemove);
    PlayerChatEvent event = new PlayerChatEvent(BukkitPlayer.of(e.getPlayer()), e.getFormat(), set, e.getMessage());
    RatTranslate.getInstance().getEventBus().post(event);
  }
}
