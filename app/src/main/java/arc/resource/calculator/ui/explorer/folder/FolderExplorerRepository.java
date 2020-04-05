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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.FolderDao;
import arc.resource.calculator.db.entity.FolderEntity;

public class FolderExplorerRepository {
    private final FolderDao mDao;
    private final MutableLiveData<List<FolderEntity>> mFolders = new MutableLiveData<>();
    private Stack<FolderEntity> mFolderStack = new Stack<>();

    public FolderExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mDao = db.folderDao();
    }

    public LiveData<List<FolderEntity>> getFolders() {
        return mFolders;
    }

    private void setFolders(LiveData<List<FolderEntity>> folders) {
        setFolders(folders.getValue());
    }

    private void setFolders(List<FolderEntity> folderEntities) {
        mFolders.setValue(folderEntities);
    }

    private FolderEntity getCurrentFolder() {
        return mFolderStack.peek();
    }

    public void select(FolderEntity folderEntity) {
        addToFolderStack(folderEntity);
        settle();
    }

    public void deselect() {
        removeFromFolderStack();
        settle();
    }

    private boolean isFolderStackEmpty() {
        return mFolderStack.isEmpty();
    }

    private void addToFolderStack(FolderEntity folderEntity) {
        mFolderStack.push(folderEntity);
    }

    private void removeFromFolderStack() {
        mFolderStack.pop();
    }

    private void fetchFolders() {
        FolderEntity folderEntity = getCurrentFolder();
        setFolders(mDao.getFolders(folderEntity.getStationId(), folderEntity.getParentId()));
    }

    private void clearFolders() {
        setFolders(new ArrayList<>());
    }

    /**
     * Final step in executing changes to {@see ExplorerViewModel}
     * <p>
     * If the folder stack is empty, clear list of folders
     * If the folder stack is not empty, fetch list of folders for last added value
     */
    private void settle() {
        if (isFolderStackEmpty()) {
            clearFolders();
        } else {
            fetchFolders();
        }
    }
}