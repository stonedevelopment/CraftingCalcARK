/*
 * Copyright (c) 2019 Jared Stone
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
package arc.resource.calculator.model.category;

import static arc.resource.calculator.ui.explorer.ExplorerAdapter.ROOT;

/**
 * A helper Category object used to provide root category details for Engrams without a Category
 */
public class RootCategory extends Category {

    public RootCategory() {
        super(ROOT, "(root)/", ROOT, null);
    }
}
