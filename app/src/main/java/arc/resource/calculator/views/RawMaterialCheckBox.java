package arc.resource.calculator.views;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;

import arc.resource.calculator.util.PrefsUtil;

public class RawMaterialCheckBox extends AppCompatCheckBox {
    private static final String TAG = RawMaterialCheckBox.class.getSimpleName();

    public RawMaterialCheckBox( Context context ) {
        super( context );
    }

    public RawMaterialCheckBox( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public RawMaterialCheckBox( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    public void create() {
        setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                PrefsUtil.getInstance( getContext() ).saveRefinedFilterPreference( isChecked() );
            }
        } );

        setChecked( PrefsUtil.getInstance( getContext() ).getRefinedFilterPreference() );
    }

    public void resume() {
    }

    public void pause() {
    }

    public void destroy() {
    }

}
