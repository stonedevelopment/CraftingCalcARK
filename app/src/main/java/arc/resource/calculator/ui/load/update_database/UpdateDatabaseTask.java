/*
 * Copyright (c) 2020 Jared Stone
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

package arc.resource.calculator.ui.load.update_database;

import android.content.Context;
import android.os.AsyncTask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.entity.primary.GameEntity;
import arc.resource.calculator.db.entity.primary.ResourceEntity;
import arc.resource.calculator.ui.load.check_version.versioning.PrimaryVersioning;
import arc.resource.calculator.ui.load.check_version.versioning.Versioning;
import arc.resource.calculator.ui.load.update_database.game_data.GameObject;
import arc.resource.calculator.util.JSONUtil;
import arc.resource.calculator.util.PrefsUtil;

public class UpdateDatabaseTask extends AsyncTask<Void, Void, Void> {
    private AppDatabase database;
    private PrefsUtil prefsUtil;
    private List<Versioning> versioningList;
    private UpdateDatabaseListener listener;
    private WeakReference<Context> context;

    public UpdateDatabaseTask(Context context, PrefsUtil prefsUtil, List<Versioning> versioningList, UpdateDatabaseListener listener) {
        setContext(context);
        setDatabase();
        setPrefsUtil(prefsUtil);
        setVersioningList(versioningList);
        setListener(listener);
    }

    private Context getContext() {
        return context.get();
    }

    private void setContext(Context context) {
        this.context = new WeakReference<>(context);
    }

    private void setDatabase() {
        this.database = AppDatabase.getInstance(getContext());
    }

    private void setPrefsUtil(PrefsUtil prefsUtil) {
        this.prefsUtil = prefsUtil;
    }

    private void setListener(UpdateDatabaseListener listener) {
        this.listener = listener;
    }

    private void setVersioningList(List<Versioning> versioningList) {
        this.versioningList = versioningList;
    }

    private boolean isPrimary(Versioning versioning) {
        return versioning instanceof PrimaryVersioning;
    }

    private JsonNode getJsonNodeFromFilePath(String filePath) throws IOException {
        return JSONUtil.parseFileToNode(getContext(), filePath);
    }

    @Override
    protected Void doInBackground(Void... aVoid) {
        for (int i = 0; i < versioningList.size(); i++) {
            //  get versioning object from list
            Versioning versioning = versioningList.get(i);

            //  tell listener we're starting a new versioning task
            listener.onUpdate(versioning, i, versioningList.size());

            if (isPrimary(versioning)) {
                updatePrimary(versioning);
            } else {
            }
        }
        return null;
    }

    private void updatePrimary(Versioning versioning) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode node = getJsonNodeFromFilePath(versioning.getFilePath());

            GameEntity details = GameEntity.fromJSON(node.get("details"));

            JsonNode resources = node.get("resources");
            JsonNode stations = node.get("stations");
            JsonNode folders = node.get("folders");
            JsonNode engrams = node.get("engrams");
            JsonNode composition = node.get("composition");
            JsonNode directory = node.get("directory");

            //  clear database for fresh data
            database.clearAllTables();

            for (JsonNode resourceNode : resources) {
                ResourceEntity entity = ResourceEntity.fromJSON(resourceNode);
                database.resourceDao().insert(entity);
            }

        } catch (IOException e) {
            listener.onError(e);
        }
    }
}