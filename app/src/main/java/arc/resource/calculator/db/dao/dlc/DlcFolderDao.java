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

import arc.resource.calculator.db.entity.dlc.DlcFolderEntity;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DlcFolderDao {
    String tableName = "dlc_folders";

    @Insert(onConflict = REPLACE)
    void insert(DlcFolderEntity entity);

    @Query("delete from dlc_folders")
    void deleteAll();

    @Query("select * from dlc_folders where gameId is :gameId order by name asc")
    LiveData<List<DlcFolderEntity>> getFolderList(String gameId);

    @Query("select * from dlc_folders where uuid is :uuid")
    LiveData<DlcFolderEntity> getFolder(String uuid);
}
