package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

import arc.resource.calculator.R;
import arc.resource.calculator.adapters.InventoryAdapter;
import arc.resource.calculator.views.switchers.listeners.Listener;
import arc.resource.calculator.views.switchers.listeners.Observer;

public class InventoryRecyclerView extends RecyclerViewWithContextMenu implements Observer {
    private static final String TAG = InventoryRecyclerView.class.getSimpleName();

    private static Listener mListener;

    @Override
    public void notifyExceptionCaught( Exception e ) {
        getListener().onError( e );
    }

    @Override
    public void notifyInitializing() {
        getListener().onInit();
    }

    @Override
    public void notifyDataSetPopulated() {
        getListener().onPopulated();
    }

    @Override
    public void notifyDataSetEmpty() {
        getListener().onEmpty();
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
        setAdapter( new InventoryAdapter( getContext(), this ) );
    }

    public void resume() {
//        getAdapter().resume();
    }

    public void pause() {
//        getAdapter().pause();
    }

    public void destroy() {
//        getAdapter().destroy();
    }

    @Override
    public InventoryAdapter getAdapter() {
        return ( InventoryAdapter ) super.getAdapter();
    }
}