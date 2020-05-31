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

import com.fasterxml.jackson.databind.JsonNode;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import arc.resource.calculator.ui.load.check_version.versioning.DLCVersioning;
import arc.resource.calculator.ui.load.check_version.versioning.PrimaryVersioning;
import arc.resource.calculator.ui.load.check_version.versioning.Versioning;
import arc.resource.calculator.util.JSONUtil;
import arc.resource.calculator.util.PrefsUtil;

import static arc.resource.calculator.util.JSONUtil.cDLC;
import static arc.resource.calculator.util.JSONUtil.cPrimary;

public class CheckVersionTask extends AsyncTask<Void, Void, Void> {
    private CheckVersionListener listener;
    private PrefsUtil prefsUtil;
    private JsonNode versioningObject;
    private List<Versioning> versioningList;

    private boolean hasException;
    private Exception exception;

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
        try {
            versioningObject = JSONUtil.readVersioningJsonToString(context);
            // build a json object based on the read json string
            versioningList = new ArrayList<>();
        } catch (IOException e) {
            exception = e;
            hasException = true;
        }
    }

    @Override
    protected void onPreExecute() {
        //  let listener know we're checking versions
        listener.onStart();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (hasException) return null;

        //  get Primary object
        JsonNode primaryNode = versioningObject.get(cPrimary);
        PrimaryVersioning primaryVersioning = PrimaryVersioning.fromJSON(primaryNode);
        if (checkVersion(primaryVersioning)) {
            versioningList.add(primaryVersioning);
        }

        //  get DLC array
        //  iterate getting and testing versions
        JsonNode dlcArray = versioningObject.get(cDLC);
        for (int i = 0; i < dlcArray.size(); i++) {
            JsonNode dlcObject = dlcArray.get(i);
            DLCVersioning dlcVersioning = DLCVersioning.fromJSON(dlcObject);
            if (checkVersion(dlcVersioning)) {
                versioningList.add(dlcVersioning);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (hasException) {
            listener.onError(exception);
            return;
        }

        //  let listener know we're finished
        //  send listener list of new versionings
        listener.onFinish(versioningList);
    }

    private boolean checkVersion(Versioning versioning) {
        String oldVersion = prefsUtil.getVersionByUUID(versioning.getUuid());
        String newVersion = versioning.getVersion();

        return hasUpdate(oldVersion, newVersion);
    }

    private boolean hasUpdate(String oldVersion, String newVersion) {
        return !Objects.equals(oldVersion, newVersion);
    }
}