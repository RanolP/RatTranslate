package io.github.ranolp.rattranslate.translator;

import io.github.ranolp.rattranslate.BukkitConfiguration;
import io.github.ranolp.rattranslate.Locale;

import java.util.List;
import java.util.Objects;

public class CompoundTranslator implements Translator {
    private List<Translator> translators;

    public CompoundTranslator(List<Translator> translators) {
        this.translators = Objects.requireNonNull(translators, "translators");
    }

    @Override
    public void applyConfiguration(BukkitConfiguration section) {
        // do not apply
    }

    @Override
    public boolean isLocaleSupported(Locale locale) {
        for (Translator it: translators) {
            if (it.isLocaleSupported(locale)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAutoSupported() {
        for (Translator it: translators) {
            if (it.isAutoSupported()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String translate(String sentences, Locale from, Locale to) {
        for (Translator it: translators) {
            if (it.isLocaleSupported(from) && it.isLocaleSupported(to)) {
                String result = it.translate(sentences, from, to);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    public String translateAuto(String sentences, Locale to) {
        for (Translator it: translators) {
            if (it.isAutoSupported() && it.isLocaleSupported(to)) {
                String result = it.translateAuto(sentences, to);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
