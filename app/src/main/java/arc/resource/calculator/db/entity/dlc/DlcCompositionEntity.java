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

package arc.resource.calculator.db.entity.dlc;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.dlc.DlcCompositionDao;
import arc.resource.calculator.db.entity.primary.CompositionEntity;

@Entity(tableName = DlcCompositionDao.tableName)
public class DlcCompositionEntity extends CompositionEntity {
    private String dlcId;

    public DlcCompositionEntity(@NonNull String uuid,
                                @NonNull String engramId,
                                @NonNull Date lastUpdated,
                                @NonNull String gameId,
                                @NonNull String dlcId) {
        super(uuid, engramId, lastUpdated, gameId);
        this.dlcId = dlcId;
    }

    public static DlcCompositionEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, DlcCompositionEntity.class);
    }

    public String getDlcId() {
        return dlcId;
    }

    public void setDlcId(@NonNull String dlcId) {
        this.dlcId = dlcId;
    }
}