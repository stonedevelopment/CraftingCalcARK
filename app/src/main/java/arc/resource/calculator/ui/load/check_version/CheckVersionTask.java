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

package arc.resource.calculator.ui.load.check_version;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import arc.resource.calculator.ui.load.check_version.versioning.DLCVersioning;
import arc.resource.calculator.ui.load.check_version.versioning.PrimaryVersioning;
import arc.resource.calculator.util.JSONUtil;
import arc.resource.calculator.util.PrefsUtil;

import static arc.resource.calculator.util.JSONUtil.cDLC;
import static arc.resource.calculator.util.JSONUtil.cPrimary;

public class CheckVersionTask extends AsyncTask<Void, Void, Void> {
    private CheckVersionListener listener;
    private PrefsUtil prefsUtil;
    private JSONObject versioningObject;

    public CheckVersionTask(Context context, PrefsUtil prefsUtil, CheckVersionListener listener) {
        setListener(listener);
        setPrefsUtil(prefsUtil);

        initialize(context);
    }

    private void setPrefsUtil(PrefsUtil prefsUtil) {
        this.prefsUtil = prefsUtil;
    }

    private void setListener(CheckVersionListener listener) {
        this.listener = listener;
    }

    private void initialize(Context context) {
        listener.onInit();
        try {
            String jsonString = JSONUtil.readVersioningJsonToString(context);
            // build a json object based on the read json string
            versioningObject = new JSONObject(jsonString);
        } catch (IOException | JSONException e) {
            listener.onError(e);
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            JSONObject primaryObject = versioningObject.getJSONObject(cPrimary);
            JSONArray dlcArray = versioningObject.getJSONArray(cDLC);

            //  get total version counnt for progress bar
            //      primary + length of dlc array
            int totalVersions = 1 + dlcArray.length();

            //  let listener know we're starting and how many versions we're checking
            listener.onStart(totalVersions);

            //  get Primary object
            checkPrimaryVersion(primaryObject);

            //  // TODO: 5/10/2020  get DLC array
            //  iterate getting and testing versions
            for (int i = 0; i < dlcArray.length(); i++) {
                JSONObject dlcObject = (JSONObject) dlcArray.get(i);
                checkDLCVersion(dlcObject);
            }

        } catch (JSONException e) {
            listener.onError(e);
        }
        return null;
    }

    private void checkPrimaryVersion(JSONObject primaryObject) throws JSONException {
        PrimaryVersioning versioning = PrimaryVersioning.fromJSON(primaryObject);
        listener.onCheckPrimaryVersion();

        String oldVersion = prefsUtil.getVersionForPrimary();
        String newVersion = versioning.getVersion();
        if (hasUpdate(oldVersion, newVersion)) {
            listener.onNewPrimaryVersion(oldVersion, newVersion, versioning);
        }
    }

    private void checkDLCVersion(JSONObject dlcObject) throws JSONException {
        DLCVersioning versioning = DLCVersioning.fromJSON(dlcObject);
        listener.onCheckDLCVersion(versioning);

        String oldVersion = prefsUtil.getVersionForDLC(versioning.getName());
        String newVersion = versioning.getVersion();
        if (hasUpdate(oldVersion, newVersion)) {
            listener.onNewDLCVersion(oldVersion, newVersion, versioning);
        }
    }

    private boolean hasUpdate(String oldVersion, String newVersion) {
        return !Objects.equals(oldVersion, newVersion);
    }
}