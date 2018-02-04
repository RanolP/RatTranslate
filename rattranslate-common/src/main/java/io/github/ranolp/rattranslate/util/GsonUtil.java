package io.github.ranolp.rattranslate.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class GsonUtil {
    private GsonUtil() {
        throw new UnsupportedOperationException("You cannot instantiate GsonUtil");
    }

    private static final JsonParser PARSER = new JsonParser();

    public static JsonElement parse(CharSequence json) throws JsonSyntaxException {
        return PARSER.parse(json.toString());
    }
}
