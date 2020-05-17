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

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import arc.resource.calculator.db.entity.primary.ResourceEntity;

@Dao
public interface ResourceDao {
    String tableName = "resources";

    @Insert()
    ResourceEntity insert(ResourceEntity resourceEntity);

    @Query("delete from resources")
    void deleteAll();
}
