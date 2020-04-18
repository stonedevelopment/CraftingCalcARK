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

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import arc.resource.calculator.db.dao.CompositionDao;

@Entity(tableName = CompositionDao.tableName)
public class CompositionEntity {
    @PrimaryKey(autoGenerate = true)
    private int rowId;

    //  rowid from stations table
    private int stationId;

    //  rowid from engrams table
    private int engramId;

    //  rowid from resources table  // TODO: 3/28/2020 determine how to show substitute items
    private int resourceId;

    //  amount required
    private int quantity;

    public CompositionEntity(int stationId, int engramId, int resourceId, int quantity) {
        this.stationId = stationId;
        this.engramId = engramId;
        this.resourceId = resourceId;
        this.quantity = quantity;
    }

    public int getRowId() {
        return rowId;
    }

    public int getStationId() {
        return stationId;
    }

    public int getEngramId() {
        return engramId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getQuantity() {
        return quantity;
    }
}