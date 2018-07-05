package io.github.ranolp.rattranslate.locale;

import org.bukkit.entity.Player;

public class SimpleLocaleDelegate implements LocaleDelegate {
    private SimpleLocaleDelegate() {
    }

    public static SimpleLocaleDelegate getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String getLocale(Player player) {
        return player.getLocale();
    }

    private static final class SingletonHolder {
        private static final SimpleLocaleDelegate INSTANCE = new SimpleLocaleDelegate();
    }
}
