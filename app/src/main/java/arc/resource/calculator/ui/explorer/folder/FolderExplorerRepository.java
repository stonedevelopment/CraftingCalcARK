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

import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.FolderDao;
import arc.resource.calculator.db.entity.FolderEntity;

public class FolderExplorerRepository {
    private final FolderDao mDao;
    private final LiveData<List<FolderEntity>> mFolders;

    public FolderExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mDao = db.folderDao();
        mFolders = mDao.getFolders();
    }

    LiveData<List<FolderEntity>> getFolders() {
        return mFolders;
    }
}
