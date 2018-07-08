package io.github.ranolp.rattranslate.lang;

import io.github.ranolp.rattranslate.Locale;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LangLoader {
    private LangLoader() {
        throw new UnsupportedOperationException("You cannot instantiate LangLoader");
    }

    public static Lang load(File file, Locale locale) {
        Map<String, FormattableText> translated = new HashMap<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            Properties properties = new Properties();
            properties.load(reader);
            for (String key : properties.stringPropertyNames()) {
                translated.put(key, new FormattableText(properties.getProperty(key)));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return new Lang(translated, locale);
    }
}
