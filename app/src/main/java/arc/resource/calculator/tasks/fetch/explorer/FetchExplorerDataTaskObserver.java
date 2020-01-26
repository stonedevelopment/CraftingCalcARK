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

package arc.resource.calculator.tasks.fetch.explorer;

import arc.resource.calculator.model.map.CategoryMap;
import arc.resource.calculator.model.map.CraftableMap;
import arc.resource.calculator.model.map.StationMap;
import arc.resource.calculator.tasks.fetch.FetchDataTaskObserver;

public interface FetchExplorerDataTaskObserver extends FetchDataTaskObserver {
    void onFetchSuccess(StationMap stationMap, CategoryMap categoryMap, CraftableMap craftableMap);
}
