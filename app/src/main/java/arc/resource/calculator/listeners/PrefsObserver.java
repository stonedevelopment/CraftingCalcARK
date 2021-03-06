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
 * Observing class helper for CraftableAdapter and CraftingQueue
 * <p>
 * Alerts listeners of changes to SharedPreferences - onChanged and alerts listeners to save its local preference values
 */

public class PrefsObserver {
    private static PrefsObserver sInstance;

    private HashMap<String, Listener> mListeners;

    public static abstract class Listener {
        public void onPreferencesChanged(
                boolean dlcValueChange,
                boolean categoryPrefChange,
                boolean stationPrefChange,
                boolean levelPrefChange,
                boolean levelValueChange,
                boolean refinedPrefChange) {
            // do nothing
        }
    }

    public static PrefsObserver getInstance() {
        if (sInstance == null)
            sInstance = new PrefsObserver();

        return sInstance;
    }

    private PrefsObserver() {
        mListeners = new HashMap<>();
    }

    public void registerListener(String key, Listener listener) {
        mListeners.put(key, listener);
    }

    public void unregisterListener(String key) {
        mListeners.remove(key);
    }

    public void notifyPreferencesChanged(boolean dlcValueChange, boolean categoryPrefChange, boolean stationPrefChange,
                                         boolean levelPrefChange, boolean levelValueChange, boolean refinedPrefChange) {
        for (Listener listener : mListeners.values()) {
            listener.onPreferencesChanged(dlcValueChange, categoryPrefChange, stationPrefChange, levelPrefChange, levelValueChange, refinedPrefChange);
        }
    }
}
