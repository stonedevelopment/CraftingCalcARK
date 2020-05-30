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

public class EngramExplorerItem extends ExplorerItem {
    private int quantity;

    EngramExplorerItem(String uuid, String title, String imageFile, int viewType, String sourceId, String parentId, String gameId, int quantity) {
        super(uuid, title, imageFile, viewType, sourceId, parentId, gameId);
        setQuantity(quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}