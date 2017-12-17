package arc.resource.calculator;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_search );
        handleIntent( getIntent() );
    }

    @Override
    protected void onNewIntent( Intent intent ) {
        handleIntent( intent );
    }

    private void handleIntent( Intent intent ) {
        if ( Intent.ACTION_SEARCH.equals( intent.getAction() ) ) {
            String query = intent.getStringExtra( SearchManager.QUERY );
            Log.d( "SEARCH", query );
        }
    }
}
