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

package arc.resource.calculator.model.ui.interactive;

public class InteractiveHeader {
    private final String title;
    private final int viewType;
    private int quantity;

    protected InteractiveHeader(String title, int viewType) {
        this.title = title;
        this.viewType = viewType;
        this.quantity = 0;
    }

    public String getTitle() {
        return title;
    }

    public int getViewType() {
        return viewType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}