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
 * Base Category object
 */
public class BackCategory extends Category {
    private final String mFile;

    public BackCategory( long _id, String name, long parent ) {
        super( _id, name, parent );
        mFile = "back_folder.png";
    }

    public String getImagePath() {
        return mFile;
    }
}
