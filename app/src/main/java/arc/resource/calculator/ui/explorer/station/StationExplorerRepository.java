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

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.StationDao;
import arc.resource.calculator.db.entity.StationEntity;

public class StationExplorerRepository {
    private final MutableLiveData<List<StationEntity>> mStations = new MutableLiveData<>();
    private final StationDao mDao;
    private StationEntity mCurrentStation;

    public StationExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mDao = db.stationDao();
    }

    public LiveData<List<StationEntity>> getStations() {
        return mStations;
    }

    private void setStations(LiveData<List<StationEntity>> stations) {
        setStations(stations.getValue());
    }

    private void setStations(List<StationEntity> stationEntities) {
        mStations.setValue(stationEntities);
    }

    private void setCurrentStation(StationEntity stationEntity) {
        mCurrentStation = stationEntity;
    }

    private void unsetCurrentStation() {
        setCurrentStation(null);
    }

    /**
     * ViewModel-derived action used to initialize repository data. This method will allow
     * the repository to set any retained data ahead of settling changes.
     * <p>
     * Instantiate variables
     * Settle changes
     */
    void init() {
        /* instantiate variables here */
        settle();
    }

    /**
     * User-derived action to select a crafting station and view its contents
     * <p>
     * Update current station
     * Settle changes
     *
     * @param stationEntity Station to change to
     */
    public void select(StationEntity stationEntity) {
        setCurrentStation(stationEntity);
        settle();
    }

    /**
     * User-derived action to deselect a crafting station and view all crafting stations
     * <p>
     * Clear current station
     * Settle changes
     */
    public void deselect() {
        unsetCurrentStation();
        settle();
    }

    /**
     * Helper method to satisfy inquiry on if the current station has value
     *
     * @return true/false if current station has value
     */
    private boolean currentStationHasValue() {
        return mCurrentStation != null;
    }

    /**
     * Fetches new data from DAO
     */
    private void fetchStations() {
        setStations(mDao.getStations());
    }

    /**
     * Clears list of crafting stations
     */
    private void clearStations() {
        setStations(new ArrayList<>());
    }

    /**
     * Final step in executing changes to ViewModel
     * <p>
     * If the current station has value, clear list of stations
     * If the current station is null, fetch full list of stations
     */
    private void settle() {
        if (currentStationHasValue()) {
            clearStations();
        } else {
            fetchStations();
        }
    }
}