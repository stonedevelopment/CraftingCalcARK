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

import arc.resource.calculator.db.entity.EngramEntity;

@Dao
public interface EngramDao {
    String tableName = "engrams";
    String columnName = "engramid";

    @Insert()
    void insert(EngramEntity engramEntity);

    @Query("delete from engrams")
    void deleteAll();

    @Query("select * from engrams where stationid = :stationId and parentid = :parentId order by name asc")
    LiveData<List<EngramEntity>> getEngrams(int stationId, int parentId);
}
