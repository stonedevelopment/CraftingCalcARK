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

package arc.resource.calculator.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import arc.resource.calculator.db.dao.ResourceDao;

/**
 * Resource object for base game data (vanilla)
 * <p>
 * Resources for DLC game data should extend from this class
 */
@Entity(tableName = ResourceDao.tableName)
public class ResourceEntity {
    @PrimaryKey(autoGenerate = true)
    private final int rowId;

    //  name of resource
    private final String name;

    //  description of resource TODO: 3/28/2020 add descriptions for resources from website
    private final String description;

    //  filename of image in /assets folder
    private final String image;

    public ResourceEntity(int rowId, String name, String description, String image) {
        this.rowId = rowId;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public int getRowId() {
        return rowId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
