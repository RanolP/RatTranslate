package io.github.ranolp.rattranslate.translator;

import io.github.ranolp.rattranslate.BukkitConfiguration;
import io.github.ranolp.rattranslate.Locale;
import io.github.ranolp.rattranslate.util.GsonUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

public class PapagoTranslator implements Translator {
    private final Set<Locale> supportedLocales = new HashSet<>(
            Arrays.asList(Locale.KOREAN, Locale.AMERICAN_ENGLISH, Locale.JAPANESE, Locale.SIMPLIFIED_CHINESE,
                    Locale.TRADITIONAL_CHINESE, Locale.SPANISH, Locale.ARGENTINIAN_SPANISH, Locale.MEXICAN_SPANISH,
                    Locale.URUGUAYAN_SPANISH, Locale.VENEZUELAN_SPANISH, Locale.FRENCH, Locale.CANADIAN_FRENCH,
                    Locale.GERMAN, Locale.AUSTRIAN_GERMAN, Locale.LOW_GERMAN, Locale.SWABIAN_GERMAN, Locale.RUSSIAN,
                    Locale.PORTUGUESE, Locale.VIETNAMESE, Locale.THAI, Locale.INDONESIAN, Locale.HINDI
            ));

    private PapagoTranslator() {
    }

    public static PapagoTranslator getInstance() {
        return PapagoTranslator.SingletonHolder.INSTANCE;
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

    public String translate(String sentences, String from, String to) {
        if (from.equals(to)) {
            return sentences;
        }
        try {
            String apiURL = "https://papago.naver.com/apis/n2mt/translate";
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0");
            connection.addRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            String base = "rlWxnJA0Vwc0paIyLCJkaWN0RGlzcGxheSI6NSwic291cmNlIjoi";
            String str = String.format("%s\",\"target\":\"%s\",\"text\":\"%s\"}", from, to, sentences);
            String postParams = "data=" + base + Base64.getEncoder()
                    .encodeToString(str.getBytes(StandardCharsets.UTF_8));
            connection.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(postParams);
            dos.flush();
            dos.close();
            return GsonUtil.parse(connection.getInputStream(), StandardCharsets.UTF_8).getAsJsonObject().get("translatedText").getAsString();
        } catch (IOException | IllegalStateException | IndexOutOfBoundsException | UnsupportedOperationException | NullPointerException ignore) {
            // ignore all
        }
        return null;
    }

    @Override
    public String translate(String sentences, Locale from, Locale to) {
        return translate(sentences, code(from), code(to));
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
    public String translateAuto(String sentences, Locale to) {
        return translate(sentences, "auto", code(to));
    }

    private static final class SingletonHolder {
        private static final PapagoTranslator INSTANCE = new PapagoTranslator();
    }

}
