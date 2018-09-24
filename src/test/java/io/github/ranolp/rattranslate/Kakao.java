package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.translator.KakaoTranslator;

public class Kakao extends TranslateTest{
    public Kakao() {
        super("Kakao", KakaoTranslator.getInstance());
    }
}
