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

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.entity.EngramEntity;
import arc.resource.calculator.db.entity.FolderEntity;
import arc.resource.calculator.db.entity.StationEntity;
import arc.resource.calculator.db.repository.EngramRepository;
import arc.resource.calculator.db.repository.FolderRepository;
import arc.resource.calculator.db.repository.StationRepository;

class ExplorerRepository {
    private StationRepository stationRepository;
    private FolderRepository folderRepository;
    private EngramRepository engramRepository;
    private MutableLiveData<List<ExplorerItem>> mExplorerItems = new MutableLiveData<>();

    ExplorerRepository(Application application) {
        stationRepository = new StationRepository(application);
        folderRepository = new FolderRepository(application);
        engramRepository = new EngramRepository(application);

        buildExplorerItems();
    }

    MutableLiveData<List<ExplorerItem>> getExplorerItems() {
        return mExplorerItems;
    }

    private void buildExplorerItems() {
        List<ExplorerItem> explorerItems = new ArrayList<>();
        explorerItems.addAll(convertStations());
        explorerItems.addAll(convertFolders());
        explorerItems.addAll(convertEngrams());
        mExplorerItems.setValue(explorerItems);
    }

    private List<ExplorerItem> convertStations() {
        List<ExplorerItem> explorerItems = new ArrayList<>();
        List<StationEntity> stationEntities = stationRepository.getEntities().getValue();
        if (stationEntities != null) {
            for (StationEntity stationEntity : stationEntities) {
                explorerItems.add(ExplorerItem.fromStation(stationEntity));
            }
        }
        return explorerItems;
    }

    private List<ExplorerItem> convertFolders() {
        List<ExplorerItem> explorerItems = new ArrayList<>();
        List<FolderEntity> folderEntities = folderRepository.getEntities().getValue();
        if (folderEntities != null) {
            for (FolderEntity folderEntity : folderEntities) {
                explorerItems.add(ExplorerItem.fromFolder(folderEntity));
            }
        }
        return explorerItems;
    }

    private List<ExplorerItem> convertEngrams() {
        List<ExplorerItem> explorerItems = new ArrayList<>();
        List<EngramEntity> engramEntities = engramRepository.getEntities().getValue();
        if (engramEntities != null) {
            for (EngramEntity engramEntity : engramEntities) {
                explorerItems.add(ExplorerItem.fromEngram(engramEntity));
            }
        }
        return explorerItems;
    }
}
