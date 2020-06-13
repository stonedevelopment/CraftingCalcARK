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

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.annotation.Nullable;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.primary.DirectoryDao;
import arc.resource.calculator.db.entity.primary.DirectoryEntity;

public class ExplorerRepository {
    private DirectoryDao dao;

    ExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        setDao(db.directoryDao());
    }

    public DirectoryDao getDao() {
        return dao;
    }

    private void setDao(DirectoryDao dao) {
        this.dao = dao;
    }

    LiveData<List<DirectoryEntity>> fetchDirectory(@NonNull String parentId) {
        return dao.getDirectory(parentId);
    }
}