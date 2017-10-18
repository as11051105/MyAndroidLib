package com.infrastructure.util.logger.util;

import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kale
 * @date 2016/3/23
 */
public class JsonParser {

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 4;

    @CheckResult
    public static String json(@Nullable String json) {
        if (TextUtils.isEmpty(json)) {
            return "Empty/Null json content.(This msg from logger)";
        }

        try {
            if (json.startsWith("{")) {
                return new JSONObject(json).toString(JSON_INDENT);
            } else if (json.startsWith("[")) {
                return new JSONArray(json).toString(JSON_INDENT);
            }
        } catch (JSONException e) {
            return e.getCause().getMessage() + "\n" + json;
        }
        return "Log error!.(This msg from logger)";
    }
}
