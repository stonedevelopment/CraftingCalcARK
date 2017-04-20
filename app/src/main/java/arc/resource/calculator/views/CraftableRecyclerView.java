package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

import arc.resource.calculator.R;
import arc.resource.calculator.adapters.CraftableAdapter;

public class CraftableRecyclerView extends RecyclerViewWithContextMenu {
    private static final String TAG = CraftableRecyclerView.class.getSimpleName();

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            // TODO: 4/18/2017 When one is increasing quantity, have it scroll to position, but if its just updating due to data fetching
            scrollToPosition( 1 );
        }

        @Override
        public void onItemRangeChanged( int positionStart, int itemCount ) {
            Log.d( TAG, "onItemRangeChanged: " + positionStart + ", " + itemCount );
            scrollToPosition( positionStart );
        }
    };

    public CraftableRecyclerView( Context context ) {
        super( context );
    }

    public CraftableRecyclerView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public CraftableRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
    }

    public void create() {
        Log.d( TAG, "onCreate: " );
        int numCols = getResources().getInteger( R.integer.gridview_column_count );
        setLayoutManager( new GridLayoutManager( getContext(), numCols ) );

        setAdapter( new CraftableAdapter( getContext() ) );
    }

    public void resume() {
        getAdapter().resume( mDataObserver );
    }

    public void pause() {
        getAdapter().pause( mDataObserver );
    }

    @Override
    public CraftableAdapter getAdapter() {
        return ( CraftableAdapter ) super.getAdapter();
    }
}