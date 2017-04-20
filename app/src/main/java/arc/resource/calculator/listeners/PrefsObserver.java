package arc.resource.calculator.listeners;

import java.util.ArrayList;
import java.util.List;

/**
 * Observing class helper for CraftableAdapter and CraftingQueue
 * <p>
 * Alerts listeners of changes to SharedPreferences - onChanged and alerts listeners to save its local preference values
 */

public class PrefsObserver {
    private static PrefsObserver sInstance;

    private List<Listener> mListeners;

    public static abstract class Listener {
        public void onPreferencesChanged( boolean dlcValueChange, boolean categoryPrefChange, boolean stationPrefChange, boolean levelPrefChange, boolean levelValueChange, boolean refinedPrefChange ) {
            // do nothing
        }

        public void onSavePreferencesRequested() {
            // do nothing
        }
    }

    public static PrefsObserver getInstance() {
        if ( sInstance == null )
            sInstance = new PrefsObserver();

        return sInstance;
    }

    private PrefsObserver() {
        mListeners = new ArrayList<>();
    }

    public void registerListener( Listener listener ) {
        mListeners.add( listener );
    }

    public void unregisterListener( Listener listener ) {
        mListeners.remove( listener );
    }

    public void notifyPreferencesChanged( boolean dlcValueChange, boolean categoryPrefChange, boolean stationPrefChange,
                                          boolean levelPrefChange, boolean levelValueChange, boolean refinedPrefChange ) {
        for ( Listener listener : mListeners ) {
            listener.onPreferencesChanged( dlcValueChange, categoryPrefChange, stationPrefChange, levelPrefChange, levelValueChange, refinedPrefChange );
        }
    }

    public void requestPreferencesSave() {
        for ( Listener listener : mListeners ) {
            listener.onSavePreferencesRequested();
        }
    }
}
