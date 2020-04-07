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

package arc.resource.calculator.ui.explorer;

import android.app.Application;

import arc.resource.calculator.ui.explorer.engram.EngramExplorerRepository;
import arc.resource.calculator.ui.explorer.folder.FolderExplorerRepository;
import arc.resource.calculator.ui.explorer.station.StationExplorerRepository;

/**
 * Wrapper class for ExplorerViewModel
 */
class ExplorerItemRepository {
    private final StationExplorerRepository mStationExplorerRepository;
    private final FolderExplorerRepository mFolderExplorerRepository;
    private final EngramExplorerRepository mEngramExplorerRepository;

    ExplorerItemRepository(Application application) {
        mStationExplorerRepository = new StationExplorerRepository(application);
        mFolderExplorerRepository = new FolderExplorerRepository(application);
        mEngramExplorerRepository = new EngramExplorerRepository(application);
    }
}