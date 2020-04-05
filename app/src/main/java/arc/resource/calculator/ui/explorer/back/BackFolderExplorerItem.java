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

package arc.resource.calculator.ui.explorer.back;

import arc.resource.calculator.ui.explorer.ExplorerItem;
import arc.resource.calculator.ui.explorer.ExplorerItemType;

public class BackFolderExplorerItem extends ExplorerItem {

    //  back folder button
    //  can hold a back button from current station to list of stations
    //  can hold a back button from a current folder to list parent folder or parent station

    public BackFolderExplorerItem(int rowid, String title, String image, ExplorerItemType itemType) {
        super(rowid, title, image, itemType);
    }
}
