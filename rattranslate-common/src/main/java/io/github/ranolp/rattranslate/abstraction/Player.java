package io.github.ranolp.rattranslate.abstraction;

import io.github.ranolp.rattranslate.Locale;
import io.github.ranolp.rattranslate.RatTranslate;

public abstract class Player implements MessageReceiver {
  private boolean translateMode = true;
  private Locale customLocale = null;

  public abstract String getDisplayName();

  public abstract Locale getRealLocale();

  public final Locale getLocale() {
    return getCustomLocale() != null ? getCustomLocale() : getRealLocale();
  }

  /**
   * Send hoverable message if it supported.
   *
   * @param message - The text to see on chat.
   * @param onHover - The text to see on hover.
   */
  public abstract void sendHoverableMessage(String message, String onHover);

  public boolean getTranslateMode() {
    return translateMode;
  }

  public void setTranslateMode(boolean useTranslate) {
    this.translateMode = useTranslate;
    if (useTranslate) {
      sendMessage(RatTranslate.getInstance().getLangStorage(), "chat.translate.start");
    } else {
      sendMessage(RatTranslate.getInstance().getLangStorage(), "chat.translate.stop");
    }
  }

  public Locale getCustomLocale() {
    return customLocale;
  }

  public void setCustomLocale(Locale customLocale) {
    this.customLocale = customLocale;
  }
}
