package io.github.ranolp.rattranslate.lang;

import io.github.ranolp.rattranslate.Locale;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class LangStorage {
    private Locale defaultLocale;
    private Map<Locale, Lang> langs = new EnumMap<>(Locale.class);

    public LangStorage(Locale defaultLocale) {
        this.defaultLocale = Objects.requireNonNull(defaultLocale, "defaultLocale");
    }

    public LangStorage() {
        this(Locale.AMERICAN_ENGLISH);
    }

    public void addLang(Lang lang) {
        Objects.requireNonNull(lang, "lang");
        langs.put(lang.getLocale(), lang);
    }

    public FormattableText get(Locale locale, String key) {
        return langs.containsKey(locale)
               ? langs.get(locale).getOptional(key).orElse(langs.get(defaultLocale).get(key))
               : langs.get(defaultLocale).get(key);
    }
}
