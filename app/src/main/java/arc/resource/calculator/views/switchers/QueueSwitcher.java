package arc.resource.calculator.views.switchers;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.views.QueueRecyclerView;
import arc.resource.calculator.views.switchers.listeners.Listener;

public class QueueSwitcher extends ViewSwitcher implements Listener, LifecycleObserver {
    private static final String TAG = QueueSwitcher.class.getSimpleName();

    private TextView mTextView;
    private QueueRecyclerView mRecyclerView;

    @Override
    public void onError( Exception e ) {
        // switch view to textview, display error message
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( getContext().getString( R.string.switcher_queue_status_onerror ) );

        ExceptionObserver.getInstance().notifyExceptionCaught( TAG, e );
    }

    @Override
    public void onInit() {
        // switch view to textview, display fetching message
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( getContext().getString( R.string.switcher_queue_status_oninit ) );
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

        onStatusUpdate( getContext().getString( R.string.switcher_queue_status_onempty ) );
    }

    public QueueSwitcher( Context context ) {
        super( context );
    }

    public QueueSwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public void init( Lifecycle lifecycle ) {
        lifecycle.addObserver( this );
    }

    public void onCreate( LifecycleOwner owner ) {
        //  register as LifecycleObserver
        owner.getLifecycle().addObserver( this );

        // set size (height) to that of 1/5 screen's width
//        int viewSize = PrefsUtil.getInstance( getContext() ).getCraftableViewSize();
//        setLayoutParams( new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, viewSize ) );

        // instantiate our textview for status updates
        mTextView = findViewById( R.id.textview_queue );

        // instantiate recyclerView
        mRecyclerView = findViewById( R.id.gridview_queue );

        // onResume view with switcher observer
        // set this as observer, initialize adapter, create crafting queue instance, query for saved data, if any.
        mRecyclerView.onCreate( this );
    }

    @OnLifecycleEvent( Lifecycle.Event.ON_RESUME )
    public void onResume() {
        mRecyclerView.onResume();
    }

    @OnLifecycleEvent( Lifecycle.Event.ON_PAUSE )
    public void onPause() {
        mRecyclerView.pause();
    }

    @OnLifecycleEvent( Lifecycle.Event.ON_DESTROY )
    public void onDestroy( LifecycleOwner owner ) {
        owner.getLifecycle().removeObserver( this );
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

    public QueueRecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}