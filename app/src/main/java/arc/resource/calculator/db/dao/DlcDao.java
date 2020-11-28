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

package arc.resource.calculator.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.DlcEntity;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DlcDao {
    String tableName = "dlc";

    @Insert(onConflict = REPLACE)
    void insert(DlcEntity entity);

    @Query("delete from dlc")
    void deleteAll();

    @Query("select * from dlc where gameId is :gameId and totalConversion is :totalConversion order by name asc")
    LiveData<List<DlcEntity>> getDlcList(String gameId, boolean totalConversion);

    @Query("select * from dlc where uuid is :uuid")
    LiveData<DlcEntity> getDlc(String uuid);
}
