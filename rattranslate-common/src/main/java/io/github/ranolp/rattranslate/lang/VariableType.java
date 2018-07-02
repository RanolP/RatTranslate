package io.github.ranolp.rattranslate.lang;

import io.github.ranolp.rattranslate.Locale;

public interface VariableType {
  String getName();

  default boolean isSupported(VariableType type) {
    return equals(type);
  }

  VariableFormatter getDefaultFormatter(Locale locale);
}
