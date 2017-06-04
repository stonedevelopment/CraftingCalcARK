package arc.resource.calculator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.util.PrefsUtil;

public class QueueSwitcher extends ViewSwitcher implements QueueRecyclerView.Listener {
    private static final String TAG = QueueSwitcher.class.getSimpleName();

    private TextView mTextView;
    private QueueRecyclerView mRecyclerView;

    @Override
    public void onError( Exception e ) {
        // switch view to textview, display error message
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( "An error occurred while fetching crafting queue." );

        ExceptionObserver.getInstance().notifyExceptionCaught( TAG, e );
    }

    @Override
    public void onInit() {
        // switch view to textview, display fetching message
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( "Fetching crafting queue.." );
    }

    @Override
    public void onPopulated() {
        // data is ready for viewing, switch view to recycler
        if ( !isRecyclerViewShown() )
            showNext();
    }

    @Override
    public void onEmpty() {
        // switch view to textview, display empty message
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( "Crafting queue is empty." );
    }

    public QueueSwitcher( Context context ) {
        super( context );
    }

    public QueueSwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public void onCreate() {
        Log.d( TAG, "onCreate: " );
        // set size (height) to that of 1/5 screen's width
        int viewSize = PrefsUtil.getInstance( getContext() ).getCraftableViewSize();
        setLayoutParams( new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, viewSize ) );

        // instantiate our textview for status updates
        mTextView = ( TextView ) findViewById( R.id.textview_queue );

        // instantiate recyclerView
        mRecyclerView = ( QueueRecyclerView ) findViewById( R.id.gridview_queue );

        // onResume view with switcher observer
        // set this as observer, initialize adapter, create crafting queue instance, query for saved data, if any.
        mRecyclerView.create( this );
    }

    public void onResume() {
        mRecyclerView.resume();
    }

    public void onPause() {
        Log.d( TAG, "onPause: " );
        mRecyclerView.pause();
    }

    public void onDestroy() {
        Log.d( TAG, "onDestroy: " );
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