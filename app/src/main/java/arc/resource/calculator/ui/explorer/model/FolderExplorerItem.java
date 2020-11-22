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

package arc.resource.calculator.ui.explorer.model;

public class FolderExplorerItem extends ExplorerItem {
    FolderExplorerItem(String uuid, String title, int viewType, String sourceId, String parentId, String gameId) {
        super(uuid, title, viewType, sourceId, parentId, gameId);
    }
}