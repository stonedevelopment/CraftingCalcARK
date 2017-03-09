package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import arc.resource.calculator.R;
import arc.resource.calculator.adapters.DisplayCaseListAdapter;

public class DisplayCaseRecyclerView extends RecyclerViewWithContextMenu {
    private static final String TAG = DisplayCaseRecyclerView.class.getSimpleName();

    final RecyclerView.AdapterDataObserver dataObserver = new RecyclerView.AdapterDataObserver() {
        private final String TAG = "AdapterDataObserver";

        @Override
        public void onChanged() {
            super.onChanged();
            Log.d( TAG, "onChanged()" );
            scrollToPosition( 1 );
        }

        @Override
        public void onItemRangeChanged( int positionStart, int itemCount ) {
            super.onItemRangeChanged( positionStart, itemCount );
            Log.d( TAG, "onItemRangeChanged(): " + positionStart + ", " + itemCount );
            scrollToPosition( positionStart );
        }

        @Override
        public void onItemRangeInserted( int positionStart, int itemCount ) {
            super.onItemRangeInserted( positionStart, itemCount );
            Log.d( TAG, "onItemRangeInserted(): " + positionStart + ", " + itemCount );
        }

        @Override
        public void onItemRangeRemoved( int positionStart, int itemCount ) {
            super.onItemRangeRemoved( positionStart, itemCount );
            Log.d( TAG, "onItemRangeRemoved(): " + positionStart + ", " + itemCount );
        }
    };

    public DisplayCaseRecyclerView( Context context ) throws Exception {
        super( context );

        init( context );
    }

    public DisplayCaseRecyclerView( Context context, @Nullable AttributeSet attrs ) throws Exception {
        super( context, attrs );

        init( context );
    }

    public DisplayCaseRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) throws Exception {
        super( context, attrs, defStyle );

        init( context );
    }

    private void init( Context context ) {
        DisplayCaseListAdapter adapter = DisplayCaseListAdapter.getInstance( context, false );
        adapter.registerAdapterDataObserver( dataObserver );
        setAdapter( adapter );

        int numCols = getResources().getInteger( R.integer.display_case_column_count );
        setLayoutManager( new GridLayoutManager( getContext(), numCols ) );
    }
}