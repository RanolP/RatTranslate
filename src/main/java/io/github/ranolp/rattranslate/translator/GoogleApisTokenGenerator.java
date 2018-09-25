package io.github.ranolp.rattranslate.translator;

import io.github.ranolp.rattranslate.util.Util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleApisTokenGenerator {
    private static final Pattern TKK_REGEX = Pattern.compile("TKK='(.*?)';");
    private static final ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");
    private static final String SCRIPT = "(function(a){xr=function(a,b){for(var c=0;c<b.length-2;c+=3){var d=b.charAt(c+2),d=\"a\"<=d?d.charCodeAt(0)-87:Number(d),d=\"+\"==b.charAt(c+1)?a>>>d:a<<d;a=\"+\"==b.charAt(c)?a+d&4294967295:a^d};return a};var b=TKK;var d=b.split(\".\");b=Number(d[0])||0;for(var e=[],f=0,g=0;g<a.length;g++){var l=a.charCodeAt(g);128>l?e[f++]=l:(2048>l?e[f++]=l>>6|192:(55296==(l&64512)&&g+1<a.length&&56320==(a.charCodeAt(g+1)&64512)?(l=65536+((l&1023)<<10)+(a.charCodeAt(++g)&1023),e[f++]=l>>18|240,e[f++]=l>>12&63|128):e[f++]=l>>12|224,e[f++]=l>>6&63|128),e[f++]=l&63|128)};a=b;for(f=0;f<e.length;f++) a+=e[f],a=xr(a,\"+-a^+6\");a=xr(a,\"+-3^+b+-f\");a^=Number(d[1])||0;0>a&&(a=(a&2147483647)+2147483648);a%=1E6;return a.toString()+\".\"+(a^b)})(text);";

    public String generate(String text) {
        try {
            SCRIPT_ENGINE.put("text", text);
            return (String) SCRIPT_ENGINE.eval(SCRIPT);
        } catch (ScriptException ex) {
            return null;
        }
    }

    public void updateTKK() {
        try {
            String url = "https://translate.google.com";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            String response = Util.readFully(connection.getInputStream());
            Matcher match = TKK_REGEX.matcher(response);
            if (match.find()) {
                SCRIPT_ENGINE.eval(match.group());
            }
        } catch (IOException | ScriptException ex) {
            ex.printStackTrace();
            // ignore all
        }
    }

    private int xr(int a, String b) {
        for (int c = 0; c < b.length(); c += 3) {
            int d = b.charAt(c + 2);
            d = 'a' <= d ? d - 87 : d;
            d = '+' == b.charAt(c + 1) ? a >>> d : a << d;
            a = '+' == b.charAt(c) ? a + d : a ^ d;
        }
        return a;
    }
}
