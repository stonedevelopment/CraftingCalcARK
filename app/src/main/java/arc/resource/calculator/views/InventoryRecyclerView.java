package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

import arc.resource.calculator.R;
import arc.resource.calculator.adapters.InventoryAdapter;

public class InventoryRecyclerView extends RecyclerViewWithContextMenu {
    private static final String TAG = InventoryRecyclerView.class.getSimpleName();

    private static Listener mListener;

    interface Listener {
        void onError( Exception e );

        void onInit();

        void onPopulated();

        void onEmpty();
    }

    public static class Observer {
        public void notifyExceptionCaught( Exception e ) {
            mListener.onError( e );
        }

        public void notifyInitializing() {
            mListener.onInit();
        }

        public void notifyDataSetPopulated() {
            mListener.onPopulated();
        }

        public void notifyDataSetEmpty() {
            mListener.onEmpty();
        }
    }

    public InventoryRecyclerView( Context context ) {
        super( context );
        init();
    }

    public InventoryRecyclerView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        init();
    }

    public InventoryRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
        init();
    }

    private void init() {
        Log.d( TAG, "init()" );

        int numCols = getResources().getInteger( R.integer.gridview_column_count );
        setLayoutManager( new GridLayoutManager( getContext(), numCols ) );

        setAdapter( new InventoryAdapter( getContext() ) );
    }

    public void startUp( Listener listener ) {
        // register listener from switcher
        mListener = listener;

        // setup up adapter and register this observer
        getAdapter().start( new Observer() );
    }

    public void shutDown() {
        // stop adapter and remove this observer
        getAdapter().stop();
    }

    @Override
    public InventoryAdapter getAdapter() {
        return ( InventoryAdapter ) super.getAdapter();
    }
}