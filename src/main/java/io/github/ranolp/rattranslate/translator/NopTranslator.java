package io.github.ranolp.rattranslate.translator;

import io.github.ranolp.rattranslate.BukkitConfiguration;
import io.github.ranolp.rattranslate.Locale;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NopTranslator implements Translator {
    private final Set<Locale> supportedLocales = new HashSet<>(Arrays.asList(Locale.values()));

    private NopTranslator() {
    }

    public static NopTranslator getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void applyConfiguration(BukkitConfiguration section) {

    }

    @Override
    public Set<Locale> getSupportedLocales() {
        return supportedLocales;
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
