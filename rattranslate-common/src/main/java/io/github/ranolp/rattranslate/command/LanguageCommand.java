package io.github.ranolp.rattranslate.command;

import io.github.ranolp.rattranslate.Locale;
import io.github.ranolp.rattranslate.RatTranslate;
import io.github.ranolp.rattranslate.abstraction.Player;
import io.github.ranolp.rattranslate.lang.Variable;

import java.util.Arrays;
import java.util.List;

public class LanguageCommand implements Command {
  @Override
  public void onCommand(Player player, String label, String[] args) {
    if (args.length == 0) {

    } else {
      switch (args[0]) {
        case "reset":
          player.setCustomLocale(null);
          player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.reset-locale");
          break;
        case "list":
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
                Variable.ofNumber("page", "page", page),
                Variable.ofNumber("page", "maxPage", maxPage));
            return;
          }
          player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.page-header");
          int max = Math.min(page * 8, values.size());
          for (int i = (page - 1) * 8; i < max; i++) {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.page-column",
                Variable.ofAny("language", "name",
                    player.format(RatTranslate.getInstance().getLangStorage(),
                        "lang." + values.get(i).toPropertiesKey())),
                Variable.ofAny("language", "code", values.get(i).getCode()));
          }
          if (page == maxPage) {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.page-footer-end");
          } else {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.page-footer",
                Variable.ofAny("command", "command", "/" + label + " " + (page + 1)));
          }
          break;
        case "set":
          if (args.length < 2) {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(),
                "command.set-locale.require-locale");
            return;
          }
          Locale locale = Locale.getByCode(args[1]);
          if (locale == null) {
            player.sendMessage(RatTranslate.getInstance().getLangStorage(),
                "command.set-locale.invalid-locale");
            return;
          }
          player.setCustomLocale(locale);
          player.sendMessage(RatTranslate.getInstance().getLangStorage(), "command.set-locale.success",
              Variable.ofAny("new", "language",
                  player.format(RatTranslate.getInstance().getLangStorage(),
                      "lang." + locale.toPropertiesKey()) +
                      " (" +
                      locale.getCode() +
                      ")"));
          break;
      }
    }
  }

  @Override
  public List<String> onTabComplete(Player player, String label, String[] args) {
    return null;
  }
}
