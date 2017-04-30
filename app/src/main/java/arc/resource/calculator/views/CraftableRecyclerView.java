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

    private static Listener mListener;

    interface Listener {
        void onError( Exception e );

        void onInit();

        void onPopulated();

        void onEmpty();
    }

    public static class Observer {
        public void notifyExceptionCaught( Exception e ) {
            getListener().onError( e );
        }

        public void notifyInitializing() {
            getListener().onInit();
        }

        public void notifyDataSetPopulated() {
            getListener().onPopulated();
        }

        public void notifyDataSetEmpty() {
            getListener().onEmpty();
        }
    }

    public CraftableRecyclerView( Context context ) {
        super( context );
    }

    public CraftableRecyclerView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public CraftableRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
    }

    private static Listener getListener() {
        return mListener;
    }

    private void setListener( Listener listener ) {
        mListener = listener;
    }

    public void create( Listener listener ) {
        int numCols = getResources().getInteger( R.integer.gridview_column_count );
        setLayoutManager( new GridLayoutManager( getContext(), numCols ) );
        setListener( listener );
        setAdapter( new CraftableAdapter( getContext(), new Observer() ) );
    }

    public void resume() {
        getAdapter().registerAdapterDataObserver( mDataObserver );
        getAdapter().resume();
    }

    public void pause() {
        getAdapter().unregisterAdapterDataObserver( mDataObserver );
        getAdapter().pause();
    }

    public void destroy() {
        getAdapter().destroy();
    }

    public void search( String query ) {
        getAdapter().searchData( query );
    }

    @Override
    public CraftableAdapter getAdapter() {
        return ( CraftableAdapter ) super.getAdapter();
    }
}