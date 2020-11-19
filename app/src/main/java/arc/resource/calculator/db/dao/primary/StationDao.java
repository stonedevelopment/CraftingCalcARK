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
import androidx.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.db.entity.primary.StationEntity;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface StationDao {
    String tableName = "stations";

    @Insert(onConflict = REPLACE)
    void insert(StationEntity entity);

    @Query("delete from stations")
    void deleteAll();

    @Query("select * from stations where gameId is :gameId order by name asc")
    LiveData<List<StationEntity>> getStationList(String gameId);

    @Query("select * from stations where uuid is :uuid")
    LiveData<StationEntity> getStation(String uuid);

    @Query("select * from stations where name like :searchName order by name asc")
    LiveData<List<StationEntity>> searchByName(String searchName);
}