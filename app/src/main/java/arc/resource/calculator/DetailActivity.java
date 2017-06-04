package arc.resource.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import arc.resource.calculator.adapters.ShowcaseResourceListAdapter;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.model.Showcase;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.FeedbackUtil;

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

    public static final int REQUEST_CODE = 1000;

    public static final String REQUEST_ID = "ID";

    public static final String RESULT_CODE = "CODE";
    public static final String RESULT_EXTRA_NAME = "EXTRA_NAME";
    public static final String RESULT_EXTRA_ID = "EXTRA_ID";
    public static final String RESULT_EXTRA_QUANTITY = "EXTRA_QUANTITY";

    public static final int ERROR = -1;
    public static final int REMOVE = 0;
    public static final int ADD = 1;
    public static final int UPDATE = 2;

    private static final int DECREMENT = 10;
    private static final int INCREMENT = 10;

    private Showcase mShowcase;

    private ShowcaseResourceListAdapter mAdapter;

    private AdUtil mAdUtil;

    private boolean mIsInQueue;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        try {
            setContentView( R.layout.activity_detail );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );

            // // FIXME: 5/4/2017 Quick workaround for phones that sleep in DetailActivity
//            if ( savedInstanceState != null )
//                FinishActivityWithoutResult();

            long id = getIntent().getExtras().getLong( REQUEST_ID );

            setShowcase( new Showcase( getApplicationContext(), id ) );

            ImageView imageView = ( ImageView ) findViewById( R.id.imageView );
            imageView.setContentDescription( "Image of " + getShowcase().getName() );

            String imagePath = "file:///android_asset/" + getShowcase().getImagePath();
            Picasso.with( getApplicationContext() ).load( imagePath ).into( imageView );

            TextView nameText = ( TextView ) findViewById( R.id.textView_name );
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

            mIsInQueue = ( getShowcase().getQuantity() > 0 );

            if ( mIsInQueue ) {
                saveButton.setText( R.string.detail_content_save_button_update_queue );
            } else {
                removeButton.setEnabled( false );
                saveButton.setText( R.string.detail_content_save_button_add_to_queue );
            }

            TextView categoryText = ( TextView ) findViewById( R.id.textView_navigation );
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

            TextView descriptionText = ( TextView ) findViewById( R.id.textView_description );
            descriptionText.setText( getShowcase().getDescription() );

            TextView stationText = ( TextView ) findViewById( R.id.textView_crafted_in );
            stationText.setText( String.format( getString( R.string.content_detail_station_and_level_format ), craftedIn, level ) );

            TextView dlcText = ( TextView ) findViewById( R.id.textView_dlc_name );
            dlcText.setText( getShowcase().getDLCName() );

            updateQuantityText();

            setAdapter( new ShowcaseResourceListAdapter( getShowcase() ) );

            RecyclerView recyclerView = ( RecyclerView ) findViewById( R.id.resourceList );
            recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
            recyclerView.setAdapter( getAdapter() );

            mAdUtil = new AdUtil( this, R.id.content_detail );
        } catch ( Exception e ) {
            FinishActivityWithError( e );
        }

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu_detail, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch ( item.getItemId() ) {
            case android.R.id.home:
                FinishActivityWithoutResult();
                return true;

            case R.id.action_feedback_correction:
                FeedbackUtil.composeCorrectionEmail( DetailActivity.this, getShowcase().getName() );
                break;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onBackPressed() {
        FinishActivityWithoutResult();
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

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        outState.putLong( DatabaseContract.EngramEntry._ID, getShowcase().getId() );
        outState.putInt( DatabaseContract.QueueEntry.COLUMN_QUANTITY, getShowcase().getQuantity() );

        super.onSaveInstanceState( outState );
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
        FinishActivityWithResult( REMOVE );
    }

    public void SaveAndFinish( View view ) {
        if ( mIsInQueue )
            FinishActivityWithResult( UPDATE );
        else
            FinishActivityWithResult( ADD );
    }

    private void FinishActivityWithError( Exception e ) {
        Intent returnIntent = getIntent();

        returnIntent.putExtra( RESULT_CODE, ERROR );
        returnIntent.putExtra( RESULT_EXTRA_NAME, e );

        setResult( RESULT_CANCELED, returnIntent );

        finish();
    }

    private void FinishActivityWithResult( int resultCode ) {
        Intent returnIntent = getIntent();

        returnIntent.putExtra( RESULT_CODE, resultCode );
        returnIntent.putExtra( RESULT_EXTRA_NAME, getShowcase().getName() );
        returnIntent.putExtra( RESULT_EXTRA_ID, getShowcase().getId() );

        switch ( resultCode ) {
            case REMOVE:
                break;

            case ADD:
            case UPDATE:
                int quantity = getShowcase().getQuantity();

                if ( quantity == 0 && !mIsInQueue )
                    quantity++;

                returnIntent.putExtra( RESULT_EXTRA_QUANTITY, quantity );
                break;
        }

        setResult( RESULT_OK, returnIntent );

        finish();
    }

    private void FinishActivityWithoutResult() {
        setResult( RESULT_CANCELED, getIntent() );
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
