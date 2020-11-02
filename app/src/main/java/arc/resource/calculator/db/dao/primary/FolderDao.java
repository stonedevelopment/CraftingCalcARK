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

import arc.resource.calculator.db.entity.primary.FolderEntity;

@Dao
public interface FolderDao {
    String tableName = "folders";

    @Insert
    void insert(FolderEntity entity);

    @Query("delete from folders")
    void deleteAll();

    @Query("select * from folders where gameId is :gameId order by name asc")
    LiveData<List<FolderEntity>> getFolderList(String gameId);

    @Query("select * from folders where uuid is :uuid")
    LiveData<FolderEntity> getFolder(String uuid);

    @Query("select * from folders where name like :searchName order by name asc")
    LiveData<List<FolderEntity>> searchByName(String searchName);
}
