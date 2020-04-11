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

package arc.resource.calculator.ui.explorer.folder;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.FolderDao;

public class FolderExplorerRepository {
    private final FolderDao mDao;
    private MutableLiveData<List<FolderExplorerItem>> mFolders = new MutableLiveData<>();

    public FolderExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mDao = db.folderDao();
    }

    public LiveData<List<FolderExplorerItem>> getFolders() {
        return mFolders;
    }

    public void fetchFolders(int stationId, int parentId) {
        mFolders = (MutableLiveData<List<FolderExplorerItem>>)
                Transformations.map(mDao.getFolders(stationId, parentId), FolderExplorerItem::fromEntities);
    }

    public void clearFolders() {
        mFolders.setValue(new ArrayList<>());
    }
}