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

package arc.resource.calculator.db.dao.primary;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.primary.StationEntity;

@Dao
public interface StationDao {
    String tableName = "stations";
    String columnName = "stationid";

    @Query("select * from stations order by name asc")
    LiveData<List<StationEntity>> getStations();

    @Query("delete from stations")
    public void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    StationEntity insert(StationEntity stationEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<StationEntity> stationEntities);
}