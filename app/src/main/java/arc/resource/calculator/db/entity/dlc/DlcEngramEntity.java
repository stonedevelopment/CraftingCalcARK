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

import arc.resource.calculator.db.dao.dlc.DlcEngramDao;
import arc.resource.calculator.db.entity.primary.EngramEntity;

/**
 * Engram object for base game data (vanilla)
 * <p>
 * Engrams for DLC game data should extend from this class
 */
@Entity(tableName = DlcEngramDao.tableName)
public class DlcEngramEntity extends EngramEntity {
    @NonNull
    private String dlcId;

    public DlcEngramEntity(@NonNull String uuid,
                           @NonNull String name,
                           @NonNull String description,
                           @NonNull String imageFile,
                           int level,
                           int yield,
                           int points,
                           int xp,
                           int craftingTime,
                           @NonNull Date lastUpdated,
                           @NonNull String gameId,
                           @NonNull String dlcId) {
        super(uuid, name, description, imageFile, level, yield, points, xp, craftingTime, lastUpdated, gameId);
        this.dlcId = dlcId;
    }

    public static DlcEngramEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, DlcEngramEntity.class);
    }

    @NonNull
    public String getDlcId() {
        return dlcId;
    }

    public void setDlcId(@NonNull String dlcId) {
        this.dlcId = dlcId;
    }
}
