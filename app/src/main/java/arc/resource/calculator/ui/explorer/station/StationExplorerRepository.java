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
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.StationDao;
import arc.resource.calculator.db.entity.StationEntity;

public class StationExplorerRepository {
    private final StationDao mDao;
    private MutableLiveData<List<StationEntity>> mStations = new MediatorLiveData<>();

    public StationExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mDao = db.stationDao();
    }

    public LiveData<List<StationEntity>> getStations() {
        return mStations;
    }

    /**
     * User-derived action to "open" a crafting station and view its contents
     */
    public void selectStation() {
        clearStations();
    }

    /**
     * User-derived "back" action to "close" current station and view all stations
     */
    public void deselectStation() {
        fetchStations();
    }

    private void fetchStations() {
        mStations = mDao.getStations();
    }

    private void clearStations() {
        mStations.setValue(new ArrayList<>());
    }
}