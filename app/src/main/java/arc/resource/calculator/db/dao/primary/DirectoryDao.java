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

import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DirectoryDao {
    String tableName = "directory";

    @Insert(onConflict = REPLACE)
    void insert(DirectoryItemEntity entity);

    @Query("delete from directory")
    void deleteAll();

    @Query("select * from directory " +
            "where gameId is :gameId and parentId is :parentId " +
            "order by viewType asc, name asc")
    LiveData<List<DirectoryItemEntity>> getDirectoryItemList(String gameId, String parentId);

    @Query("select * from directory where uuid is :uuid")
    LiveData<DirectoryItemEntity> getDirectoryItem(String uuid);
}