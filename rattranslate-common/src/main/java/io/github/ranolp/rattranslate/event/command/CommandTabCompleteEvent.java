package io.github.ranolp.rattranslate.event.command;

import io.github.ranolp.rattranslate.abstraction.Player;
import io.github.ranolp.rattranslate.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CommandTabCompleteEvent extends Event {
  private final Player player;
  private final String label;
  private final String[] args;
  private final List<String> completions = new ArrayList<>();

  public CommandTabCompleteEvent(Player player, String label, String[] args) {
    this.player = player;
    this.label = label;
    this.args = args;
  }

  public Player getPlayer() {
    return player;
  }

  public String getLabel() {
    return label;
  }

  public String[] getArgs() {
    return args;
  }

  public void setCompletions(Collection<String> completions) {
    this.completions.clear();
    this.completions.addAll(completions);
  }

  public void setCompletions(String... completions) {
    this.completions.clear();
    this.completions.addAll(Arrays.asList(completions));
  }

  public void addCompletions(Collection<String> completions) {
    this.completions.addAll(completions);
  }

  public void addCompletions(String... completions) {
    this.completions.addAll(Arrays.asList(completions));
  }

  public void removeCompletions(Collection<String> completions) {
    this.completions.removeAll(completions);
  }

  public void removeCompletions(String... completions) {
    this.completions.removeAll(Arrays.asList(completions));
  }

  public void clearCompletions() {
    this.completions.clear();
  }

  public List<String> getCompletions() {
    return new ArrayList<>(completions);
  }
}
