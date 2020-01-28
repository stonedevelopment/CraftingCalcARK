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

package arc.resource.calculator.repository.queue;

import java.util.HashMap;

import arc.resource.calculator.model.engram.QueueEngram;

/**
 * Observable class helper for QueueRepository
 * <p>
 * Notifies observers of changes to the 'Queue' - adds, removes, etc.
 */

class QueueObservable {
    private static QueueObservable sInstance;

    private HashMap<String, QueueObserver> mObservers;

    public static QueueObservable getInstance() {
        if (sInstance == null)
            sInstance = new QueueObservable();

        return sInstance;
    }

    private QueueObservable() {
        mObservers = new HashMap<>();
    }

    void addObserver(String key, QueueObserver queueObserver) {
        mObservers.put(key, queueObserver);
    }

    void removeObserver(String key) {
        mObservers.remove(key);
    }

    public void removeObservers() {
        mObservers.clear();
    }

    void notifyEngramAdded(QueueEngram engram) {
        for (QueueObserver queueObserver : mObservers.values()) {
            queueObserver.onItemAdded(engram);
        }
    }

    void notifyEngramRemoved(QueueEngram engram) {
        for (QueueObserver queueObserver : mObservers.values()) {
            queueObserver.onItemRemoved(engram);
        }
    }

    void notifyEngramChanged(QueueEngram engram) {
        for (QueueObserver queueObserver : mObservers.values()) {
            queueObserver.onItemChanged(engram);
        }
    }

    void notifyQueueDataPopulating() {
        for (QueueObserver queueObserver : mObservers.values()) {
            queueObserver.onQueueDataPopulating();
        }
    }

    void notifyQueueDataPopulated() {
        for (QueueObserver queueObserver : mObservers.values()) {
            queueObserver.onQueueDataPopulated();
        }
    }

    void notifyQueueDataEmpty() {
        for (QueueObserver queueObserver : mObservers.values()) {
            queueObserver.onQueueDataEmpty();
        }
    }
}