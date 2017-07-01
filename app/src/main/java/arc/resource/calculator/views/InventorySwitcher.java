package arc.resource.calculator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObserver;

public class InventorySwitcher extends ViewSwitcher implements InventoryRecyclerView.Listener {
    private static final String TAG = InventorySwitcher.class.getSimpleName();

    private TextView mTextView;
    private InventoryRecyclerView mRecyclerView;

    @Override
    public void onError( Exception e ) {
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( String.format(
                getContext().getString( R.string.content_main_switcher_error_format ),
                getContext().getString( R.string.content_main_switcher_inventory ) ) );

        ExceptionObserver.getInstance().notifyExceptionCaught( TAG, e );
    }

    @Override
    public void onInit() {
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( String.format(
                getContext().getString( R.string.content_main_switcher_fetch_format ),
                getContext().getString( R.string.content_main_switcher_inventory ) ) );
    }

    @Override
    public void onPopulated() {
        // switch view to recycler
        if ( !isRecyclerViewShown() )
            showNext();
    }

    @Override
    public void onEmpty() {
        // switch view to textview, display empty message
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( String.format(
                getContext().getString( R.string.content_main_switcher_empty_format ),
                getContext().getString( R.string.content_main_switcher_inventory ) ) );
    }

    public InventorySwitcher( Context context ) {
        super( context );
    }

    public InventorySwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public void onCreate() {
        // instantiate our textview for status updates
        mTextView = ( TextView ) findViewById( R.id.textview_inventory );

        // instantiate recyclerView
        mRecyclerView = ( InventoryRecyclerView ) findViewById( R.id.gridview_inventory );
        mRecyclerView.create( this );
    }

    public void onResume() {
        mRecyclerView.resume();
    }

    public void onPause() {
        mRecyclerView.pause();
    }

    public void onDestroy() {
        mRecyclerView.destroy();
    }

    private void onStatusUpdate( String text ) {
        mTextView.setText( text );
    }

    private boolean isTextViewShown() {
        return getCurrentView().getId() == mTextView.getId();
    }

    private boolean isRecyclerViewShown() {
        return getCurrentView().getId() == mRecyclerView.getId();
    }
}
