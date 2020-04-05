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
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import arc.resource.calculator.db.entity.EngramEntity;
import arc.resource.calculator.db.entity.FolderEntity;
import arc.resource.calculator.db.entity.StationEntity;
import arc.resource.calculator.ui.explorer.back.BackFolderExplorerItem;
import arc.resource.calculator.ui.explorer.engram.EngramExplorerRepository;
import arc.resource.calculator.ui.explorer.folder.FolderExplorerRepository;
import arc.resource.calculator.ui.explorer.station.StationExplorerRepository;

public class ExplorerViewModel extends AndroidViewModel {
    // TODO: Maintain filters?
    public static final String TAG = ExplorerViewModel.class.getSimpleName();
    private final StationExplorerRepository mStationExplorerRepository;
    private final FolderExplorerRepository mFolderExplorerRepository;
    private final EngramExplorerRepository mEngramExplorerRepository;

    private MutableLiveData<ExplorerViewModelState> mViewModelState = new MutableLiveData<>();
    private MutableLiveData<BackFolderExplorerItem> mBackFolderExplorerItem = new MutableLiveData<>();

    public ExplorerViewModel(@NonNull Application application) {
        super(application);

        mStationExplorerRepository = new StationExplorerRepository(application);
        mFolderExplorerRepository = new FolderExplorerRepository(application);
        mEngramExplorerRepository = new EngramExplorerRepository(application);
    }

    MutableLiveData<ExplorerViewModelState> getViewModelState() {
        return mViewModelState;
    }

    private void setViewModelState(ExplorerViewModelState viewModelState) {
        mViewModelState.setValue(viewModelState);
    }

    LiveData<List<StationEntity>> getStations() {
        return mStationExplorerRepository.getStations();
    }

    LiveData<List<FolderEntity>> getFolders() {
        return mFolderExplorerRepository.getFolders();
    }

    LiveData<List<EngramEntity>> getEngrams() {
        return mEngramExplorerRepository.getEngrams();
    }

    /**
     * User-controlled action to select a crafting station to view its contents
     * (change station as current destination container)
     *
     * @param stationEntity Station to select (change to)
     */
    public void selectStation(StationEntity stationEntity) {
        mStationExplorerRepository.select(stationEntity);
    }

    /**
     * User-derived action to traverse backwards from a folder into a list of stations
     */
    public void deselectStation() {
        mStationExplorerRepository.deselect();
    }

    /**
     * User-derived action to select a folder location to view its contents
     * (adds selected folder for a stack of folders to track history)
     *
     * @param folderEntity Folder to select (change to)
     */
    public void selectFolder(FolderEntity folderEntity) {
        mFolderExplorerRepository.select(folderEntity);
    }

    /**
     * User-derived action to traverse backwards from current folders into a list of folders
     * (back action for folders: back button or tapping a back folder)
     */
    public void deselectFolder() {
        mFolderExplorerRepository.deselect();
    }

    public void goBack(BackFolderExplorerItem explorerItem) {
        if (explorerItem.getItemType() == ExplorerItemType.CraftingStation) {
            deselectStation();
            // TODO: 4/5/2020 tell back folder adapter they are no longer needed 
        }
    }
}
