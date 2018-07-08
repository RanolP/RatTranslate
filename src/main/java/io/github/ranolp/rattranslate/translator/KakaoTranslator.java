package io.github.ranolp.rattranslate.translator;

import com.google.gson.JsonElement;
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

public class KakaoTranslator implements Translator {
    private KakaoTranslator() {
    }

    public static KakaoTranslator getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void applyConfiguration(BukkitConfiguration section) {

    }

    @Override
    public boolean isLocaleSupported(Locale locale) {
        switch (locale) {
            case AMERICAN_ENGLISH:
            case AUSTRALIAN_ENGLISH:
            case BRITISH_ENGLISH:
            case BRITISH_ENGLISH_UPSIDE_DOWN:
            case CANADIAN_ENGLISH:
            case NEW_ZEALAND_ENGLISH:
            case PIRATE_ENGLISH:
            case KOREAN:
            case JAPANESE:
            case SIMPLIFIED_CHINESE:
            case TRADITIONAL_CHINESE:
                return true;
            default:
                return false;
        }
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
                    StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String data;
                while ((data = reader.readLine()) != null) {
                    response.append(data);
                }
                JsonElement element = GsonUtil.parse(response);
                return element.getAsJsonObject().get("result").getAsString();
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
