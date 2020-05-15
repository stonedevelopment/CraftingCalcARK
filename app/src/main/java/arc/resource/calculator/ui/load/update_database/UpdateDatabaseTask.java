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

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.ui.load.check_version.versioning.Versioning;
import arc.resource.calculator.util.PrefsUtil;

public class UpdateDatabaseTask extends AsyncTask {
    private AppDatabase database;
    private PrefsUtil prefsUtil;
    private Versioning versioning;

    public UpdateDatabaseTask(Context context, PrefsUtil prefsUtil, Versioning versioning) {
        setDatabase(context);
        setPrefsUtil(prefsUtil);
        setVersioning(versioning);
    }

    private void setPrefsUtil(PrefsUtil prefsUtil) {
        this.prefsUtil = prefsUtil;
    }

    private void setDatabase(Context context) {
        this.database = AppDatabase.getInstance(context);
    }

    private void setVersioning(Versioning versioning) {
        this.versioning = versioning;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}