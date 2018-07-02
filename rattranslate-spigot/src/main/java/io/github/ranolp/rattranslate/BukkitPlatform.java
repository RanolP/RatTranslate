package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.abstraction.Platform;
import io.github.ranolp.rattranslate.abstraction.Player;
import io.github.ranolp.rattranslate.config.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

final class BukkitPlatform extends Platform {
  private final File dataFile;
  private RatTranslateBukkit plugin;
  private BukkitConfiguration configurationSection;
  // todo: how to get?
  private boolean jsonMessageAvailable = true;
  private YamlConfiguration dataConfiguration;


  BukkitPlatform(RatTranslateBukkit plugin) {
    this.plugin = plugin;
    dataFile = new File(plugin.getDataFolder(), "data.yml");
  }

  @Override
  public Configuration getConfiguration() {
    return configurationSection;
  }

  @Override
  public boolean isJsonMessageAvailable() {
    return jsonMessageAvailable;
  }

  @Override
  public Player getPlayer(String name) {
    return BukkitPlayer.of(Bukkit.getPlayer(name));
  }

  @Override
  public String getName() {
    return "Bukkit";
  }

  @Override
  public String getVersion() {
    return plugin.getDescription().getVersion();
  }

  @Override
  public String getGameVersion() {
    return Bukkit.getBukkitVersion();
  }

  @Override
  public void sendMessage(String message) {
    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
  }

  @Override
  public boolean canReceiveMessage() {
    return true;
  }

  @Override
  public void reload() {
    plugin.reloadConfig();
    configurationSection = new BukkitConfiguration(plugin.getConfig());
    BukkitPlayer.PLAYER_MAP.clear();
    dataConfiguration = YamlConfiguration.loadConfiguration(dataFile);
    if (dataConfiguration.isList("players")) {
      // Deserialize on method call
      dataConfiguration.getList("players");
    }
  }

  @Override
  public void save() {
    dataConfiguration.set("players", new ArrayList<>(BukkitPlayer.PLAYER_MAP.values()));
    try {
      dataConfiguration.save(dataFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
