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

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import arc.resource.calculator.db.dao.FolderDao;

public class FolderEntityWithChildren {

    @Embedded
    public FolderEntity folderEntity;

    @Relation(parentColumn = "rowid", entityColumn = "parentId")
    public List<FolderEntity> folderEntities;

    @Relation(parentColumn = "rowid", entityColumn = "parentId")
    public List<EngramEntity> engramEntities;
}
