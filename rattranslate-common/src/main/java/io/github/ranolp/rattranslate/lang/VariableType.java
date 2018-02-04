package io.github.ranolp.rattranslate.lang;

import io.github.ranolp.rattranslate.Locale;

public interface VariableType {
    String getName();

    VariableFormatter getDefaultFormatter(Locale locale);
}
