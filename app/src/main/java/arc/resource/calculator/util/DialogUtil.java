package arc.resource.calculator.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.widget.EditText;

import arc.resource.calculator.BuildConfig;
import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseHelper;

public class DialogUtil {
    public static AlertDialog Error( Context c ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper( c, R.style.AlertDialogCustom ) );
        builder.setTitle( c.getString( R.string.ad_error_reported_title ) )
                .setIcon( android.R.drawable.stat_notify_error )
                .setMessage( c.getString( R.string.ad_error_reported_message ) );

        return builder.create();
    }

    public static AlertDialog EditQuantity( final Context context, final long _id ) {
        final EditText editText = new EditText( context );
        editText.setTextColor( context.getResources().getColor( R.color.colorWhite ) );
        editText.setInputType( InputType.TYPE_CLASS_NUMBER );

        AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper( context, R.style.AlertDialogCustom ) );
        builder.setTitle( context.getString( R.string.quantity_dialog_full_title ) )
                .setIcon( android.R.drawable.ic_menu_edit )
                .setMessage( "Enter new quantity" )
                .setView( editText )
                .setNegativeButton( context.getString( R.string.quantity_dialog_negative_button ), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {

                    }
                } )
                .setPositiveButton( context.getString( R.string.quantity_dialog_positive_button ), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        if ( !editText.getText().toString().equals( "" ) ) {
                            int quantity = Integer.parseInt( editText.getText().toString() );

                            if ( quantity > 0 )
                                ListenerUtil.getInstance().requestUpdateQuantity( context, _id, quantity );
                        }
                    }
                } );

        return builder.create();
    }

    public static AlertDialog Search( final Context context ) {
        final EditText editText = new EditText( context );
        editText.setTextColor( context.getResources().getColor( R.color.colorWhite ) );

        AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper( context, R.style.AlertDialogCustom ) );
        builder.setTitle( context.getString( R.string.search_dialog_full_title ) )
                .setIcon( android.R.drawable.ic_menu_search )
                .setView( editText )
                .setNegativeButton( context.getString( R.string.search_dialog_negative_button ), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {

                    }
                } )
                .setPositiveButton( context.getString( R.string.search_dialog_positive_button ), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        if ( !editText.getText().toString().equals( "" ) ) {
                            String searchText = editText.getText().toString();

                            ListenerUtil.getInstance().requestSearch( context, searchText );
                        }
                    }
                } );

        return builder.create();
    }

    public static AlertDialog ReportEngram( final Context context, final long _id ) {
        final EditText editText = new EditText( context );
        editText.setTextColor( context.getResources().getColor( R.color.colorWhite ) );
        editText.setInputType( InputType.TYPE_CLASS_NUMBER );

        AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper( context, R.style.AlertDialogCustom ) );
        builder.setTitle( context.getString( R.string.quantity_dialog_full_title ) )
                .setIcon( android.R.drawable.ic_menu_edit )
                .setMessage( "Enter new quantity" )
                .setView( editText )
                .setNegativeButton( context.getString( R.string.quantity_dialog_negative_button ), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {

                    }
                } )
                .setPositiveButton( context.getString( R.string.quantity_dialog_positive_button ), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        if ( !editText.getText().toString().equals( "" ) ) {
                            int quantity = Integer.parseInt( editText.getText().toString() );

                            if ( quantity > 0 )
                                ListenerUtil.getInstance().requestUpdateQuantity( context, _id, quantity );
                        }
                    }
                } );

        return builder.create();
    }

    public static AlertDialog About( Context context ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper( context, R.style.AlertDialogCustom ) );
        builder.setTitle( context.getString( R.string.about_dialog_full_title ) )
                .setIcon( R.drawable.dialog_icons_about )
                .setMessage(
                        "Passionately developed by Shane Stone.\n\n" +
                                "Email:\n  jaredstone1982@gmail.com\n  stonedevs@gmail.com\n\n" +
                                "Twitter:\n  @MasterxOfxNone\n  @ARKResourceCalc\n\n" +
                                "Steam:\n  MasterxOfxNone\n" +
                                "Xbox Live:\n  MasterxOfxNone\n\n" +
                                "App Version: " + BuildConfig.VERSION_NAME + "/" + BuildConfig.VERSION_CODE + "\n" +
                                "Database Version: " + DatabaseHelper.DATABASE_VERSION + "\n" +
                                "JSON File Version: " + context.getString( R.string.json_version ) + "\n" );

        return builder.create();
    }
}
