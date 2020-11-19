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

import arc.resource.calculator.db.entity.primary.FavoriteEntity;
import arc.resource.calculator.db.entity.primary.FolderEntity;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavoriteDao {
    String tableName = "favorites";

    @Insert(onConflict = REPLACE)
    void insert(FolderEntity entity);

    @Query("delete from favorites")
    void deleteAll();

    @Query("select * from favorites where gameId is :gameId")
    LiveData<List<FavoriteEntity>> getFavoriteList(String gameId);

    @Query("select * from favorites where uuid is :uuid")
    LiveData<FavoriteEntity> getFavorite(String uuid);
}
