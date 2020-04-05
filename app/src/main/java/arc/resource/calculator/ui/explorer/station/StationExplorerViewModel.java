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

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.entity.StationEntity;

public class StationExplorerViewModel extends AndroidViewModel {
    private final LiveData<List<StationEntity>> mStations;
    private final StationExplorerRepository mRepository;

    public StationExplorerViewModel(Application application) {
        super(application);

        mRepository = new StationExplorerRepository(application);
        mRepository.init();

        mStations = mRepository.getStations();
    }

    public void select(StationEntity stationEntity) {
        mRepository.select(stationEntity);
    }

    public void deselect() {
        mRepository.deselect();
    }

    public LiveData<List<StationEntity>> getStations() {
        return mStations;
    }
}