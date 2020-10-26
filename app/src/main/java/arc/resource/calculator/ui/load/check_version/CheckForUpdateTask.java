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
import java.util.Date;
import java.util.List;

import arc.resource.calculator.ui.load.check_version.versioning.DlcVersioning;
import arc.resource.calculator.ui.load.check_version.versioning.PrimaryVersioning;
import arc.resource.calculator.ui.load.check_version.versioning.Versioning;
import arc.resource.calculator.util.JsonUtil;
import arc.resource.calculator.util.PrefsUtil;

import static arc.resource.calculator.util.JsonUtil.cDLC;
import static arc.resource.calculator.util.JsonUtil.cPrimary;

public class CheckForUpdateTask extends AsyncTask<Void, Integer, List<Versioning>> {
    public static final String TAG = CheckForUpdateTask.class.getCanonicalName();

    private CheckForUpdateListener listener;
    private PrefsUtil prefsUtil;
    private JsonNode updatificationNode;

    public CheckForUpdateTask(
            Context context, PrefsUtil prefsUtil, CheckForUpdateListener listener) {
        setListener(listener);
        setPrefsUtil(prefsUtil);

        setupUpdatificationNode(context);
    }

    private void setPrefsUtil(PrefsUtil prefsUtil) {
        this.prefsUtil = prefsUtil;
    }

    private void setListener(CheckForUpdateListener listener) {
        this.listener = listener;
    }

    private void setupUpdatificationNode(Context context) {
        try {
            updatificationNode = JsonUtil.parseUpdatificationFile(context);
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
        JsonNode primaryNode = updatificationNode.get(cPrimary);

        //  convert into Versioning object
        PrimaryVersioning primaryVersioning = PrimaryVersioning.fromJson(primaryNode);

        //  test version against previous, add if test passes
        if (isNewVersion(primaryVersioning)) {
            versioningList.add(primaryVersioning);
        }

        //  get DLC array
        JsonNode dlcArray = updatificationNode.get(cDLC);
        for (JsonNode dlcNode : dlcArray) {
            //  convert into Versioning object
            DlcVersioning dlcVersioning = DlcVersioning.fromJson(dlcNode);

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
     * Used to test if incoming Versioning object's last update date matches saved date
     *
     * @param versioning Versioning object to test for
     * @return if new date was found
     */
    private boolean isNewVersion(Versioning versioning) {
        Date oldDate = new Date(prefsUtil.getLastUpdateFromUuid(versioning.getUuid()));
        Date newDate = versioning.getLastUpdated();

        return hasUpdate(oldDate, newDate);
    }

    /**
     * Used to test if versions match
     *
     * @param oldDate previously-saved Date in ms
     * @param newDate Date in ms taken from json file
     * @return if times match
     */
    private boolean hasUpdate(Date oldDate, Date newDate) {
        return !oldDate.equals(newDate);
    }
}