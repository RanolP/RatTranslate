package io.github.ranolp.rattranslate.translator;

import io.github.ranolp.rattranslate.BukkitConfiguration;
import io.github.ranolp.rattranslate.Locale;
import org.bukkit.Bukkit;

import java.util.Set;

public class AutoableTranslator implements Translator {
    private Translator real = NopTranslator.getInstance();

    public AutoableTranslator() {
    }

    public AutoableTranslator(Translator real) {
        this.real = real;
    }

    @Override
    public void applyConfiguration(BukkitConfiguration section) {
        section.getString("instance").ifPresent(name -> {
            Translator instance = Translators.get(name).orElse(null);
            if (instance == null) {
                Bukkit.getLogger().warning("Translator " + name + " is not found");
            } else {
                this.real = instance;
            }
        });
    }

    @Override
    public Set<Locale> getSupportedLocales() {
        return real.getSupportedLocales();
    }

    @Override
    public boolean isAutoSupported() {
        return true;
    }

    @Override
    public String translate(String sentences, Locale from, Locale to) {
        return real.translate(sentences, from, to);
    }

    @Override
    public String translateAuto(String sentences, Locale to) {
        return real.isAutoSupported()
               ? real.translateAuto(sentences, to)
               : getSupportedLocales().stream()
                                      .filter(Locale::predictSupported)
                                      .max((a, b) -> Float.compare(a.predict(sentences), b.predict(sentences)))
                                      .map(from -> real.translate(sentences, from, to))
                                      .orElse(sentences);
    }
}
