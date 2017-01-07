package arc.resource.calculator.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextThemeWrapper;

import arc.resource.calculator.R;

public class AlertUtil {
    public static AlertDialog AlertDialogError( Context c ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper( c, R.style.AlertDialogCustom ) );
        builder.setIcon( android.R.drawable.stat_notify_error );
        builder.setTitle( c.getString( R.string.ad_error_reported_title ) );
        builder.setMessage( c.getString( R.string.ad_error_reported_message ) );

        return builder.create();
    }
}
