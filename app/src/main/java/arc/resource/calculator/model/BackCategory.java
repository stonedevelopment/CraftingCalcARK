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
package arc.resource.calculator.model;

/**
 * A helper Category object used to provide back category details, visual and functional
 */
public class BackCategory extends Category {
    /**
     * Constructor which instantiates a super object with custom variables: "back_folder.png"
     *
     * @param id     Id of requested category
     * @param parent Parent id of requested category
     */
    public BackCategory(long id, long parent) {
        super(id, null, parent, "back_folder.webp");
    }
}
