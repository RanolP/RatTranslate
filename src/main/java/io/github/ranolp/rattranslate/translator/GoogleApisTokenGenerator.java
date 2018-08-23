package io.github.ranolp.rattranslate.translator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleApisTokenGenerator {

    private static ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");

    private static String script = "(function(a){xr=function(a,b){for(var c=0;c<b.length-2;c+=3){var d=b.charAt(c+2),d=\"a\"<=d?d.charCodeAt(0)-87:Number(d),d=\"+\"==b.charAt(c+1)?a>>>d:a<<d;a=\"+\"==b.charAt(c)?a+d&4294967295:a^d};return a};var b=TKK;var d=b.split(\".\");b=Number(d[0])||0;for(var e=[],f=0,g=0;g<a.length;g++){var l=a.charCodeAt(g);128>l?e[f++]=l:(2048>l?e[f++]=l>>6|192:(55296==(l&64512)&&g+1<a.length&&56320==(a.charCodeAt(g+1)&64512)?(l=65536+((l&1023)<<10)+(a.charCodeAt(++g)&1023),e[f++]=l>>18|240,e[f++]=l>>12&63|128):e[f++]=l>>12|224,e[f++]=l>>6&63|128),e[f++]=l&63|128)};a=b;for(f=0;f<e.length;f++) a+=e[f],a=xr(a,\"+-a^+6\");a=xr(a,\"+-3^+b+-f\");a^=Number(d[1])||0;0>a&&(a=(a&2147483647)+2147483648);a%=1E6;return a.toString()+\".\"+(a^b)})(text);";

    public String generate(String text) {
        try {
            scriptEngine.put("text", text);
            return (String) scriptEngine.eval(script);
        } catch(ScriptException ex) {
            return null;
        }
    }

    public void updateTKK() {
        try {
            String url = "https://translate.google.com";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            StringBuilder response = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String data;
                while((data = br.readLine()) != null) {
                    response.append(data);
                }
            }
            Matcher match = Pattern.compile("TKK=(.*?)\\(\\)\\)'\\);").matcher(response.toString());
            if(match.find()) {
                scriptEngine.eval(match.group());
            }
        } catch(IOException | ScriptException ex) {
            // ignore all
        }
    }

    private int xr(int  a, String b) {
        for(int c = 0 ; c < b.length() ; c += 3) {
            int d = b.charAt(c + 2);
            d = 'a' <= d ? d - 87 : d;
            d = '+' == b.charAt(c + 1) ? a >>> d : a << d;
            a = '+' == b.charAt(c) ? a + d : a ^ d;
        }
        return a;
    }

}
