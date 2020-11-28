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

import arc.resource.calculator.db.entity.primary.ResourceEntity;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ResourceDao {
    String tableName = "resources";

    @Insert(onConflict = REPLACE)
    void insert(ResourceEntity entity);

    @Query("delete from resources")
    void deleteAll();

    @Query("select * from resources " +
            "where uuid is :uuid")
    LiveData<ResourceEntity> getResource(String uuid);

    @Query("select * from resources " +
            "where gameId is :gameId " +
            "order by name asc")
    LiveData<List<ResourceEntity>> getResourceList(String gameId);

    @Query("select * from resources " +
            "where name like :searchName and gameId is :gameId " +
            "order by name asc")
    LiveData<List<ResourceEntity>> searchByName(String searchName, String gameId);
}