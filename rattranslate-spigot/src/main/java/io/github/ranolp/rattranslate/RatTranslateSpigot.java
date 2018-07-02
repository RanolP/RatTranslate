package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.event.PlatformDisableEvent;
import io.github.ranolp.rattranslate.event.PlatformReadyEvent;
import io.github.ranolp.rattranslate.event.command.CommandIssueEvent;
import io.github.ranolp.rattranslate.event.command.CommandTabCompleteEvent;
import io.github.ranolp.rattranslate.lang.LangLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public final class RatTranslateSpigot extends JavaPlugin {

  @Override
  public void onEnable() {
    // noinspection ResultOfMethodCallIgnored
    getDataFolder().mkdirs();

    saveDefaultConfig();

    for (Locale locale: Locale.values()) {
      if (getResource("lang_" + locale.getCode() + ".properties") != null) {
        if (!new File("lang_" + locale.getCode() + ".lang").exists()) {
          saveResource("lang_" + locale.getCode() + ".properties", true);
        }
        RatTranslate.getInstance()
            .registerLang(
                LangLoader.load(new File(getDataFolder(), "lang_" + locale.getCode() + ".properties"),
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
    RatTranslate instance = RatTranslate.getInstance();
    CommandTabCompleteEvent e = new CommandTabCompleteEvent(BukkitPlayer.of((Player) sender), command.getLabel(), args);
    instance.getEventBus().post(e);
    return e.getCompletions();
  }
}
