package arc.resource.calculator.listeners;

import java.util.ArrayList;
import java.util.List;

/**
 * Observing class helper for CraftableAdapter and InventoryAdapter
 * <p>
 * Alerts listeners of changes to the 'Queue' - adds, removes, etc.
 */

public class QueueObserver {
    private static QueueObserver sInstance;

    private List<Listener> mListeners;

    public interface Listener {
        void onItemChanged( long craftableId, int quantity );

        void onItemRemoved( long craftableId );

        void onDataSetChanged();

        void onDataSetEmpty();
    }

    public static QueueObserver getInstance() {
        if ( sInstance == null )
            sInstance = new QueueObserver();

        return sInstance;
    }

    private QueueObserver() {
        mListeners = new ArrayList<>();
    }

    public void registerListener( Listener listener ) {
        mListeners.add( listener );
    }

    public void unregisterListener( Listener listener ) {
        mListeners.remove( listener );
    }

    public void notifyItemChanged( long craftableId, int quantity ) {
        for ( Listener listener : mListeners ) {
            listener.onItemChanged( craftableId, quantity );
        }
    }

    public void notifyItemRemoved( long craftableId ) {
        for ( Listener listener : mListeners ) {
            listener.onItemRemoved( craftableId );
        }
    }

    public void notifyDataSetEmpty() {
        for ( Listener listener : mListeners ) {
            listener.onDataSetEmpty();
        }
    }

    public void notifyDataSetChanged() {
        for ( Listener listener : mListeners ) {
            listener.onDataSetChanged();
        }
    }
}