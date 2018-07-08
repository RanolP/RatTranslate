package io.github.ranolp.rattranslate.command;

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

public class RatTranslateCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&',
                    RatTranslate.getInstance()
                                .format(RatTranslate.getInstance().getLangStorage(), "command.require-player")
            ));
            return false;
        }
        RatPlayer player = RatPlayer.of(((Player) sender));
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
                    return false;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> result = new ArrayList<>(Arrays.asList("on", "off"));
            result.removeIf(it -> !it.startsWith(args[0].toLowerCase()));
            return result;
        } else {
            return Collections.emptyList();
        }
    }
}
