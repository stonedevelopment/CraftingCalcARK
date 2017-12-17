package arc.resource.calculator.views.layouts;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import arc.resource.calculator.R;
import arc.resource.calculator.views.switchers.QueueSwitcher;

public class QueueLayout extends FrameLayout {
    private QueueSwitcher mSwitcher;

    public QueueLayout( Context context ) {
        super( context );

    }

    public QueueLayout( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );

    }

    public QueueLayout( Context context, @Nullable AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public QueueLayout( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );

    }

    public void onCreate() {
        mSwitcher = findViewById( R.id.switcher_queue );
        mSwitcher.onCreate();
    }

    public void onResume() {
        mSwitcher.onResume();
    }

    public void onPause() {
        mSwitcher.onPause();
    }

    public void onDestroy() {
        mSwitcher.onDestroy();
    }

    public QueueSwitcher getSwitcher() {
        return mSwitcher;
    }
}
