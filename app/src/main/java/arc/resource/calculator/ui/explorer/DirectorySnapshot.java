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

import java.util.List;

import javax.annotation.Nullable;

import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;

public class DirectorySnapshot {
    private final ExplorerItem parent;
    private final List<DirectoryItemEntity> directory;

    DirectorySnapshot(@Nullable ExplorerItem parent, List<DirectoryItemEntity> directory) {
        this.parent = parent;
        this.directory = directory;
    }

    public ExplorerItem getParent() {
        return parent;
    }

    public List<DirectoryItemEntity> getDirectory() {
        return directory;
    }

    boolean hasParent() {
        return parent != null;
    }
}
