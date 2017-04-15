package arc.resource.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import arc.resource.calculator.views.InventoryRecyclerView;

public class ResourceActivity extends AppCompatActivity {
    private static final String TAG = ResourceActivity.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_resource );

        InventoryRecyclerView recyclerView = ( InventoryRecyclerView ) findViewById( R.id.crafting_queue_resource_list );
        registerForContextMenu( recyclerView );
    }
}