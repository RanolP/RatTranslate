package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.event.PlatformDisableEvent;
import io.github.ranolp.rattranslate.event.PlatformReadyEvent;
import io.github.ranolp.rattranslate.event.command.CommandIssueEvent;
import io.github.ranolp.rattranslate.lang.LangLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public final class RatTranslateBukkit extends JavaPlugin {

  @Override
  public void onEnable() {
    // noinspection ResultOfMethodCallIgnored
    getDataFolder().mkdirs();

    saveDefaultConfig();

    File lang = new File(getDataFolder(), "lang");
    if (!lang.exists()) {
      // noinspection ResultOfMethodCallIgnored
      lang.mkdirs();
    }
    for (Locale locale: Locale.values()) {
      if (getResource("lang/" + locale.getCode() + ".lang") != null) {
        if (!new File(lang, locale.getCode() + ".lang").exists()) {
          saveResource("lang/" + locale.getCode() + ".lang", true);
        }
        RatTranslate.getInstance()
            .registerLang(
                LangLoader.load(new File(getDataFolder(), "/lang/" + locale.getCode() + ".lang"),
                    locale));
      }
    }

    Bukkit.getPluginManager().registerEvents(new RatTranslateListener(), this);

    // ok, it's ready!
    RatTranslate.getInstance().getEventBus().post(new PlatformReadyEvent(new BukkitPlatform(this)));
  }

  @Override
  public void onDisable() {
    RatTranslate.getInstance().getEventBus().post(new PlatformDisableEvent());
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    RatTranslate instance = RatTranslate.getInstance();
    if (!(sender instanceof Player)) {
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getPlatform().format(instance.getLangStorage(), "command.require-player")));
    } else {
      instance.getEventBus().post(new CommandIssueEvent(BukkitPlayer.of((Player) sender), label, args));
    }
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    return null;
  }
}
