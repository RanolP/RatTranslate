package io.github.ranolp.rattranslate.translator;

import io.github.ranolp.rattranslate.BukkitConfiguration;
import io.github.ranolp.rattranslate.Locale;

public class NopTranslator implements Translator {
  private NopTranslator() {
  }

  public static NopTranslator getInstance() {
    return SingletonHolder.INSTANCE;
  }

  @Override
  public void applyConfiguration(BukkitConfiguration section) {

  }

  @Override
  public boolean isLocaleSupported(Locale locale) {
    return true;
  }

  @Override
  public boolean isAutoSupported() {
    return true;
  }

  @Override
  public String translate(String sentences, Locale from, Locale to) {
    return sentences;
  }

  @Override
  public String translateAuto(String sentences, Locale to) {
    return sentences;
  }

  private static final class SingletonHolder {
    private static final NopTranslator INSTANCE = new NopTranslator();
  }
}
