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
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import arc.resource.calculator.db.entity.FolderEntityWithChildren;
import arc.resource.calculator.db.entity.StationEntity;
import arc.resource.calculator.db.entity.StationEntityWithChildren;

@Dao
public abstract class ExplorerDao {

    @Query("SELECT * from stations")
    public abstract LiveData<List<StationEntity>> getStations();

    @Transaction
    @Query("SELECT * from stations where rowid = :stationId")
    public abstract LiveData<StationEntityWithChildren> getChildrenItemsFromStation(int stationId);

    @Transaction
    @Query("SELECT * from folders where rowid = :folderId")
    public abstract LiveData<FolderEntityWithChildren> getChildrenItemsFromFolder(int folderId);
}