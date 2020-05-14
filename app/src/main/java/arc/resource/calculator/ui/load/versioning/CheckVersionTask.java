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

package arc.resource.calculator.ui.load.versioning;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import arc.resource.calculator.model.json.PrimaryVersioning;
import arc.resource.calculator.util.JSONUtil;

import static arc.resource.calculator.util.JSONUtil.cPrimary;

public class CheckVersionTask extends AsyncTask<Void, Void, Void> {
    private CheckVersionListener listener;
    private JSONObject versioningObject;

    public CheckVersionTask(Context context, CheckVersionListener listener) {
        setListener(listener);

        initialize(context);
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

        //  get Primary object
        //  test version
        //  TODO: 5/9/2020  how to update db? together with dlc or separate?
        JSONObject primaryObject = versioningObject.getJSONObject(cPrimary);
        primaryVersioning = (PrimaryVersioning) PrimaryVersioning.fromJSON(primaryObject);
        String oldVersion = prefsUtil.getVersionForPrimary();
        String newVersion = primaryVersioning.getVersion();

        //  // TODO: 5/10/2020  get DLC array
        //  iterate getting and testing versions

        // now, let's check if we even need to update.
        mHasUpdate = JSONUtil.isNewVersion(oldVersion, newVersion);

        return null;
    }
}