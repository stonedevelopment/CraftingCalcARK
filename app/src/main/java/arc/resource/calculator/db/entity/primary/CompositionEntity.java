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

import arc.resource.calculator.db.dao.primary.CompositionDao;

@Entity(tableName = CompositionDao.tableName)
public class CompositionEntity {
    @PrimaryKey
    private final String uuid;
    private final String engramId;
    private final String gameId;
}