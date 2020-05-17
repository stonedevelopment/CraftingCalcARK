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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.primary.StationDao;

public class StationExplorerRepository {
    private final StationDao mDao;
    private MutableLiveData<List<StationExplorerItem>> mStations = new MutableLiveData<>();

    public StationExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mDao = db.stationDao();
        fetchStations();
    }

    public LiveData<List<StationExplorerItem>> getStations() {
        return mStations;
    }

    public void fetchStations() {
        mStations = (MutableLiveData<List<StationExplorerItem>>)
                Transformations.map(mDao.getStations(), StationExplorerItem::fromEntities);
    }

    public void clearStations() {
        mStations.setValue(new ArrayList<>());
    }
}