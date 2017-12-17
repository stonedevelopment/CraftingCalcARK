package arc.resource.calculator.views.layouts;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import arc.resource.calculator.R;
import arc.resource.calculator.views.InventoryRecyclerView;
import arc.resource.calculator.views.switchers.listeners.Listener;
import arc.resource.calculator.views.switchers.listeners.Observer;

/**
 * Container class for calculating resources and crafting steps
 *
 * Implements a listener that listens to views for errors, etc.
 * Implements an observer that communicates back to CalculateSwitcher
 */
public class CalculateLayout extends LinearLayout implements Listener, Observer {
    private InventoryRecyclerView mInventoryRecyclerView;

    //  Listener from CalculateSwitcher
    private Listener mListener;

    @Override
    public void onError( Exception e ) {
        notifyExceptionCaught( e );
    }

    @Override
    public void onInit() {
        notifyInitializing();
    }

    @Override
    public void onPopulated() {
        notifyDataSetPopulated();
    }

    @Override
    public void onEmpty() {
        notifyDataSetEmpty();
    }

    public CalculateLayout( Context context ) {
        super( context );
    }

    public CalculateLayout( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public CalculateLayout( Context context, @Nullable AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public CalculateLayout( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    private Listener getListener() {
        return mListener;
    }

    private void setListener( Listener listener ) {
        mListener = listener;
    }

    public void onCreate( Listener listener ) {
        setListener( listener );
        mInventoryRecyclerView = ( InventoryRecyclerView ) findViewById( R.id.gridview_inventory );
        mInventoryRecyclerView.create( this );
    }

    public void onResume() {
        mInventoryRecyclerView.resume();
    }

    public void onPause() {
        mInventoryRecyclerView.pause();
    }

    public void onDestroy() {
        mInventoryRecyclerView.destroy();
    }

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
}
