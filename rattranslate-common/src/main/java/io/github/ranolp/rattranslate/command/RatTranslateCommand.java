package io.github.ranolp.rattranslate.command;

import io.github.ranolp.rattranslate.RatTranslate;
import io.github.ranolp.rattranslate.abstraction.Player;
import io.github.ranolp.rattranslate.lang.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RatTranslateCommand implements Command {
  @Override
  public void onCommand(Player player, String label, String[] args) {
    if (args.length == 0) {
      player.setTranslateMode(!player.getTranslateMode());
    } else {
      switch (args[0]) {
        case "on":
          player.setTranslateMode(true);
          break;
        case "off":
          player.setTranslateMode(false);
          break;
        default:
          player.sendMessage(
              RatTranslate.getInstance().getLangStorage(),
              "chat.translate.error",
              Variable.ofAny("command", "command", "/" + label)
          );
      }
    }
  }

  @Override
  public List<String> onTabComplete(Player player, String label, String[] args) {
    if (args.length == 1) {
      List<String> result = new ArrayList<>(Arrays.asList("on", "off"));
      result.removeIf(it -> !it.startsWith(args[0].toLowerCase()));
      return result;
    } else {
      return Collections.emptyList();
    }
  }
}
