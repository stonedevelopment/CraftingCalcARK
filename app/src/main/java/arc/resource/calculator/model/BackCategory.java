/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: A:RC, a resource calculator for ARK:Survival Evolved
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
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
    public BackCategory( long id, long parent ) {
        super( id, null, parent, "back_folder.webp" );
    }
}
