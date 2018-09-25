package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.translator.OldwayGoogleApisTranslator;

public class OldwayGoogle extends TranslateTest {
    public OldwayGoogle() {
        super("Google(Old Way)", OldwayGoogleApisTranslator.getInstance());
    }
}
