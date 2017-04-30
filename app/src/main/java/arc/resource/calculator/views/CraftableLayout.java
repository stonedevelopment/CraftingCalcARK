package arc.resource.calculator.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import arc.resource.calculator.R;

public class CraftableLayout extends LinearLayout {
    CraftableSwitcher mSwitcher;
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
        mSwitcher = ( CraftableSwitcher ) findViewById( R.id.switcher_craftables );
        mSwitcher.onCreate();

        mTextView = ( NavigationTextView ) findViewById( R.id.textview_navigation_hierarchy );
        mTextView.create();
    }

    public void onResume() {
        mTextView.resume();
        mSwitcher.onResume();
    }

    public void onPause() {
        mTextView.pause();
        mSwitcher.onPause();
    }

    public void onDestroy() {
        mTextView.destroy();
        mSwitcher.onDestroy();
    }

    public void onSearch( String query ) {
        mSwitcher.onSearch( query );
    }
}
