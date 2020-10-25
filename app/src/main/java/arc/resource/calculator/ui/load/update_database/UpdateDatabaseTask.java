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

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.entity.primary.CompositeEntity;
import arc.resource.calculator.db.entity.primary.CompositionEntity;
import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;
import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.db.entity.primary.FolderEntity;
import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.db.entity.primary.ResourceEntity;
import arc.resource.calculator.db.entity.primary.StationEntity;
import arc.resource.calculator.ui.load.check_version.versioning.PrimaryVersioning;
import arc.resource.calculator.ui.load.check_version.versioning.Versioning;
import arc.resource.calculator.util.JSONUtil;

import static arc.resource.calculator.util.Constants.cComposites;
import static arc.resource.calculator.util.Constants.cComposition;
import static arc.resource.calculator.util.Constants.cDetails;
import static arc.resource.calculator.util.Constants.cDirectory;
import static arc.resource.calculator.util.Constants.cEngrams;
import static arc.resource.calculator.util.Constants.cFolders;
import static arc.resource.calculator.util.Constants.cResources;
import static arc.resource.calculator.util.Constants.cStations;

public class UpdateDatabaseTask extends AsyncTask<Void, Integer, Void> {
    public static final String TAG = UpdateDatabaseTask.class.getCanonicalName();

    private AppDatabase database;
    private List<Versioning> versioningList;
    private UpdateDatabaseListener listener;
    private WeakReference<Context> context;

    public UpdateDatabaseTask(Context context, List<Versioning> versioningList, UpdateDatabaseListener listener) {
        setContext(context);
        setListener(listener);
        setVersioningList(versioningList);

        setupDatabase();
    }

    private Context getContext() {
        return context.get();
    }

    private void setContext(Context context) {
        this.context = new WeakReference<>(context);
    }

    private void setupDatabase() {
        this.database = AppDatabase.getInstance(getContext());
    }

    private void setListener(UpdateDatabaseListener listener) {
        this.listener = listener;
    }

    private void setVersioningList(List<Versioning> versioningList) {
        this.versioningList = versioningList;
    }

    @Override
    protected void onPreExecute() {
        listener.onStart();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int index = values[0];
        Versioning versioning = versioningList.get(index);
        int visualPosition = index + 1;
        int size = versioningList.size();

        listener.onProgressUpdate(versioning, visualPosition, size);
    }

    @Override
    protected Void doInBackground(Void... aVoid) {
        try {
            for (int i = 0; i < versioningList.size(); i++) {
                //  tell listener we're starting a new versioning task
                publishProgress(i);

                //  get versioning object from list
                Versioning versioning = versioningList.get(i);

                if (isPrimary(versioning)) {
                    updatePrimary(versioning);
                } else {
                    //  updateDlc(versioning);
                }
            }
        } catch (IOException e) {
            listener.onError(e);
        }
        return null;
    }

    private boolean isPrimary(Versioning versioning) {
        return versioning instanceof PrimaryVersioning;
    }

    private void updatePrimary(Versioning versioning) throws IOException {
        JsonNode inNode = JSONUtil.parseUpdatifiedFile(getContext(), versioning);

        //  clear database for fresh data
        database.clearAllTables();

        //  insert game object
        database.gameDao().insert(GameEntity.fromJson(inNode.get(cDetails)));

        JsonNode resources = inNode.get(cResources);
        for (JsonNode node : resources) {
            database.resourceDao().insert(ResourceEntity.fromJson(node));
        }

        JsonNode stations = inNode.get(cStations);
        for (JsonNode node : stations) {
            database.stationDao().insert(StationEntity.fromJson(node));
        }

        JsonNode folders = inNode.get(cFolders);
        for (JsonNode node : folders) {
            database.folderDao().insert(FolderEntity.fromJson(node));
        }

        JsonNode engrams = inNode.get(cEngrams);
        for (JsonNode node : engrams) {
            database.engramDao().insert(EngramEntity.fromJson(node));
        }

        JsonNode compositions = inNode.get(cComposition);
        for (JsonNode node : compositions) {
            database.compositionDao().insert(CompositionEntity.fromJson(node));
        }

        JsonNode composites = inNode.get(cComposites);
        for (JsonNode node : composites) {
            database.compositeDao().insert(CompositeEntity.fromJson(node));
        }

        JsonNode directory = inNode.get(cDirectory);
        for (JsonNode node : directory) {
            database.directoryDao().insert(DirectoryItemEntity.fromJson(node));
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.onFinish();
    }
}