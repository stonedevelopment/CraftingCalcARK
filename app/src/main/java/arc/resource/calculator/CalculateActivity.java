package arc.resource.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import arc.resource.calculator.views.switchers.CalculateSwitcher;

public class CalculateActivity extends AppCompatActivity {

    CalculateSwitcher mCalculateSwitcher;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_calculate );

        mCalculateSwitcher = ( CalculateSwitcher ) findViewById( R.id.switcher_calculate );
        mCalculateSwitcher.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCalculateSwitcher.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mCalculateSwitcher.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCalculateSwitcher.onDestroy();
    }
}
