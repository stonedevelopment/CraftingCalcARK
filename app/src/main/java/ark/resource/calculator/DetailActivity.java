package ark.resource.calculator;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import ark.resource.calculator.adapters.ResourceListAdapter;
import ark.resource.calculator.helpers.Helper;
import ark.resource.calculator.model.Showcase;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: ARK:Resource Calculator
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class DetailActivity extends AppCompatActivity {
    private static final String LOGTAG = "DETAIL";

    private static final int MIN = 0;
    private static final int MAX = 100;

    private long id;
    private Showcase showcase;

    private ResourceListAdapter resourceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.in_game_background, null)));

        RecyclerView.LayoutManager layoutManager_ResourceList =
                new LinearLayoutManager(this);

        ImageView imageView = (ImageView) findViewById(R.id.engram_detail_imageView);
        TextView nameText = (TextView) findViewById(R.id.engram_detail_nameText);
        TextView descriptionText = (TextView) findViewById(R.id.engram_detail_descriptionText);
        TextView categoryText = (TextView) findViewById(R.id.engram_detail_categoryText);
        NumberPicker quantityNumberPicker = (NumberPicker) findViewById(R.id.engram_detail_quantityNumberPicker);
        RecyclerView resourceList = (RecyclerView) findViewById(R.id.engram_detail_resources);
        Button saveButton = (Button) findViewById(R.id.engram_detail_save_button);
        Button removeButton = (Button) findViewById(R.id.engram_detail_remove_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong(Helper.DETAIL_ID);
        }

        assert removeButton != null;
        assert saveButton != null;
        assert imageView != null;
        assert nameText != null;
        assert descriptionText != null;
        assert categoryText != null;
        assert quantityNumberPicker != null;
        assert resourceList != null;

        showcase = new Showcase(this, id);
        if (showcase.getQuantity() <= MIN) {
            showcase.setQuantity(MIN + 1);

            removeButton.setEnabled(false);
            saveButton.setText("Add to Queue");
        } else {
            saveButton.setText("Update Queue");
        }

        imageView.setImageResource(showcase.getImageId());
        nameText.setText(showcase.getName());
        descriptionText.setText(showcase.getDescription());
        categoryText.setText(showcase.getCategoryDescription());

        resourceListAdapter = new ResourceListAdapter(this, showcase.getComposition());

        quantityNumberPicker.setMinValue(MIN);
        quantityNumberPicker.setMaxValue(MAX);
        quantityNumberPicker.setValue(showcase.getQuantity());
        quantityNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                showcase.setQuantity(newVal);
                resourceListAdapter.setResources(showcase.getComposition());
                resourceListAdapter.Refresh();
            }
        });

        resourceList.setLayoutManager(layoutManager_ResourceList);
        resourceList.setAdapter(resourceListAdapter);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishActivityWithResult(Helper.DETAIL_REMOVE, true);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishActivityWithResult(Helper.DETAIL_SAVE, showcase.getQuantity());
            }
        });
    }

    private void FinishActivityWithResult(String resultCode, boolean result) {
        Intent returnIntent = getIntent();

        returnIntent.putExtra(Helper.DETAIL_RESULT_CODE, resultCode);
        returnIntent.putExtra(resultCode, result);

        setResult(RESULT_OK, returnIntent);

        finish();
    }

    private void FinishActivityWithResult(String resultCode, int result) {
        Intent returnIntent = getIntent();

        returnIntent.putExtra(Helper.DETAIL_RESULT_CODE, resultCode);
        returnIntent.putExtra(Helper.DETAIL_QUANTITY, result);

        setResult(RESULT_OK, returnIntent);

        finish();
    }
}
