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

package arc.resource.calculator.db.dao.dlc;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.dlc.DlcStationEntity;
import arc.resource.calculator.db.entity.primary.StationEntity;

@Dao
public interface DlcStationDao {
    String tableName = "dlc_stations";

    @Insert
    void insert(DlcStationEntity entity);

    @Query("delete from dlc_stations")
    void deleteAll();

    @Query("select * from dlc_stations where dlcId is :dlcId order by name asc")
    LiveData<List<DlcStationEntity>> getStationList(String dlcId);

    @Query("select * from dlc_stations where uuid is :uuid")
    LiveData<DlcStationEntity> getStation(String uuid);
}