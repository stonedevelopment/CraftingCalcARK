package arc.resource.calculator;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import arc.resource.calculator.adapters.ShowcaseResourceListAdapter;
import arc.resource.calculator.listeners.SendErrorReportListener;
import arc.resource.calculator.model.Showcase;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.ListenerUtil;
import arc.resource.calculator.util.Util;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: A:RC, a resource calculator for ARK:Survival Evolved
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class DetailActivity extends AppCompatActivity
        implements SendErrorReportListener {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private Showcase mShowcase;

    private ShowcaseResourceListAdapter mShowcaseResourceListAdapter;

    private AdUtil mAdUtil;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        try {
            long _id = getIntent().getExtras().getLong( Util.DETAIL_ID );
            int quantity = getIntent().getExtras().getInt( Util.DETAIL_QUANTITY );
            mShowcase = new Showcase( this, _id );

            ImageView imageView = ( ImageView ) findViewById( R.id.engram_detail_imageView );
            String imagePath = mShowcase.getImagePath();
            InputStream stream = getAssets().open( imagePath );
            imageView.setImageBitmap( BitmapFactory.decodeStream( stream ) );

            TextView nameText = ( TextView ) findViewById( R.id.engram_detail_nameText );
            nameText.setText( mShowcase.getName() );

            Button decreaseButtonBy10 = ( Button ) findViewById( R.id.decreaseButtonBy10 );
            decreaseButtonBy10.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    mShowcase.decreaseQuantity( 10 );
                    mShowcaseResourceListAdapter.notifyDataSetChanged();

                    UpdateQuantityText();
                }
            } );

            Button decreaseButton = ( Button ) findViewById( R.id.decreaseButton );
            decreaseButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    mShowcase.decreaseQuantity();
                    mShowcaseResourceListAdapter.notifyDataSetChanged();

                    UpdateQuantityText();
                }
            } );

            Button increaseButton = ( Button ) findViewById( R.id.increaseButton );
            increaseButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    mShowcase.increaseQuantity();
                    mShowcaseResourceListAdapter.notifyDataSetChanged();

                    UpdateQuantityText();
                }
            } );

            Button increaseButtonBy10 = ( Button ) findViewById( R.id.increaseButtonBy10 );
            increaseButtonBy10.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    mShowcase.increaseQuantity( 10 );
                    mShowcaseResourceListAdapter.notifyDataSetChanged();

                    UpdateQuantityText();
                }
            } );

            Button saveButton = ( Button ) findViewById( R.id.saveButton );
            saveButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    if ( mShowcase.getQuantity() > 0 )
                        FinishActivityWithResult( false );
                    else
                        FinishActivityWithResult( true );
                }
            } );

            Button removeButton = ( Button ) findViewById( R.id.removeButton );
            removeButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    FinishActivityWithResult( true );
                }
            } );

            if ( mShowcase.getQuantity() == 0 ) {
                mShowcase.setQuantity( 1 );

                removeButton.setEnabled( false );
                saveButton.setText( "Add to Queue" ); // TODO: 1/22/2017 Extract string resource
            } else {
                saveButton.setText( "Update Queue" ); // TODO: 1/22/2017 Extract string resource
            }

            TextView categoryText = ( TextView ) findViewById( R.id.engram_detail_categoryText );
            categoryText.setText( mShowcase.getCategoryHierarchy( getApplicationContext() ) );

            String craftedIn;
            if ( mShowcase.getStationId() == 0 ) {
                craftedIn = String.format(
                        getString( R.string.content_detail_crafted_in_no_station ),
                        mShowcase.getName()
                );
            } else {
                craftedIn = String.format(
                        getString( R.string.content_detail_crafted_in_format ),
                        mShowcase.getName(),
                        mShowcase.getStationName( getApplicationContext() )
                );
            }

            String level;
            if ( mShowcase.getRequiredLevel() == 1 ) {
                level = getString( R.string.content_detail_required_level_any_level );
            } else {
                level = String.format(
                        getString( R.string.content_detail_required_level_format ),
                        Integer.toString( mShowcase.getRequiredLevel() )
                );
            }

            TextView descriptionText = ( TextView ) findViewById( R.id.engram_detail_descriptionText );
            descriptionText.setText( mShowcase.getDescription() + "\n\n" + craftedIn + level );

            TextView quantityEditText = ( TextView ) findViewById( R.id.quantityEditText );
            quantityEditText.setText( mShowcase.getQuantityText() );

            mShowcaseResourceListAdapter = new ShowcaseResourceListAdapter( mShowcase );

            RecyclerView resourceList = ( RecyclerView ) findViewById( R.id.resourceList );
            resourceList.setLayoutManager( new LinearLayoutManager( this ) );
            resourceList.setAdapter( mShowcaseResourceListAdapter );

            mAdUtil = new AdUtil( this, R.id.content_detail );
            mAdUtil.init();
        } catch ( Exception e ) {
            ListenerUtil.getInstance().emitSendErrorReportWithAlertDialog( TAG, e );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAdUtil.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mAdUtil.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mAdUtil.destroy();
    }

    void UpdateQuantityText() {
        TextView quantityEditText = ( TextView ) findViewById( R.id.quantityEditText );
        quantityEditText.setText( mShowcase.getQuantityText() );
    }

    private void FinishActivityWithResult( boolean removeFromQueue ) {
        Intent returnIntent = getIntent();

        if ( removeFromQueue ) {
            returnIntent.putExtra( Util.DETAIL_REMOVE, true );
        } else {
            returnIntent.putExtra( Util.DETAIL_QUANTITY, mShowcase.getQuantity() );
        }

        setResult( RESULT_OK, returnIntent );

        finish();
    }

    @Override
    public void onSendErrorReport( String tag, Exception e, boolean showAlertDialog ) {
        if ( showAlertDialog )
            ExceptionUtil.SendErrorReportWithAlertDialog( DetailActivity.this, tag, e );
        else
            ExceptionUtil.SendErrorReport( DetailActivity.this, tag, e );
    }
}
