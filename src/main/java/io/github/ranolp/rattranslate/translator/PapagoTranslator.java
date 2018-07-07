package io.github.ranolp.rattranslate.translator;

import com.google.gson.JsonElement;
import io.github.ranolp.rattranslate.BukkitConfiguration;
import io.github.ranolp.rattranslate.Locale;
import io.github.ranolp.rattranslate.util.GsonUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PapagoTranslator implements Translator {

    public static PapagoTranslator getInstance() {
        return PapagoTranslator.SingletonHolder.INSTANCE;
    }

    @Override
    public void applyConfiguration(BukkitConfiguration section) {
    }

    @Override
    public boolean isLocaleSupported(Locale locale) {
        switch(locale) {
            case KOREAN:
            case AMERICAN_ENGLISH:
            case JAPANESE:
            case SIMPLIFIED_CHINESE:
            case TRADITIONAL_CHINESE:
            case SPANISH:
            case ARGENTINIAN_SPANISH:
            case MEXICAN_SPANISH:
            case URUGUAYAN_SPANISH:
            case VENEZUELAN_SPANISH:
            case FRENCH:
            case CANADIAN_FRENCH:
            case GERMAN:
            case AUSTRIAN_GERMAN:
            case LOW_GERMAN:
            case SWABIAN_GERMAN:
            case RUSSIAN:
            case PORTUGUESE:
            case VIETNAMESE:
            case THAI:
            case INDONESIAN:
            case HINDI:
                return true;
        }
        return false;
    }

    @Override
    public boolean isAutoSupported() {
        return true;
    }

    public String translate(String sentences, String from, String to) {
        if(from.equals(to)) {
            return sentences;
        }
        try {
            String apiURL = "http://papago.naver.com/apis/nsmt/translate";
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0");
            connection.addRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            String base = "rlWxnJA0Vwc0paIyLCJkaWN0RGlzcGxheSI6NSwic291cmNlIjoi";
            String str = String.format("%s\",\"target\":\"%s\",\"text\":\"%s\"}", from, to, sentences);
            String postParams = "data=" + base + Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
            connection.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(postParams);
            dos.flush();
            dos.close();
            BufferedReader br;
            if(connection.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }
            String line;
            StringBuilder response = new StringBuilder();
            while((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            JsonElement element = GsonUtil.parse(response.toString());
            try {
                return element.getAsJsonObject().get("translatedText").getAsString();
            } catch(NullPointerException ex) {
                return null;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
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
