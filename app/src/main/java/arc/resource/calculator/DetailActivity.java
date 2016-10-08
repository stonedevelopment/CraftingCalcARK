package arc.resource.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import arc.resource.calculator.adapters.ShowcaseResourceListAdapter;
import arc.resource.calculator.helpers.Helper;
import arc.resource.calculator.model.Showcase;

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
    private Showcase showcase;

    private ShowcaseResourceListAdapter resourceListAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );

        RecyclerView.LayoutManager layoutManager_ResourceList =
                new LinearLayoutManager( this );

        ImageView imageView = ( ImageView ) findViewById( R.id.engram_detail_imageView );
        TextView nameText = ( TextView ) findViewById( R.id.engram_detail_nameText );
        TextView descriptionText = ( TextView ) findViewById( R.id.engram_detail_descriptionText );
        TextView categoryText = ( TextView ) findViewById( R.id.engram_detail_categoryText );

        Button increaseButton = ( Button ) findViewById( R.id.increaseButton );
        final EditText quantityEditText = ( EditText ) findViewById( R.id.quantityEditText );
        Button decreaseButton = ( Button ) findViewById( R.id.decreaseButton );

        RecyclerView resourceList = ( RecyclerView ) findViewById( R.id.resourceList );

        Button saveButton = ( Button ) findViewById( R.id.saveButton );
        Button removeButton = ( Button ) findViewById( R.id.removeButton );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            id = extras.getLong( Helper.DETAIL_ID );
        }

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

        showcase = new Showcase( this, id );
        if ( showcase.getQuantity() <= Helper.MIN ) {
            showcase.setQuantity( Helper.MIN + 1 );

            removeButton.setEnabled( false );
            saveButton.setText( "Add to Queue" );
        } else {
            saveButton.setText( "Update Queue" );
        }

        imageView.setImageResource( getResources().getIdentifier( showcase.getDrawable(), "drawable", getPackageName() ) );
        nameText.setText( showcase.getName() );
        descriptionText.setText( showcase.getDescription() );
        categoryText.setText( showcase.getCategoryHierarchy() );

        resourceListAdapter = new ShowcaseResourceListAdapter( this, showcase.getQuantifiableComposition() );

        quantityEditText.setText( showcase.getQuantityText() );
//        quantityEditText.addTextChangedListener( new TextWatcher() {
//            @Override
//            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {
//
//            }
//
//            @Override
//            public void onTextChanged( CharSequence s, int start, int before, int count ) {
//                if ( s.length() > 0 ) {
//                    int quantityFromText = Integer.parseInt( s.toString() );
//
//                    int quantityByYield = quantityFromText / showcase.getYield();
//
//                    if ( quantityByYield > Helper.MAX ) {
//                        quantityByYield = Helper.MAX / showcase.getYield();
//                        quantityEditText.setText( String.valueOf( quantityByYield ) );
//                    }
//
//                    quantityEditText.setSelection( quantityEditText.length() );
//
//                    showcase.setQuantity( quantityByYield / showcase.getYield() );
//
//                    resourceListAdapter.setResources( showcase.getQuantifiableComposition() );
//                    resourceListAdapter.Refresh();
//                }
//            }
//
//            @Override
//            public void afterTextChanged( Editable s ) {
//
//            }
//        } );

        decreaseButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                showcase.decreaseQuantity();

                quantityEditText.setText( showcase.getQuantityText() );
                quantityEditText.setSelection( quantityEditText.length() );

                resourceListAdapter.setResources( showcase.getQuantifiableComposition() );
                resourceListAdapter.Refresh();
            }
        } );

        increaseButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                showcase.increaseQuantity();

                quantityEditText.setText( showcase.getQuantityText() );
                quantityEditText.setSelection( quantityEditText.length() );

                resourceListAdapter.setResources( showcase.getQuantifiableComposition() );
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
                FinishActivityWithResult( Helper.DETAIL_SAVE, showcase.getQuantityWithYield() );
            }
        } );

        resourceList.setLayoutManager( layoutManager_ResourceList );
        resourceList.setAdapter( resourceListAdapter );
    }

    private void FinishActivityWithResult( String resultCode, boolean result ) {
        Intent returnIntent = getIntent();

        returnIntent.putExtra( Helper.DETAIL_RESULT_CODE, resultCode );
        returnIntent.putExtra( resultCode, result );

        setResult( RESULT_OK, returnIntent );

        finish();
    }

    private void FinishActivityWithResult( String resultCode, int result ) {
        Intent returnIntent = getIntent();

        returnIntent.putExtra( Helper.DETAIL_RESULT_CODE, resultCode );
        returnIntent.putExtra( Helper.DETAIL_QUANTITY, result );

        setResult( RESULT_OK, returnIntent );

        finish();
    }
}
