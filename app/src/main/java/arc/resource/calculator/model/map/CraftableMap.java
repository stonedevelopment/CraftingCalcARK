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

package arc.resource.calculator.model.map;

import arc.resource.calculator.model.engram.DisplayEngram;

public class CraftableMap extends SortableMap {

    public CraftableMap() {
        super();
    }

    @Override
    public DisplayEngram get(long key) {
        return (DisplayEngram) super.get(key);
    }

    @Override
    public DisplayEngram valueAt(int position) {
        return (DisplayEngram) super.valueAt(position);
    }

    @Override
    public Comparable getComparable(int position) {
        return valueAt(position).getName();
    }
}