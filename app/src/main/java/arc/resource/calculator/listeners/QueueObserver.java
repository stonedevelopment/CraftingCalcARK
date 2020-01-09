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

package arc.resource.calculator.listeners;

import java.util.HashMap;

/**
 * Observing class helper for CraftableAdapter and InventoryAdapter
 * <p>
 * Alerts listeners of changes to the 'Queue' - adds, removes, etc.
 */

public class QueueObserver {
    private static QueueObserver sInstance;

    private HashMap<String, Listener> mListeners;

    public interface Listener {
        void onItemChanged(long craftableId, int quantity);
        void onItemRemoved(long craftableId);
        void onDataSetPopulated();
        void onDataSetEmpty();
    }

    public static QueueObserver getInstance() {
        if (sInstance == null)
            sInstance = new QueueObserver();

        return sInstance;
    }

    private QueueObserver() {
        mListeners = new HashMap<>();
    }

    public void registerListener(String key, Listener listener) {
        mListeners.put(key, listener);
    }

    public void unregisterListener(String key) {
        mListeners.remove(key);
    }

    public void notifyItemChanged(long craftableId, int quantity) {
        for (Listener listener : mListeners.values()) {
            listener.onItemChanged(craftableId, quantity);
        }
    }

    public void notifyItemRemoved(long craftableId) {
        for (Listener listener : mListeners.values()) {
            listener.onItemRemoved(craftableId);
        }
    }

    public void notifyDataSetEmpty() {
        for (Listener listener : mListeners.values()) {
            listener.onDataSetEmpty();
        }
    }

    public void notifyDataSetPopulated() {
        for (Listener listener : mListeners.values()) {
            listener.onDataSetPopulated();
        }
    }
}