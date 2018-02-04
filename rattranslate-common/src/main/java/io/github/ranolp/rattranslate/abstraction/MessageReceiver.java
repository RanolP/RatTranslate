package io.github.ranolp.rattranslate.abstraction;

import io.github.ranolp.rattranslate.Locale;
import io.github.ranolp.rattranslate.lang.FormattableText;
import io.github.ranolp.rattranslate.lang.LangStorage;
import io.github.ranolp.rattranslate.lang.Variable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface MessageReceiver {
    Locale getLocale();

    void sendMessage(String message);

    default void sendMessage(LangStorage storage, String key, List<Variable> variables) {
        sendMessage(format(storage.get(getLocale(), key), variables));
    }

    default void sendMessage(LangStorage storage, String key, Variable... variables) {
        sendMessage(format(storage.get(getLocale(), key), Arrays.asList(variables)));
    }

    default void sendMessage(FormattableText text, Variable... variables) {
        sendMessage(format(text, Arrays.asList(variables)));
    }

    default void sendMessage(FormattableText text, List<Variable> variables) {
        sendMessage(format(text, variables));
    }

    default String format(LangStorage storage, String key) {
        return format(storage.get(getLocale(), key), Collections.emptyList());
    }

    default String format(LangStorage storage, String key, List<Variable> variables) {
        return format(storage.get(getLocale(), key), variables);
    }

    default String format(LangStorage storage, String key, Variable... variables) {
        return format(storage.get(getLocale(), key), Arrays.asList(variables));
    }

    default String format(FormattableText text) {
        return text.format(Collections.emptyList(), getLocale());
    }

    default String format(FormattableText text, List<Variable> variables) {
        return text.format(variables, getLocale());
    }

    default String format(FormattableText text, Variable... variables) {
        return text.format(Arrays.asList(variables), getLocale());
    }
}
