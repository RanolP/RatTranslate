package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.translator.PapagoTranslator;

public class Papago extends TranslateTest{
    public Papago() {
        super("Papago", PapagoTranslator.getInstance());
    }
}
