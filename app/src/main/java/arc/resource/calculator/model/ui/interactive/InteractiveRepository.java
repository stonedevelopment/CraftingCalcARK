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

package arc.resource.calculator.model.ui.interactive;

import android.app.Application;

import androidx.lifecycle.LiveData;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.DlcDao;
import arc.resource.calculator.db.dao.dlc.DlcEngramDao;
import arc.resource.calculator.db.dao.dlc.DlcFolderDao;
import arc.resource.calculator.db.dao.dlc.DlcResourceDao;
import arc.resource.calculator.db.dao.dlc.DlcStationDao;
import arc.resource.calculator.db.dao.primary.EngramDao;
import arc.resource.calculator.db.dao.primary.FolderDao;
import arc.resource.calculator.db.dao.primary.ResourceDao;
import arc.resource.calculator.db.dao.primary.StationDao;
import arc.resource.calculator.db.entity.DlcEntity;

public class InteractiveRepository {
    public static final String TAG = InteractiveRepository.class.getSimpleName();

    private final AppDatabase db;

    private final DlcDao dlcDao;

    private final EngramDao engramDao;
    private final ResourceDao resourceDao;
    private final StationDao stationDao;
    private final FolderDao folderDao;

    private final DlcEngramDao dlcEngramDao;
    private final DlcResourceDao dlcResourceDao;
    private final DlcStationDao dlcStationDao;
    private final DlcFolderDao dlcFolderDao;

    public InteractiveRepository(Application application) {
        db = AppDatabase.getInstance(application);

        dlcDao = db.dlcDao();

        engramDao = db.engramDao();
        resourceDao = db.resourceDao();
        stationDao = db.stationDao();
        folderDao = db.folderDao();

        dlcEngramDao = db.dlcEngramDao();
        dlcResourceDao = db.dlcResourceDao();
        dlcStationDao = db.dlcStationDao();
        dlcFolderDao = db.dlcFolderDao();
    }

    public AppDatabase getAppDatabase() {
        return db;
    }

    public DlcDao getDlcDao() {
        return dlcDao;
    }

    public EngramDao getEngramDao() {
        return engramDao;
    }

    public ResourceDao getResourceDao() {
        return resourceDao;
    }

    public StationDao getStationDao() {
        return stationDao;
    }

    public FolderDao getFolderDao() {
        return folderDao;
    }

    public DlcEngramDao getDlcEngramDao() {
        return dlcEngramDao;
    }

    public DlcResourceDao getDlcResourceDao() {
        return dlcResourceDao;
    }

    public DlcStationDao getDlcStationDao() {
        return dlcStationDao;
    }

    public DlcFolderDao getDlcFolderDao() {
        return dlcFolderDao;
    }

    public LiveData<DlcEntity> getDlcEntity(String dlcId) {
        return dlcDao.getDlc(dlcId);
    }
}