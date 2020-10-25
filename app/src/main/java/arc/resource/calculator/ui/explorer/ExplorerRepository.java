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

package arc.resource.calculator.ui.explorer;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.primary.DirectoryDao;
import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;

public class ExplorerRepository {
    public static final String TAG = ExplorerRepository.class.getSimpleName();

    private final DirectoryDao mDirectoryDao;

    ExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mDirectoryDao = db.directoryDao();
    }

    LiveData<List<DirectoryItemEntity>> fetchDirectory(@Nullable String parentId) {
        Log.d(TAG, "fetchDirectory: " + parentId);
        if (parentId == null) parentId = "a2942aac-b904-468a-a887-78637c86aa1b";

        return mDirectoryDao.getDirectoryItemList(parentId);
    }
}