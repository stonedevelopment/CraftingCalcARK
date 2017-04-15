package arc.resource.calculator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ViewSwitcher;

public class MainSwitcher extends ViewSwitcher {
    private static final String TAG = MainSwitcher.class.getSimpleName();

    public MainSwitcher( Context context ) {
        super( context );
        init();
    }

    public MainSwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
        init();
    }

    private void init() {
        Log.d( TAG, "init()" );
    }
}
