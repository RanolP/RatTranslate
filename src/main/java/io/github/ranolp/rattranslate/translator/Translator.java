package io.github.ranolp.rattranslate.translator;

import io.github.ranolp.rattranslate.BukkitConfiguration;
import io.github.ranolp.rattranslate.Locale;

public interface Translator {
  void applyConfiguration(BukkitConfiguration section);

  boolean isLocaleSupported(Locale locale);

  boolean isAutoSupported();

  String translate(String sentences, Locale from, Locale to);

  String translateAuto(String sentences, Locale to);
}
