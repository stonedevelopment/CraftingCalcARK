package arc.resource.calculator.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by jared on 4/27/2017.
 */

public class SquareRelativeLayout extends RelativeLayout {
    public SquareRelativeLayout( Context context ) {
        super( context );
    }

    public SquareRelativeLayout( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public SquareRelativeLayout( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public SquareRelativeLayout( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
        super.onMeasure( heightMeasureSpec, heightMeasureSpec );
    }
}
