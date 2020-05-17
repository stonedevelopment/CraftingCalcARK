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

package arc.resource.calculator.db.entity.primary;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import arc.resource.calculator.db.dao.primary.CompositeDao;

@Entity(tableName = CompositeDao.tableName)
public class CompositeEntity {
    @PrimaryKey
    private final String uuid;
    private final int quantity;
    private final String engramId;
    private final String resourceId;
    private final boolean isEngram;

    public CompositeEntity(String uuid, int quantity, String engramId, String resourceId, boolean isEngram) {
        this.uuid = uuid;
        this.quantity = quantity;
        this.engramId = engramId;
        this.resourceId = resourceId;
        this.isEngram = isEngram;
    }

    public String getUuid() {
        return uuid;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getEngramId() {
        return engramId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public boolean isEngram() {
        return isEngram;
    }
}