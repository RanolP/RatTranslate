package io.github.ranolp.rattranslate.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.nio.charset.Charset;

public class GsonUtil {
    private static final JsonParser PARSER = new JsonParser();

    private GsonUtil() {
        throw new UnsupportedOperationException("You cannot instantiate GsonUtil");
    }

    public static JsonElement parse(CharSequence json) throws JsonSyntaxException {
        return PARSER.parse(json.toString());
    }

    public static JsonElement parse(Reader reader) throws JsonSyntaxException {
        return PARSER.parse(reader);
    }

    public static JsonElement parse(InputStream stream) throws JsonSyntaxException, IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            return PARSER.parse(reader);
        }
    }

    public static JsonElement parse(InputStream stream, Charset charset) throws JsonSyntaxException, IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset))) {
            return PARSER.parse(reader);
        }
    }
}
