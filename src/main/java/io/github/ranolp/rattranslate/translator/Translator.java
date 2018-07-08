package io.github.ranolp.rattranslate.translator;

import io.github.ranolp.rattranslate.BukkitConfiguration;
import io.github.ranolp.rattranslate.Locale;

import java.util.Set;

public interface Translator {
    void applyConfiguration(BukkitConfiguration section);

    Set<Locale> getSupportedLocales();

    default boolean isLocaleSupported(Locale locale) {
        return getSupportedLocales().contains(locale);
    }

    boolean isAutoSupported();

    String translate(String sentences, Locale from, Locale to);

    String translateAuto(String sentences, Locale to);
}
