package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.translator.GoogleApisTranslator;

public class Google extends TranslateTest{
    public Google() {
        super("Google", GoogleApisTranslator.getInstance());
    }
}
