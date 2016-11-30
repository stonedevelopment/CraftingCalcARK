package arc.resource.calculator;
/**
 * Copyright (C) 2011-2013, Karsten Priegnitz
 * <p/>
 * Permission to use, copy, modify, and distribute this piece of software
 * for any purpose with or without fee is hereby granted, provided that
 * the above copyright notice and this permission notice appear in the
 * source code of all copies.
 * <p/>
 * It would be appreciated if you mention the author in your change log,
 * contributors list or the like.
 *
 * @author: Karsten Priegnitz
 * @see: http://code.google.com/p/android-change-log/
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.ContextThemeWrapper;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HelpDialog {

    private final Context context;
    private static final String TAG = "HelpDialog";

    public HelpDialog( Context context ) {
        this.context = context;
    }

    protected AlertDialog getDialog() {
        WebView wv = new WebView( this.context );

        wv.setBackgroundColor( ContextCompat.getColor( this.context, android.R.color.black ) );
        wv.loadDataWithBaseURL( null, this.getLog(), "text/html", "UTF-8", null );

        AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper( this.context, android.R.style.Theme_Dialog ) );
        builder.setTitle(
                context.getString( R.string.help_dialog_full_title ) )
                .setView( wv )
                .setCancelable( true )
                // OK button
                .setPositiveButton( context.getString( R.string.help_dialog_ok_button ),
                        new DialogInterface.OnClickListener() {
                            public void onClick( DialogInterface dialog, int which ) {
                            }
                        } );

        return builder.create();
    }

    /**
     * modes for HTML-Lists (bullet, numbered)
     */
    private enum Listmode {
        NONE, ORDERED, UNORDERED,
    }

    private Listmode listMode = Listmode.NONE;
    private StringBuffer sb = null;

    protected String getLog() {
        // read changelog.txt file
        sb = new StringBuffer();
        try {
            InputStream ins = context.getResources().openRawResource( R.raw.helpdialog );
            BufferedReader br = new BufferedReader( new InputStreamReader( ins ) );

            String line;

            // sections
            while ( ( line = br.readLine() ) != null ) {
                line = line.trim();

                char marker = line.length() > 0 ? line.charAt( 0 ) : 0;
                if ( marker == '$' ) {
                    // begin of a version section
                    this.closeList();
//                    String version = line.substring(1).trim();
                } else {
                    switch ( marker ) {
                        case '%':
                            // line contains version title
                            this.closeList();
                            sb.append( "<div class='title'>" + line.substring( 1 ).trim() + "</div>\n" );
                            break;
                        case '_':
                            // line contains version title
                            this.closeList();
                            sb.append( "<div class='subtitle'>" + line.substring( 1 ).trim() + "</div>\n" );
                            break;
                        case '!':
                            // line contains free text
                            this.closeList();
                            sb.append( "<div class='freetext'>" + line.substring( 1 ).trim() + "</div>\n" );
                            break;
                        case '#':
                            // line contains numbered list item
                            this.openList( Listmode.ORDERED );
                            sb.append( "<li>" + line.substring( 1 ).trim() + "</li>\n" );
                            break;
                        case '*':
                            // line contains bullet list item
                            this.openList( Listmode.UNORDERED );
                            sb.append( "<li>" + line.substring( 1 ).trim() + "</li>\n" );
                            break;
                        default:
                            // no special character: just use line as is
                            this.closeList();
                            sb.append( line + "\n" );
                    }
                }
            }
            this.closeList();
            br.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    protected void openList( Listmode listMode ) {
        if ( this.listMode != listMode ) {
            closeList();
            if ( listMode == Listmode.ORDERED ) {
                sb.append( "<div class='list'><ol>\n" );
            } else if ( listMode == Listmode.UNORDERED ) {
                sb.append( "<div class='list'><ul>\n" );
            }
            this.listMode = listMode;
        }
    }

    protected void closeList() {
        if ( this.listMode == Listmode.ORDERED ) {
            sb.append( "</ol></div>\n" );
        } else if ( this.listMode == Listmode.UNORDERED ) {
            sb.append( "</ul></div>\n" );
        }
        this.listMode = Listmode.NONE;
    }
}