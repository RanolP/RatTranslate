package io.github.ranolp.rattranslate.translator;

import io.github.ranolp.rattranslate.Locale;
import io.github.ranolp.rattranslate.config.Configuration;

public interface Translator {
  void applyConfiguration(Configuration section);

  boolean isLocaleSupported(Locale locale);

  boolean isAutoSupported();

  String translate(String sentences, Locale from, Locale to);

  String translateAuto(String sentences, Locale to);
}
