package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.translator.GoogleApisTranslator;
import io.github.ranolp.rattranslate.translator.Translator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TranslateTest {

  @Test
  public void englishKoreanTest() {
    Translator translator = GoogleApisTranslator.getInstance();
    assertTrue(translator.isLocaleSupported(Locale.AMERICAN_ENGLISH));
    assertTrue(translator.isLocaleSupported(Locale.KOREAN));

    System.out.println(translator.translate("Hello", Locale.AMERICAN_ENGLISH, Locale.KOREAN));
    System.out.println(translator.translate("안녕", Locale.KOREAN, Locale.AMERICAN_ENGLISH));

    assertTrue(translator.isAutoSupported());

    System.out.println(translator.translateAuto("Hello", Locale.KOREAN));
    System.out.println(translator.translateAuto("안녕", Locale.AMERICAN_ENGLISH));
  }
}
