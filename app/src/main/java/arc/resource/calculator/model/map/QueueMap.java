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

import arc.resource.calculator.model.engram.QueueEngram;

public class QueueMap extends SortableMap {
    public QueueMap() {
        super();
    }

    @Override
    public QueueEngram get(long key) {
        return (QueueEngram) super.get(key);
    }

    @Override
    public QueueEngram valueAt(int position) {
        return (QueueEngram) super.valueAt(position);
    }

    @Override
    public Comparable getComparable(int position) {
        return valueAt(position).getName();
    }
}
