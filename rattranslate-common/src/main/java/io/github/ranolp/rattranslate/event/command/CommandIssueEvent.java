package io.github.ranolp.rattranslate.event.command;

import io.github.ranolp.rattranslate.abstraction.Player;
import io.github.ranolp.rattranslate.event.Event;

public class CommandIssueEvent extends Event {
    private final Player player;
    private final String label;
    private final String[] args;

    public CommandIssueEvent(Player player, String label, String[] args) {
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
}
