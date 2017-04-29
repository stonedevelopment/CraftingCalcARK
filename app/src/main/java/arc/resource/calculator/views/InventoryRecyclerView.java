package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

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

    public InventoryRecyclerView( Context context ) {
        super( context );
    }

    public InventoryRecyclerView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public InventoryRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) {
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
        setAdapter( new InventoryAdapter( getContext(), new Observer() ) );
    }

    public void resume() {
        getAdapter().resume();
    }

    public void pause() {
        getAdapter().pause();
    }

    public void destroy() {
        getAdapter().destroy();
    }

    @Override
    public InventoryAdapter getAdapter() {
        return ( InventoryAdapter ) super.getAdapter();
    }
}