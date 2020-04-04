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

package arc.resource.calculator.ui.explorer;

public class ExplorerItem {
    private final int rowid;
    private final String title;
    private final String image;
    private final ExplorerItemType itemType;

    public ExplorerItem(int rowid, String title, String image, ExplorerItemType itemType) {
        this.rowid = rowid;
        this.title = title;
        this.image = image;
        this.itemType = itemType;
    }

    public int getId() {
        return rowid;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public ExplorerItemType getItemType() {
        return itemType;
    }
}