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

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.entity.FolderEntity;
import arc.resource.calculator.db.entity.StationEntity;

public class FolderExplorerViewModel extends AndroidViewModel {
    private final LiveData<List<FolderEntity>> mFolders;
    private final FolderExplorerRepository mRepository;

    public FolderExplorerViewModel(@NonNull Application application) {
        super(application);

        mRepository = new FolderExplorerRepository(application);
        mFolders = mRepository.getFolders();
    }

    public LiveData<List<FolderEntity>> getFolders() {
        return mFolders;
    }

    public void update(StationEntity stationEntity) {
        mRepository.update(stationEntity);
    }

    public void update(FolderEntity folderEntity) {
        mRepository.update(folderEntity);
    }
}
