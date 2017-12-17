package arc.resource.calculator.views.switchers;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.views.CraftableRecyclerView;
import arc.resource.calculator.views.switchers.listeners.Listener;

public class CraftableSwitcher extends ViewSwitcher implements Listener {
    private static final String TAG = CraftableSwitcher.class.getSimpleName();

    private ProgressBar mProgressBar;
    private CraftableRecyclerView mRecyclerView;

    @Override
    public void onError( Exception e ) {
        if ( isProgressBarVisible() )
            showNext();

        ExceptionObserver.getInstance().notifyExceptionCaught( TAG, e );
    }

    @Override
    public void onInit() {
        //  switch view to progress bar
        if ( !isProgressBarVisible() )
            showNext();
    }

    @Override
    public void onPopulated() {
        // switch view to recycler
        if ( isProgressBarVisible() )
            showNext();
    }

    @Override
    public void onEmpty() {
        //  switch to recycler, throw exception
        if ( isProgressBarVisible() )
            showNext();

        Log.wtf( TAG, "CraftableRecyclerView result is empty and should NOT be." );
    }

    public CraftableSwitcher( Context context ) {
        super( context );
    }

    public CraftableSwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public void onCreate() {
        // instantiate our textview for status updates
        mProgressBar = findViewById( R.id.progress_bar_craftables );

        // instantiate recyclerView
        mRecyclerView = findViewById( R.id.gridview_craftables );
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

    public void onSearch( String query ) {
        mRecyclerView.search( query );
    }

    private boolean isProgressBarVisible() {
        return getCurrentView().getId() == mProgressBar.getId();
    }

    public CraftableRecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
