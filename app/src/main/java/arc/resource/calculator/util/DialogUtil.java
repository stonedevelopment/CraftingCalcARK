package arc.resource.calculator.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import arc.resource.calculator.BuildConfig;
import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseHelper;
import arc.resource.calculator.model.engram.QueueEngram;

public class DialogUtil {
    public abstract static class Callback {
        public void onResult( @Nullable Object result ) {
            // do nothing
        }

        public void onOk() {
            // do nothing
        }

        public void onCancel() {
            // do nothing
        }
    }

    public static AlertDialog Error( Context c, final Callback cb ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper( c, R.style.AlertDialogCustom ) );
        builder.setTitle( c.getString( R.string.dialog_error_title ) )
                .setIcon( R.drawable.dialog_icons_error )
                .setMessage( c.getString( R.string.dialog_error_message ) )
                .setNegativeButton( c.getString( R.string.dialog_error_button_negative ), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        cb.onCancel();
                    }
                } )
                .setPositiveButton( c.getString( R.string.dialog_error_button_positive ), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        cb.onOk();
                    }
                } );

        return builder.create();
    }

    public static AlertDialog EditQuantity( final Context context, String name, final Callback callback ) {
        final EditText editText = new EditText( context );
        editText.setTextColor( context.getResources().getColor( R.color.colorWhite ) );
        editText.setInputType( InputType.TYPE_CLASS_NUMBER );

        AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper( context, R.style.AlertDialogCustom ) );
        builder.setTitle( name )
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
                        String quantityText = editText.getText().toString();

                        if ( !quantityText.equals( "" ) && quantityText.length() < 5 ) {
                            int quantity = Integer.parseInt( quantityText );

                            if ( quantity > 0 )
                                callback.onResult( quantity );
                        }
                    }
                } );

        return builder.create();
    }

    public static AlertDialog Search( final Context context, final Callback callback ) {
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
                        String searchText = editText.getText().toString();

                        if ( !searchText.equals( "" ) ) {
                            callback.onResult( searchText );
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
                                "JSON File Version: " + PrefsUtil.getInstance().getJSONVersion() + "\n\n" +
                                "Screen Size: " + context.getString( R.string.dimens ) );

        return builder.create();
    }

    public static AlertDialog Details( Context context, QueueEngram craftable ) {
        View view = View.inflate( context, R.layout.dialog_detail, null );

        TextView name = ( TextView ) view.findViewById( R.id.name );
        name.setText( craftable.getName() );

        TextView description = ( TextView ) view.findViewById( R.id.description );
        description.setText( "" );

        AlertDialog.Builder builder = new AlertDialog.Builder( context );

        builder.setView( R.layout.dialog_detail );

        return builder.create();
    }
}