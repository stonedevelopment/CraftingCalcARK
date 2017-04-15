package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

import arc.resource.calculator.R;
import arc.resource.calculator.adapters.CraftableAdapter;

public class CraftableRecyclerView extends RecyclerViewWithContextMenu {
    private static final String TAG = CraftableRecyclerView.class.getSimpleName();

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            scrollToPosition( 1 );

            // TODO: 4/10/2017 figureout a way to receive navigation text to textview, its not in this recyclerview and its not in mainacivity
//            getAdapter().buildHierarchy();
        }

        @Override
        public void onItemRangeChanged( int positionStart, int itemCount ) {
            scrollToPosition( positionStart );
        }
    };

    public CraftableRecyclerView( Context context ) {
        super( context );
        init();
    }

    public CraftableRecyclerView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        init();
    }

    public CraftableRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
        init();
    }

    private void init() {
        int numCols = getResources().getInteger( R.integer.gridview_column_count );
        setLayoutManager( new GridLayoutManager( getContext(), numCols ) );

        setAdapter( new CraftableAdapter( getContext() ) );
    }

    @Override
    protected void onWindowVisibilityChanged( int visibility ) {
        if ( visibility == VISIBLE ) {
            getAdapter().start( mDataObserver );
        } else {
            getAdapter().stop( mDataObserver );
        }
    }

    @Override
    public CraftableAdapter getAdapter() {
        return ( CraftableAdapter ) super.getAdapter();
    }
}