/*
 * Copyright (c) 2020 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */
package arc.resource.calculator.ui.load;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import arc.resource.calculator.FirstUseActivity;
import arc.resource.calculator.R;
import arc.resource.calculator.ui.main.MainActivity;
import arc.resource.calculator.util.PrefsUtil;

public class LoadScreenActivity extends AppCompatActivity {
    private static final String TAG = LoadScreenActivity.class.getSimpleName();
    private static final long DELAY_MILLIS = 1500;
    private LoadScreenViewModel mViewModel;
    private TextView mTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        setupViews();
        setupViewModel();
    }

    private void setupViews() {
        ImageView imageView = findViewById(R.id.content_init_image_view);

        final String imagePath = "file:///android_asset/splash.png";
        Picasso.with(this)
                .load(imagePath)
                .error(R.drawable.placeholder_empty)
                .placeholder(R.drawable.placeholder_empty)
                .into(imageView);

        mTextView = findViewById(R.id.content_init_status_text);
        mProgressBar = findViewById(R.id.content_init_progress_bar);
    }

    private void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(LoadScreenViewModel.class);
        mViewModel.getStatusMessageEvent().observe(this, statusMessage -> mTextView.setText(statusMessage));
        mViewModel.getProgressEvent().observe(this, progress -> mProgressBar.setProgress(progress));
        mViewModel.getProgressTotalEvent().observe(this, total -> mProgressBar.setMax(total));
        mViewModel.getLoadScreenStateEvent().observe(this, this::handleLoadScreenState);
    }

    /**
     * Handles the current state of load screen events
     * TODO: 5/31/2020  code smell: this only handles the final load screen state
     *
     * @param state current state to handle
     */
    private void handleLoadScreenState(LoadScreenState state) {
        if (state == LoadScreenState.Finalize) {
            finishActivity();
        }
    }

    /**
     * Result from FirstUseActivity, saves to prefs if user finishes tutorial
     * TODO: 5/31/2020  update FirstUseActivity?
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FirstUseActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                preferences.edit().putBoolean(PrefsUtil.FirstUseKey, false).apply();
            }
        }

        startMainActivity();
    }

    private void finishWithDelay(Runnable runnable) {
        new Handler().postDelayed(runnable, DELAY_MILLIS);
    }

    private void finishActivity() {
        finishWithDelay(() -> {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            boolean isFirst = prefs.getBoolean(PrefsUtil.FirstUseKey, PrefsUtil.FirstUseDefaultValue);
//                boolean isFirst = true;

            if (isFirst) startFirstUseActivity();
            else startMainActivity();
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        finish();
    }

    private void startFirstUseActivity() {
        Intent intent = new Intent(getApplicationContext(), FirstUseActivity.class);
        startActivityForResult(intent, FirstUseActivity.REQUEST_CODE);
    }
}