package io.github.ranolp.rattranslate.translator;

import io.github.ranolp.rattranslate.BukkitConfiguration;
import io.github.ranolp.rattranslate.Locale;
import io.github.ranolp.rattranslate.util.GsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GoogleApisTranslator implements Translator {
    private final Set<Locale> supportedLocales = new HashSet<>(
            Arrays.asList(Locale.AFRIKAANS, Locale.ARABIC, Locale.AZERBAIJANI, Locale.BELARUSIAN, Locale.BULGARIAN,
                    Locale.CATALAN, Locale.CZECH, Locale.WELSH, Locale.DANISH, Locale.AUSTRIAN_GERMAN, Locale.GERMAN,
                    Locale.GREEK, Locale.AUSTRALIAN_ENGLISH, Locale.CANADIAN_ENGLISH, Locale.BRITISH_ENGLISH,
                    Locale.NEW_ZEALAND_ENGLISH, Locale.PIRATE_ENGLISH, Locale.AMERICAN_ENGLISH, Locale.ESPERANTO,
                    Locale.ARGENTINIAN_SPANISH, Locale.SPANISH, Locale.MEXICAN_SPANISH, Locale.URUGUAYAN_SPANISH,
                    Locale.VENEZUELAN_SPANISH, Locale.ESTONIAN, Locale.BASQUE, Locale.PERSIAN, Locale.FINNISH,
                    Locale.FILIPINO, Locale.FRENCH, Locale.CANADIAN_FRENCH, Locale.FRISIAN, Locale.IRISH,
                    Locale.SCOTTISH_GAELIC, Locale.GALICIAN, Locale.HAWAIIAN, Locale.HEBREW, Locale.HINDI,
                    Locale.CROATIAN, Locale.HUNGARIAN, Locale.ARMENIAN, Locale.INDONESIAN, Locale.ICELANDIC,
                    Locale.ITALIAN, Locale.JAPANESE, Locale.GEORGIAN, Locale.KOREAN, Locale.LATIN, Locale.LUXEMBOURGISH,
                    Locale.LITHUANIAN, Locale.LATVIAN, Locale.MAORI, Locale.MACEDONIAN, Locale.MONGOLIAN, Locale.MALAY,
                    Locale.MALTESE, Locale.DUTCH, Locale.NORWEGIAN_NYNORSK, Locale.NORWEGIAN1, Locale.NORWEGIAN2,
                    Locale.POLISH, Locale.BRAZILIAN_PORTUGUESE, Locale.PORTUGUESE, Locale.ROMANIAN, Locale.RUSSIAN,
                    Locale.SLOVAK, Locale.SLOVENIAN, Locale.SOMALI, Locale.ALBANIAN, Locale.SERBIAN, Locale.SWEDISH,
                    Locale.THAI, Locale.TAGALOG, Locale.TURKISH, Locale.UKRAINIAN, Locale.VALENCIAN, Locale.VIETNAMESE,
                    Locale.SIMPLIFIED_CHINESE, Locale.TRADITIONAL_CHINESE
            ));

    private GoogleApisTokenGenerator generator = new GoogleApisTokenGenerator();

    private GoogleApisTranslator() {
    }

    public static GoogleApisTranslator getInstance() {
        return SingletonHolder.INSTANCE;
    }


    @Override
    public void applyConfiguration(BukkitConfiguration section) {
        // do nothing
    }

    @Override
    public Set<Locale> getSupportedLocales() {
        return supportedLocales;
    }

    @Override
    public boolean isAutoSupported() {
        return true;
    }

    private String translate(String sentences, String from, String to) {
        if (from.equals(to)) {
            return sentences;
        }
        try {
            String token = generator.generate(sentences);
            if (token == null) {
                generator.updateTKK();
                token = generator.generate(sentences);
            }
            String url = "https://translate.googleapis.com/translate_a/single?client=t" + "&tk=" + token + "&sl=" + from + "&tl=" + to + "&dt=t&q=" + URLEncoder
                    .encode(sentences, "UTF-8");
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                return GsonUtil.parse(reader).getAsJsonArray().get(0).getAsJsonArray().get(0).getAsJsonArray().get(0).getAsString();
            }
        } catch (IOException | IllegalStateException | IndexOutOfBoundsException | UnsupportedOperationException ignore) {
            // ignore all
        }
        return null;
    }

    private String code(Locale locale) {
        switch (locale) {
            case FILIPINO:
                return "tl";
            case HEBREW:
                return "iw";
            case NORWEGIAN_NYNORSK:
            case NORWEGIAN2:
                return "no";
            case VALENCIAN:
                return "ca";
            case SIMPLIFIED_CHINESE:
                return "zh-CN";
            case TRADITIONAL_CHINESE:
                return "zh-TW";
            default:
                return locale.getLanguageCode();
        }
    }

    @Override
    public String translate(String sentences, Locale from, Locale to) {
        return translate(sentences, code(from), code(to));
    }

    @Override
    public String translateAuto(String sentences, Locale to) {
        return translate(sentences, "auto", code(to));
    }

    private static final class SingletonHolder {
        private static final GoogleApisTranslator INSTANCE = new GoogleApisTranslator();
    }
}
