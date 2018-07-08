package io.github.ranolp.rattranslate.translator;

import io.github.ranolp.rattranslate.BukkitConfiguration;
import io.github.ranolp.rattranslate.Locale;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CompoundTranslator implements Translator {
    private final Set<Locale> supportedLocales;
    private List<Translator> translators;

    public CompoundTranslator(List<Translator> translators) {
        this.translators = Objects.requireNonNull(translators, "translators");
        this.supportedLocales = this.translators.stream()
                                                .map(Translator::getSupportedLocales)
                                                .flatMap(Set::stream)
                                                .collect(Collectors.toSet());
    }

    @Override
    public void applyConfiguration(BukkitConfiguration section) {
        // do not apply
    }

    @Override
    public Set<Locale> getSupportedLocales() {
        return supportedLocales;
    }

    @Override
    public boolean isAutoSupported() {
        for (Translator it : translators) {
            if (it.isAutoSupported()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String translate(String sentences, Locale from, Locale to) {
        for (Translator it : translators) {
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
        for (Translator it : translators) {
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
