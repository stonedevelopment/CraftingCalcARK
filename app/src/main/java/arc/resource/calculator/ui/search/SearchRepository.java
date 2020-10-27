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

package arc.resource.calculator.ui.search;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.primary.EngramDao;
import arc.resource.calculator.db.entity.primary.EngramEntity;

public class SearchRepository {
    public static final String TAG = SearchRepository.class.getSimpleName();

    private final EngramDao engramDao;

    SearchRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        engramDao = db.engramDao();
    }

    LiveData<List<EngramEntity>> searchEngrams(@Nullable String searchCriteria) {
        Log.d(TAG, "searchEngrams: " + searchCriteria);
        return engramDao.searchEngrams("%" + searchCriteria + "%");
    }
}