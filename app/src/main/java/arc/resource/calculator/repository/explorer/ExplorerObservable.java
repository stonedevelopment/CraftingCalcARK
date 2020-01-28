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

package arc.resource.calculator.repository.explorer;

import java.util.HashMap;

/**
 * Observable class helper for QueueRepository
 * <p>
 * Notifies observers of changes to the 'Queue' - adds, removes, etc.
 */

class ExplorerObservable {
    private static ExplorerObservable sInstance;

    private HashMap<String, ExplorerObserver> mObservers;

    public static ExplorerObservable getInstance() {
        if (sInstance == null)
            sInstance = new ExplorerObservable();

        return sInstance;
    }

    private ExplorerObservable() {
        mObservers = new HashMap<>();
    }

    void addObserver(String key, ExplorerObserver explorerObserver) {
        mObservers.put(key, explorerObserver);
    }

    void removeObserver(String key) {
        mObservers.remove(key);
    }

    /**
     * Notifies observers when an Engram was updated in list.
     *
     * @param position global position of Engram
     */
    void notifyEngramUpdated(int position) {
        for (ExplorerObserver explorerObserver : mObservers.values()) {
            explorerObserver.onEngramUpdated(position);
        }
    }

    void notifyExplorerDataPopulating() {
        for (ExplorerObserver explorerObserver : mObservers.values()) {
            explorerObserver.onExplorerDataPopulating();
        }
    }

    void notifyExplorerDataPopulated() {
        for (ExplorerObserver explorerObserver : mObservers.values()) {
            explorerObserver.onExplorerDataPopulated();
        }
    }

    void notifyExplorerDataEmpty() {
        for (ExplorerObserver explorerObserver : mObservers.values()) {
            explorerObserver.onExplorerDataEmpty();
        }
    }
}