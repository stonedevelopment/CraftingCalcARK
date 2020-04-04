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

package arc.resource.calculator.ui.explorer.station;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.StationDao;
import arc.resource.calculator.db.entity.StationEntity;

public class StationExplorerRepository {
    private StationDao mDao;
    private LiveData<List<StationEntity>> mStations;

    public StationExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mDao = db.stationDao();
        mStations = mDao.getStations();
    }

    public LiveData<List<StationEntity>> getStations() {
        return mStations;
    }
}
