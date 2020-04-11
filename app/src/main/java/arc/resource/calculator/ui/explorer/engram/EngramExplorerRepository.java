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

package arc.resource.calculator.ui.explorer.engram;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.EngramDao;

public class EngramExplorerRepository {
    private final EngramDao mDao;
    private MutableLiveData<List<EngramExplorerItem>> mEngrams = new MutableLiveData<>();

    public EngramExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mDao = db.engramDao();
    }

    public LiveData<List<EngramExplorerItem>> getEngrams() {
        return mEngrams;
    }

    public void fetchEngrams(int stationId, int parentId) {
        mEngrams = (MutableLiveData<List<EngramExplorerItem>>)
                Transformations.map(mDao.getEngrams(stationId, parentId), EngramExplorerItem::fromEntities);
    }

    public void clearEngrams() {
        mEngrams.setValue(new ArrayList<>());
    }
}
