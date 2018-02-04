package io.github.ranolp.rattranslate.abstraction;

import io.github.ranolp.rattranslate.Locale;
import io.github.ranolp.rattranslate.RatTranslate;
import io.github.ranolp.rattranslate.config.Configuration;

public abstract class Platform implements MessageReceiver {
    public abstract Configuration getConfiguration();

    public abstract boolean isJsonMessageAvailable();

    public abstract Player getPlayer(String name);

    public abstract String getName();

    public abstract String getVersion();

    public abstract String getGameVersion();

    public final Locale getLocale() {
        return RatTranslate.getInstance().getServerLocale();
    }

    public abstract void sendMessage(String message);


    /**
     * Reload the configuration.
     */
    public abstract void reload();

    public abstract void save();
}
