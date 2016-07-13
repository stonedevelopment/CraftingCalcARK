package com.gmail.jaredstone1982.craftingcalcark.helpers;

import android.util.Log;
import android.util.SparseIntArray;

import java.util.HashMap;

/**
 * Various methods that help debugging and the like
 */
public class Helper {
    private static boolean isDebug = false;

    public static final String DETAIL_QUANTITY = "DETAIL_QUANTITY";
    public static final String DETAIL_ID = "DETAIL_ID";

    public static final int DETAIL_ID_CODE = 1000;

    public static void Log(String tag, String message) {
        if (isDebug) Log.d(tag, message);
    }

    public static void Log(String tag, String tag2, String message) {
        if (isDebug) Log.d(tag, tag2 + "> " + message);
    }

    public static void setDebug(boolean i) {
        isDebug = i;
    }

    public static String ArrayToString(SparseIntArray array) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < array.size() - 1; i++) {
            builder.append("'").append(array.keyAt(i)).append("', ");
        }
        builder.append("'").append(array.keyAt(array.size() - 1)).append("'");

        return builder.toString();
    }

    public static String MapToString(HashMap<Long, Integer> array) {
        StringBuilder builder = new StringBuilder();

        for (Long id : array.keySet()) {
            builder.append("'").append(array.get(id)).append("', ");
        }

        return builder.toString();
    }
}
