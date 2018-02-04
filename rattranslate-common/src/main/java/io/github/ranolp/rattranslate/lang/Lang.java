package io.github.ranolp.rattranslate.lang;

import io.github.ranolp.rattranslate.Locale;

import java.util.*;

public class Lang {
    private final Map<String, FormattableText> sentences;
    private final Locale locale;

    public Lang(Map<String, FormattableText> sentences, Locale locale) {
        this.sentences = Collections.unmodifiableMap(sentences);
        this.locale = locale;
    }

    public Optional<FormattableText> getOptional(String key) {
        return Optional.ofNullable(sentences.get(key));
    }

    public FormattableText get(String key) {
        return Optional.ofNullable(sentences.get(key))
                       .orElseThrow(() -> new IllegalStateException("The language " +
                                                                    locale.getName() +
                                                                    "(" +
                                                                    locale.getCode() +
                                                                    ") does not support key '" +
                                                                    key +
                                                                    "'"));
    }

    public Locale getLocale() {
        return locale;
    }

    public String format(String key, Variable... args) {
        return get(key).format(Arrays.asList(args), locale);
    }

    public String format(String key, List<Variable> args) {
        return get(key).format(args, locale);
    }
}
