package io.github.ranolp.rattranslate.translator;

import com.google.gson.JsonElement;
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

public class GoogleApisTranslator implements Translator {
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
    public boolean isLocaleSupported(Locale locale) {
        switch (locale) {
            case AFRIKAANS:
            case ARABIC:
            case AZERBAIJANI:
            case BELARUSIAN:
            case BULGARIAN:
            case CATALAN:
            case CZECH:
            case WELSH:
            case DANISH:
            case AUSTRIAN_GERMAN:
            case GERMAN:
            case GREEK:
            case AUSTRALIAN_ENGLISH:
            case CANADIAN_ENGLISH:
            case BRITISH_ENGLISH:
            case NEW_ZEALAND_ENGLISH:
            case PIRATE_ENGLISH:
            case AMERICAN_ENGLISH:
            case ESPERANTO:
            case ARGENTINIAN_SPANISH:
            case SPANISH:
            case MEXICAN_SPANISH:
            case URUGUAYAN_SPANISH:
            case VENEZUELAN_SPANISH:
            case ESTONIAN:
            case BASQUE:
            case PERSIAN:
            case FINNISH:
            case FILIPINO:
            case FRENCH:
            case CANADIAN_FRENCH:
            case FRISIAN:
            case IRISH:
            case SCOTTISH_GAELIC:
            case GALICIAN:
            case HAWAIIAN:
            case HEBREW:
            case HINDI:
            case CROATIAN:
            case HUNGARIAN:
            case ARMENIAN:
            case INDONESIAN:
            case ICELANDIC:
            case ITALIAN:
            case JAPANESE:
            case GEORGIAN:
            case KOREAN:
            case LATIN:
            case LUXEMBOURGISH:
            case LITHUANIAN:
            case LATVIAN:
            case MAORI:
            case MACEDONIAN:
            case MONGOLIAN:
            case MALAY:
            case MALTESE:
            case DUTCH:
            case NORWEGIAN_NYNORSK:
            case NORWEGIAN1:
            case NORWEGIAN2:
            case POLISH:
            case BRAZILIAN_PORTUGUESE:
            case PORTUGUESE:
            case ROMANIAN:
            case RUSSIAN:
            case SLOVAK:
            case SLOVENIAN:
            case SOMALI:
            case ALBANIAN:
            case SERBIAN:
            case SWEDISH:
            case THAI:
            case TAGALOG:
            case TURKISH:
            case UKRAINIAN:
            case VALENCIAN:
            case VIETNAMESE:
            case SIMPLIFIED_CHINESE:
            case TRADITIONAL_CHINESE:
                return true;
            default:
                return false;
        }
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
            String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" +
                         from +
                         "&tl=" +
                         to +
                         "&dt=t&q=" +
                         URLEncoder.encode(sentences, "UTF-8");
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),
                    StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String data;
                while ((data = reader.readLine()) != null) {
                    response.append(data);
                }
                JsonElement element = GsonUtil.parse(response);
                return element.getAsJsonArray().get(0).getAsJsonArray().get(0).getAsJsonArray().get(0).getAsString();
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
