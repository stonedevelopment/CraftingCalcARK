package arc.resource.calculator.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import arc.resource.calculator.R;

public class CraftableLayout extends LinearLayout {
    CraftableRecyclerView mRecyclerView;
    NavigationTextView mTextView;

    public CraftableLayout( Context context ) {
        super( context );
    }

    public CraftableLayout( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public CraftableLayout( Context context, @Nullable AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public CraftableLayout( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    public void onCreate() {
        mRecyclerView = ( CraftableRecyclerView ) findViewById( R.id.gridview_craftables );
        mRecyclerView.create();

        mTextView = ( NavigationTextView ) findViewById( R.id.textview_navigation_hierarchy );
        mTextView.create();
    }

    public void onResume() {
        mTextView.resume();
        mRecyclerView.resume();
    }

    public void onPause() {
        mTextView.pause();
        mRecyclerView.pause();
    }

    public void onDestroy() {
        mTextView.destroy();
        mRecyclerView.destroy();
    }

    public void onSearch( String query ) {
        mRecyclerView.getAdapter().searchData( query );
    }
}
