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

import arc.resource.calculator.adapters.ShowcaseResourceListAdapter;
import arc.resource.calculator.model.Showcase;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.Helper;

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
public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private long id;
    private Showcase mShowcase;

    private ShowcaseResourceListAdapter resourceListAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );

//        Toolbar toolbar = ( Toolbar ) findViewById( R.id.toolbar );
//        setSupportActionBar( toolbar );
//
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            id = extras.getLong( Helper.DETAIL_ID );
        }

        RecyclerView.LayoutManager layoutManager_ResourceList =
                new LinearLayoutManager( this );

        ImageView imageView = ( ImageView ) findViewById( R.id.engram_detail_imageView );
        TextView nameText = ( TextView ) findViewById( R.id.engram_detail_nameText );
        TextView descriptionText = ( TextView ) findViewById( R.id.engram_detail_descriptionText );
        TextView categoryText = ( TextView ) findViewById( R.id.engram_detail_categoryText );

//        TextView craftedInText = ( TextView ) findViewById( R.id.engram_detail_craftedInText );
//        TextView levelText = ( TextView ) findViewById( R.id.engram_detail_levelText );

        Button decreaseButtonBy10 = ( Button ) findViewById( R.id.decreaseButtonBy10 );
        Button decreaseButton = ( Button ) findViewById( R.id.decreaseButton );
        final TextView quantityEditText = ( TextView ) findViewById( R.id.quantityEditText );
        Button increaseButton = ( Button ) findViewById( R.id.increaseButton );
        Button increaseButtonBy10 = ( Button ) findViewById( R.id.increaseButtonBy10 );

        RecyclerView resourceList = ( RecyclerView ) findViewById( R.id.resourceList );

        Button saveButton = ( Button ) findViewById( R.id.saveButton );
        Button removeButton = ( Button ) findViewById( R.id.removeButton );

        assert removeButton != null;
        assert saveButton != null;
        assert imageView != null;
        assert nameText != null;
        assert descriptionText != null;
        assert categoryText != null;
        assert quantityEditText != null;
        assert resourceList != null;
        assert increaseButton != null;
        assert decreaseButton != null;
        assert increaseButtonBy10 != null;
        assert decreaseButtonBy10 != null;

        mShowcase = new Showcase( this, id );
        if ( mShowcase.getQuantity() <= Helper.MIN ) {
            mShowcase.setQuantity( Helper.MIN + 1 );

            removeButton.setEnabled( false );
            saveButton.setText( "Add to Queue" );
        } else {
            saveButton.setText( "Update Queue" );
        }

        imageView.setImageResource( getResources().getIdentifier( mShowcase.getDrawable(), "drawable", getPackageName() ) );
        nameText.setText( mShowcase.getName() );
        categoryText.setText( mShowcase.getCategoryHierarchy() );

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
                    mShowcase.getStationName()
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

        descriptionText.setText( mShowcase.getDescription() + " " + craftedIn + level );

        resourceListAdapter = new ShowcaseResourceListAdapter( this, mShowcase.getQuantifiableComposition() );

        quantityEditText.setText( mShowcase.getQuantityText() );

        decreaseButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                mShowcase.decreaseQuantity();

                quantityEditText.setText( mShowcase.getQuantityText() );

                resourceListAdapter.setResources( mShowcase.getQuantifiableComposition() );
                resourceListAdapter.Refresh();
            }
        } );

        increaseButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                mShowcase.increaseQuantity();

                quantityEditText.setText( mShowcase.getQuantityText() );

                resourceListAdapter.setResources( mShowcase.getQuantifiableComposition() );
                resourceListAdapter.Refresh();
            }
        } );

        decreaseButtonBy10.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                mShowcase.decreaseQuantityBy10();

                quantityEditText.setText( mShowcase.getQuantityText() );

                resourceListAdapter.setResources( mShowcase.getQuantifiableComposition() );
                resourceListAdapter.Refresh();
            }
        } );

        increaseButtonBy10.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                mShowcase.increaseQuantityBy10();

                quantityEditText.setText( mShowcase.getQuantityText() );

                resourceListAdapter.setResources( mShowcase.getQuantifiableComposition() );
                resourceListAdapter.Refresh();
            }
        } );

        removeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                FinishActivityWithResult( Helper.DETAIL_REMOVE, true );
            }
        } );

        saveButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                FinishActivityWithResult( Helper.DETAIL_SAVE, mShowcase.getQuantityWithYield() );
            }
        } );

        resourceList.setLayoutManager( layoutManager_ResourceList );
        resourceList.setAdapter( resourceListAdapter );

        AdUtil.loadAdView( this );
    }

    private void FinishActivityWithResult( String resultCode, boolean result ) {
        Intent returnIntent = getIntent();

        returnIntent.putExtra( Helper.RESULT_CODE_DETAIL_ACTIVITY, resultCode );
        returnIntent.putExtra( resultCode, result );

        setResult( RESULT_OK, returnIntent );

        finish();
    }

    private void FinishActivityWithResult( String resultCode, int result ) {
        Intent returnIntent = getIntent();

        returnIntent.putExtra( Helper.RESULT_CODE_DETAIL_ACTIVITY, resultCode );
        returnIntent.putExtra( Helper.DETAIL_QUANTITY, result );

        setResult( RESULT_OK, returnIntent );

        finish();
    }
}
