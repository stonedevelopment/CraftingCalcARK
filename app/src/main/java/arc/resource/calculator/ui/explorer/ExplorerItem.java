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
    private final int mId;
    private final String mTitle;
    private final String mImage;
    private final ExplorerItemType mType;

    public ExplorerItem(int rowid, String title, String image, ExplorerItemType itemType) {
        mId = rowid;
        mTitle = title;
        mImage = image;
        mType = itemType;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImage() {
        return mImage;
    }

    public ExplorerItemType getItemType() {
        return mType;
    }
}