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

package arc.resource.calculator.ui.load.db;

import android.content.Context;
import android.os.AsyncTask;

import arc.resource.calculator.db.AppDatabase;

public class UpdateDatabaseTask extends AsyncTask {
    private AppDatabase database;
    private String json;

    public UpdateDatabaseTask(Context context, String jsonString) {
        database = AppDatabase.getInstance(context);
        json = jsonString;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}