package io.github.ranolp.rattranslate.translator;

import io.github.ranolp.rattranslate.BukkitConfiguration;
import io.github.ranolp.rattranslate.Locale;
import io.github.ranolp.rattranslate.util.GsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class KakaoTranslator implements Translator {
    private final Set<Locale> supportedLocales = new HashSet<>(Arrays.asList(Locale.AMERICAN_ENGLISH,
            Locale.AUSTRALIAN_ENGLISH,
            Locale.BRITISH_ENGLISH,
            Locale.BRITISH_ENGLISH_UPSIDE_DOWN,
            Locale.CANADIAN_ENGLISH,
            Locale.NEW_ZEALAND_ENGLISH,
            Locale.PIRATE_ENGLISH,
            Locale.KOREAN,
            Locale.JAPANESE,
            Locale.SIMPLIFIED_CHINESE,
            Locale.TRADITIONAL_CHINESE
    ));

    private KakaoTranslator() {
    }

    public static KakaoTranslator getInstance() {
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
        return false;
    }

    private String translate(String sentences, String from, String to) {
        if (from.equals(to)) {
            return sentences;
        }
        try {
            String url = "https://translate.kakao.com/translator/translate.json";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.addRequestProperty("Origin", "https://translate.kakao.com");
            connection.addRequestProperty("Referer", "https://translate.kakao.com/");

            connection.setDoOutput(true);
            try (OutputStream stream = connection.getOutputStream()) {
                stream.write(String.format("lang=%s%s&q=%s", from, to, URLEncoder.encode(sentences, "UTF-8"))
                                   .getBytes(StandardCharsets.UTF_8));
                stream.flush();
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),
                    StandardCharsets.UTF_8
            ))) {
                return GsonUtil.parse(reader).getAsJsonObject().get("result").getAsString();
            }
        } catch (IOException | IllegalStateException | ClassCastException | UnsupportedOperationException ignore) {
            // ignore all
        }
        return null;
    }

    private String code(Locale locale) {
        switch (locale) {
            case KOREAN:
                return "kr";
            case JAPANESE:
                return "jp";
            case SIMPLIFIED_CHINESE:
            case TRADITIONAL_CHINESE:
                return "cn";
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
        return sentences;
    }

    private static final class SingletonHolder {
        private static final KakaoTranslator INSTANCE = new KakaoTranslator();
    }
}
