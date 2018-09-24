package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.translator.AutoableTranslator;
import io.github.ranolp.rattranslate.translator.KakaoTranslator;

public class AutoKakao extends TranslateTest {
    public AutoKakao() {
        super("Auto(Kakao)", new AutoableTranslator(KakaoTranslator.getInstance()));
    }

}
