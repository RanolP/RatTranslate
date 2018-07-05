package io.github.ranolp.rattranslate.command;

import io.github.ranolp.rattranslate.Locale;
import io.github.ranolp.rattranslate.RatPlayer;
import io.github.ranolp.rattranslate.RatTranslate;
import io.github.ranolp.rattranslate.lang.Variable;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LanguageCommand implements TabExecutor {
    private List<String> localeCodes = Arrays.stream(Locale.values()).
            map(Locale::getCode).
                                                     map(String::toLowerCase).
                                                     collect(Collectors.toList());

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', RatTranslate.getInstance()
                                                                                       .format(RatTranslate.getInstance()
                                                                                                           .getLangStorage(),
                                                                                               "command.require-player")));
            return false;
        }
        RatPlayer player = RatPlayer.of(((Player) sender));
        if (args.length == 0) {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.lang.usage");
            return false;
        }
        if (localeCodes.contains(args[0].toLowerCase())) {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.lang.set.failed");
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "reset": {
                _reset(player);
                return true;
            }
            case "list": {
                return _list(player, label, args);
            }
            case "set": {
                return _set(player, args);
            }
            case "show": {
                _show(player);
                return true;
            }
            default: {
                player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.lang.usage");
                return false;
            }
        }
    }

    private void _reset(RatPlayer player) {
        player.setCustomLocale(null);
        player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.lang.reset");
    }

    private boolean _list(RatPlayer player, String label, String[] args) {
        List<Locale> values = Arrays.asList(Locale.values());
        int page = 1;
        if (args.length == 2) {
            try {
                page = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                // ignore error
            }
        }
        int maxPage = values.size() % 8 == 0 ? values.size() / 8 : values.size() / 8 + 1;
        if (page > maxPage) {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.page-over",
                               Variable.ofNumber("page", "page", page), Variable.ofNumber("page", "maxPage", maxPage));
            return false;
        }
        player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.lang.list.header");
        int max = Math.min(page * 8, values.size());
        for (int i = (page - 1) * 8; i < max; i++) {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.page-column",
                               Variable.ofAny("language", "name",
                                              player.format(RatTranslate.getInstance().getLangStorage(),
                                                            values.get(i).toPropertiesKey())),
                               Variable.ofAny("language", "code", values.get(i).getCode()));
        }
        if (page == maxPage) {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.page-footer-end");
        } else {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.page-footer",
                               Variable.ofAny("command", "command", label + " list " + (page + 1)));
        }
        return true;
    }

    private boolean _set(RatPlayer player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.lang.set.require-locale");
            return false;
        }
        Locale locale = Locale.getByCode(args[1]);
        if (locale == null) {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.lang.set.invalid-locale");
            return false;
        }
        player.setCustomLocale(locale);
        player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.lang.set.success",
                           Variable.ofAny("new", "language", player.format(RatTranslate.getInstance().getLangStorage(),
                                                                           locale.toPropertiesKey()) +
                                                             " (" +
                                                             locale.getCode() +
                                                             ")"));
        return true;
    }

    private void _show(RatPlayer player) {
        Locale locale = player.getLocale();
        player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.lang.show",
                           Variable.ofAny("lang", "lang", locale.getName() + "(" + locale.getCode() + ")"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }
        if (args.length == 1) {
            List<String> result = new ArrayList<>(Arrays.asList("reset", "list", "set", "show"));
            result.removeIf(it -> !it.startsWith(args[0].toLowerCase()));
            return result;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            List<String> result = new ArrayList<>(localeCodes);
            result.removeIf(it -> !it.startsWith(args[1].toLowerCase()));
            return result;
        } else {
            return Collections.emptyList();
        }
    }
}
