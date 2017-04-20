package arc.resource.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import arc.resource.calculator.adapters.ShowcaseResourceListAdapter;
import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.model.Showcase;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.ExceptionUtil;
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
        implements ExceptionObserver.Listener {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private static final int DECREMENT = 10;
    private static final int INCREMENT = 10;

    private Showcase mShowcase;

    private ShowcaseResourceListAdapter mAdapter;

    private AdUtil mAdUtil;

    // TODO: 4/7/2017 remove from queue non responder

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        try {
            long _id = getIntent().getExtras().getLong( Util.DETAIL_ID );
            setShowcase( new Showcase( getApplicationContext(), _id ) );

            ImageView imageView = ( ImageView ) findViewById( R.id.engram_detail_imageView );
            imageView.setContentDescription( "Image of " + getShowcase().getName() );

            String imagePath = "file:///android_asset/" + getShowcase().getImagePath();
            Picasso.with( getApplicationContext() ).load( imagePath ).into( imageView );

            TextView nameText = ( TextView ) findViewById( R.id.engram_detail_nameText );
            nameText.setText( getShowcase().getName() );

            Button decreaseButtonBy10 = ( Button ) findViewById( R.id.decreaseButtonBy10 );
            decreaseButtonBy10.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    decreaseQuantity( DECREMENT );
                }
            } );

            Button decreaseButton = ( Button ) findViewById( R.id.decreaseButton );
            decreaseButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    decreaseQuantity();
                }
            } );

            Button increaseButton = ( Button ) findViewById( R.id.increaseButton );
            increaseButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    increaseQuantity();
                }
            } );

            Button increaseButtonBy10 = ( Button ) findViewById( R.id.increaseButtonBy10 );
            increaseButtonBy10.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    increaseQuantity( INCREMENT );
                }
            } );

            Button saveButton = ( Button ) findViewById( R.id.saveButton );
            Button removeButton = ( Button ) findViewById( R.id.removeButton );

            if ( getShowcase().getQuantity() == 0 ) {
                getShowcase().increaseQuantity();

                removeButton.setEnabled( false );
                saveButton.setText( R.string.detail_content_save_button_add_to_queue );
            } else {
                saveButton.setText( R.string.detail_content_save_button_update_queue );
            }

            TextView categoryText = ( TextView ) findViewById( R.id.engram_detail_categoryText );
            categoryText.setText( getShowcase().getCategoryHierarchy( getApplicationContext() ) );

            String craftedIn;
            if ( getShowcase().getShowcaseEntry().getStations().size() == 1
                    && getShowcase().getShowcaseEntry().getStations().keyAt( 0 ) == 0 )
                craftedIn = getString( R.string.content_detail_crafted_in_no_station );
            else
                craftedIn = String.format( getString( R.string.content_detail_crafted_in_format ),
                        getShowcase().getStationNameArrayAsText() );

            String level;
            if ( getShowcase().getRequiredLevel() == 1 )
                level = getString( R.string.content_detail_required_level_any_level );
            else
                level = String.format( getString( R.string.content_detail_required_level_format ),
                        getShowcase().getRequiredLevel() );

            TextView descriptionText = ( TextView ) findViewById( R.id.engram_detail_descriptionText );
            descriptionText.setText( getShowcase().getDescription() );

            TextView stationText = ( TextView ) findViewById( R.id.engram_detail_stationText );
            stationText.setText( String.format( getString( R.string.content_detail_station_and_level_format ),
                    getShowcase().getName(), craftedIn, level ) );

            TextView dlcText = ( TextView ) findViewById( R.id.engram_detail_dlcText );
            dlcText.setText( getShowcase().getDLCName() );

            updateQuantityText();

            setAdapter( new ShowcaseResourceListAdapter( getShowcase() ) );

            RecyclerView recyclerView = ( RecyclerView ) findViewById( R.id.resourceList );
            recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
            recyclerView.setAdapter( getAdapter() );

            mAdUtil = new AdUtil( this, R.id.content_detail );
            mAdUtil.init();
        } catch ( Exception e ) {
            ExceptionObserver.getInstance().notifyFatalExceptionCaught( TAG, e );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ExceptionObserver.getInstance().registerListener( this );

        mAdUtil.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ExceptionObserver.getInstance().unregisterListener( this );

        mAdUtil.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExceptionObserver.getInstance().unregisterListener( this );

        mAdUtil.destroy();
    }

    private void setShowcase( Showcase showcase ) {
        this.mShowcase = showcase;
    }

    private Showcase getShowcase() {
        return mShowcase;
    }

    private void setAdapter( ShowcaseResourceListAdapter adapter ) {
        this.mAdapter = adapter;
    }

    private ShowcaseResourceListAdapter getAdapter() {
        return mAdapter;
    }

    public void increaseQuantity() {
        getShowcase().increaseQuantity();
        notifyAndUpdate();
    }

    public void increaseQuantity( int increment ) {
        getShowcase().increaseQuantity( increment );
        notifyAndUpdate();
    }

    public void decreaseQuantity() {
        getShowcase().decreaseQuantity();
        notifyAndUpdate();
    }

    public void decreaseQuantity( int decrement ) {
        getShowcase().decreaseQuantity( decrement );
        notifyAndUpdate();
    }

    private void notifyAndUpdate() {
        getShowcase().updateQuantifiableComposition();
        notifyDataSetChanged();
        updateQuantityText();
    }

    private void notifyDataSetChanged() {
        getAdapter().notifyDataSetChanged();
    }

    private void updateQuantityText() {
        TextView quantityEditText = ( TextView ) findViewById( R.id.quantityEditText );
        quantityEditText.setText( getShowcase().getQuantityText() );
    }

    public void RemoveAndFinish( View view ) {
        getShowcase().getShowcaseEntry().getCraftable().resetQuantity();

        FinishActivityWithResult();
    }

    public void SaveAndFinish( View view ) {
        FinishActivityWithResult();
    }

    private void FinishActivityWithResult() {
        Intent returnIntent = getIntent();

        returnIntent.putExtra( Util.DETAIL_QUANTITY, getShowcase().getQuantity() );

        setResult( RESULT_OK, returnIntent );

        finish();
    }

    @Override
    public void onException( String tag, Exception e ) {
        ExceptionUtil.SendErrorReport( tag, e );
    }

    @Override
    public void onFatalException( String tag, Exception e ) {
        ExceptionUtil.SendErrorReportWithAlertDialog( DetailActivity.this, tag, e );
    }
}
