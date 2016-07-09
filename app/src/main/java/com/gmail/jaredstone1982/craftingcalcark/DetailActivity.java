package com.gmail.jaredstone1982.craftingcalcark;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.gmail.jaredstone1982.craftingcalcark.adapters.ResourceListAdapter;
import com.gmail.jaredstone1982.craftingcalcark.helpers.Helper;
import com.gmail.jaredstone1982.craftingcalcark.model.Showcase;

/**
 * // TODO: 7/8/2016 Allow for removal of engram from crafting queue
 */
public class DetailActivity extends AppCompatActivity {
    private Showcase showcase;
    private long id;
    private ResourceListAdapter resourceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        RecyclerView.LayoutManager layoutManager_ResourceList =
                new LinearLayoutManager(this);

        ImageView imageView = (ImageView) findViewById(R.id.engram_detail_imageView);
        TextView nameText = (TextView) findViewById(R.id.engram_detail_nameText);
        TextView descriptionText = (TextView) findViewById(R.id.engram_detail_descriptionText);
        NumberPicker quantityNumberPicker = (NumberPicker) findViewById(R.id.engram_detail_quantityNumberPicker);
        RecyclerView resourceList = (RecyclerView) findViewById(R.id.engram_detail_resources);
        Button saveButton = (Button) findViewById(R.id.engram_detail_save_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong(Helper.DETAIL_ID);
        }

        showcase = new Showcase(this, id);
        if (showcase.getQuantity() == 0) {
            showcase.setQuantity(1);
        }
        if (imageView != null &&
                nameText != null &&
                descriptionText != null &&
                quantityNumberPicker != null &&
                saveButton != null &&
                resourceList != null) {
            imageView.setImageResource(showcase.getImageId());
            nameText.setText(showcase.getName());
            descriptionText.setText(showcase.getDescription());

            resourceListAdapter = new ResourceListAdapter(showcase.getComposition());

            quantityNumberPicker.setMinValue(1);
            quantityNumberPicker.setMaxValue(100);
            quantityNumberPicker.setValue(showcase.getQuantity());
            quantityNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    showcase.setQuantity(newVal);
                    // TODO: 7/8/2016 The use of Resources and Composition can be confusing, they are essentially the same thing.
                    resourceListAdapter.setResources(showcase.getComposition());
                    resourceListAdapter.Refresh();
                }
            });

            resourceList.setLayoutManager(layoutManager_ResourceList);
            resourceList.setAdapter(resourceListAdapter);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CloseActivity();
                }
            });
        }
    }

    public void CloseActivity() {
//        Queue queue = new Queue(engram.getId(), engram.getImageId(), engram.getQuantity());
//        queueDataSource.Update(queue);
//        Intent intent = getIntent();
        getIntent().putExtra(Helper.DETAIL_QUANTITY, showcase.getQuantity());
        setResult(RESULT_OK, getIntent());
        finish();
    }
}