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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import arc.resource.calculator.ui.load.check_version.versioning.Versioning;

import static arc.resource.calculator.util.Constants.cUpdatificationFileName;

public class JsonUtil {
    public static final String cVersioning = "versioning";
    public static final String cUuid = "uuid";
    public static final String cName = "name";
    public static final String cDescription = "description";
    public static final String cQuantity = "quantity";
    public static final String cIsEngram = "isEngram";
    public static final String cLastUpdated = "lastUpdated";
    public static final String cCompositionId = "compositionId";
    public static final String cEngramId = "engramId";
    public static final String cGameId = "gameId";
    public static final String cParentId = "parentId";
    public static final String cSourceId = "sourceId";
    public static final String cBackFolderFile = "backFolderFile";
    public static final String cImageFile = "imageFile";
    public static final String cFolderFile = "folderFile";
    public static final String cLogoFile = "logoFile";
    public static final String cFilePath = "filePath";
    public static final String cViewType = "viewType";
    public static final String cChangeLog = "changelog";
    public static final String cPrimary = "Primary";
    public static final String cDLC = "DLC";
    public static final String cVersion = "version";
    public static final String TAG = JsonUtil.class.getSimpleName();
    public static final String cJsonFilePath = "json/";

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

    public static String readRawJsonFileToJsonString(Context context, String fileName) throws IOException {
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

    private static JsonNode parseFileToNode(Context context, String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream fileStream = context.getAssets().open(fileName);
        return mapper.readTree(fileStream);
    }

    public static JsonNode parseUpdatificationFile(Context context) throws IOException {
        return parseFileToNode(context, cUpdatificationFileName);
    }

    public static JsonNode parseUpdatifiedFile(Context context, Versioning versioning) throws IOException {
        return parseFileToNode(context, versioning.getFilePath());
    }

    public static boolean isNewVersion(String oldVersion, String newVersion) {
        return !Objects.equals(oldVersion, newVersion);
    }
}
