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

public class CheckVersionTask extends AsyncTask<Void, Integer, List<Versioning>> {
    public static final String TAG = CheckVersionTask.class.getCanonicalName();

    private CheckVersionListener listener;
    private PrefsUtil prefsUtil;
    private JsonNode versioningNode;

    public CheckVersionTask(
            Context context, PrefsUtil prefsUtil, CheckVersionListener listener) {
        setListener(listener);
        setPrefsUtil(prefsUtil);

        setupVersioning(context);
    }

    private void setPrefsUtil(PrefsUtil prefsUtil) {
        this.prefsUtil = prefsUtil;
    }

    private void setListener(CheckVersionListener listener) {
        this.listener = listener;
    }

    private void setupVersioning(Context context) {
        try {
            versioningNode = JSONUtil.convertVersioningJsonFileToNode(context);
        } catch (IOException e) {
            listener.onError(e);
        }
    }

    /**
     * Let User know that we've started checking versions.
     */
    @Override
    protected void onPreExecute() {
        listener.onStart();
    }

    @Override
    protected List<Versioning> doInBackground(Void... voids) {
        List<Versioning> versioningList = new ArrayList<>();

        //  get node for Primary game data
        JsonNode primaryNode = versioningNode.get(cPrimary);

        //  convert into Versioning object
        PrimaryVersioning primaryVersioning = PrimaryVersioning.fromJSON(primaryNode);

        //  test version against previous, add if test passes
        if (isNewVersion(primaryVersioning)) {
            versioningList.add(primaryVersioning);
        }

        //  get DLC array
        JsonNode dlcArray = versioningNode.get(cDLC);
        for (int i = 0; i < dlcArray.size(); i++) {
            //  get node for DLC game data
            JsonNode dlcNode = dlcArray.get(i);

            //  convert into Versioning object
            DLCVersioning dlcVersioning = DLCVersioning.fromJSON(dlcNode);

            //  test version against previous, add if test passes
            if (isNewVersion(dlcVersioning)) {
                versioningList.add(dlcVersioning);
            }
        }

        return versioningList;
    }

    @Override
    protected void onPostExecute(List<Versioning> versioningList) {
        listener.onFinish(versioningList);
    }

    /**
     * Used to test if incoming Versioning object matches saved version
     *
     * @param versioning Versioning object to test for
     * @return if new version found
     */
    private boolean isNewVersion(Versioning versioning) {
        String oldVersion = prefsUtil.getVersionByUUID(versioning.getUuid());
        String newVersion = versioning.getVersion();

        return hasUpdate(oldVersion, newVersion);
    }

    /**
     * Used to test if versions match
     *
     * @param oldVersion previously-saved version
     * @param newVersion version taken from json file
     * @return if versions match
     */
    private boolean hasUpdate(String oldVersion, String newVersion) {
        return !Objects.equals(oldVersion, newVersion);
    }
}