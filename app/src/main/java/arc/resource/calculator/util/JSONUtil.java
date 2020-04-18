/*
 * Copyright (c) 2019 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */

package arc.resource.calculator.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class JSONUtil {
    public static final String cPrimaryFilePath = "Primary/";
    public static final String cCommonJsonFileName = "data.json";
    public static final String cVersioning = "versioning";
    public static final String cVersion = "version";
    public static final String cChangeLog = "changelog";
    private static final String TAG = JSONUtil.class.getSimpleName();

    public static String readRawJsonFileToJsonString(Context context, int json_resource_file) throws IOException {
        String jsonString;

        InputStream fileStream = context.getResources().openRawResource(json_resource_file);
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileStream))) {
            StringBuilder buffer = new StringBuilder();

            String line;
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            jsonString = buffer.toString();
        }

        return jsonString;
    }

    private static String readRawJsonFileToJsonString(Context context, String fileName) throws IOException {
        String jsonString;

        InputStream fileStream = context.getAssets().open(fileName);
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileStream))) {
            StringBuilder buffer = new StringBuilder();

            String line;
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            jsonString = buffer.toString();
        }

        return jsonString;
    }

    public static String readPrimaryJsonFileToJsonString(Context context) throws IOException {
        String fileName = cPrimaryFilePath + cCommonJsonFileName;
        return readRawJsonFileToJsonString(context, fileName);
    }

    public static boolean isNewVersion(String oldVersion, String newVersion) {
        return !Objects.equals(oldVersion, newVersion);
    }
}
