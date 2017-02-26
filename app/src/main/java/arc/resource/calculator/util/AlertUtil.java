package arc.resource.calculator.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.widget.EditText;

import arc.resource.calculator.R;

public class AlertUtil {
    public static AlertDialog AlertDialogError( Context c ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper( c, R.style.AlertDialogCustom ) );
        builder.setIcon( android.R.drawable.stat_notify_error );
        builder.setTitle( c.getString( R.string.ad_error_reported_title ) );
        builder.setMessage( c.getString( R.string.ad_error_reported_message ) );

        return builder.create();
    }

    public static AlertDialog AlertDialogEditQuantity( final Context c, final long _id ) {
        final EditText editText = new EditText( c.getApplicationContext() );
        editText.setInputType( InputType.TYPE_CLASS_NUMBER );

        AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper( c, R.style.AlertDialogCustom ) );
        builder.setTitle( c.getString( R.string.quantity_dialog_full_title ) )
                .setIcon( R.drawable.wood_signs_wooden_sign )
                .setMessage( "Enter new quantity" )
                .setView( editText )
                .setPositiveButton( c.getString( R.string.quantity_dialog_save_button ), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        int quantity = Integer.parseInt( editText.getText().toString() );

                        if ( quantity > 0 )
                            ListenerUtil.getInstance().requestUpdateQuantity( c, _id, quantity );
                    }
                } );

        return builder.create();
    }
}
