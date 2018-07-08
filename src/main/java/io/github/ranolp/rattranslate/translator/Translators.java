package io.github.ranolp.rattranslate.translator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public final class Translators {
    private static final Map<String, Supplier<Translator>> SUPPLIERS = new HashMap<>();

    static {
        register("GoogleApis", GoogleApisTranslator::getInstance);
        register("Papago", PapagoTranslator::getInstance);
    }

    private Translators() {
        throw new UnsupportedOperationException("You cannot instantiate Translators");
    }

    public static void register(String name, Supplier<Translator> supplier) {
        if (SUPPLIERS.containsKey(name)) {
            throw new IllegalStateException("Duplicated name");
        }
        SUPPLIERS.put(name, supplier);
    }

    public static Optional<Translator> get(String name) {
        return Optional.ofNullable(SUPPLIERS.get(name)).map(Supplier::get);
    }
}
