package io.github.ranolp.rattranslate.command;

import io.github.ranolp.rattranslate.abstraction.Player;

import java.util.List;

public interface Command {
  void onCommand(Player player, String label, String[] args);

  List<String> onTabComplete(Player player, String label, String[] args);
}
