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

package arc.resource.calculator.ui.search.model;

import arc.resource.calculator.db.entity.primary.EngramEntity;

public class SearchItem {
    private final String uuid;
    private final String title;
    private final String imageFile;
    private final int viewType;
    private final String gameId;

    SearchItem(String uuid, String title, String imageFile, int viewType, String gameId) {
        this.uuid = uuid;
        this.title = title;
        this.imageFile = imageFile;
        this.viewType = viewType;
        this.gameId = gameId;
    }

    public static EngramSearchItem fromEngramEntity(EngramEntity entity) {
        return EngramSearchItem.fromEntity(entity);
    }

    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getImageFile() {
        return imageFile;
    }

    public int getViewType() {
        return viewType;
    }

    public String getGameId() {
        return gameId;
    }
}