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

package arc.resource.calculator.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import arc.resource.calculator.ChangeLog;
import arc.resource.calculator.FirstUseActivity;
import arc.resource.calculator.R;
import arc.resource.calculator.SettingsActivity;
import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.DialogUtil;
import arc.resource.calculator.util.FeedbackUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MainViewModel viewModel;

    //  loading views
    private ContentLoadingProgressBar progressBar;

    //  content views
    private BottomNavigationView bottomNavigationView;
    private View fragment;

    private AdUtil adUtil;

    // Purchase flow -> disable menu option to disable ads
    // CreateOptionsMenu -> disable menu option to disable ads if purchased

    // TODO: 5/27/2017 Error popup to have BobOnTheCob image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();

        setupViewModel();
        observeViewModel();

        setupNavigation();
        setupAds();
    }

    private void setViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragment = findViewById(R.id.navHostContainer);
        progressBar = findViewById(R.id.loadingProgressBar);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.setup(this);
    }

    private void observeViewModel() {
        viewModel.getLoadingEvent().observe(this, this::handleLoadingEvent);
        viewModel.getSnackBarMessageEvent().observe(this, this::showSnackBar);

        viewModel.getGameEntityListLiveData().observe(this, this::handleGameEntityListLiveData);
        viewModel.getGameEntityLiveData().observe(this, this::handleGameEntityLiveData);
    }

    // TODO: 6/13/2020 How to change navigation panes on demand, save position from preiouvs use
    private void setupNavigation() {
        NavController navController = Navigation.findNavController(this, R.id.navHostContainer);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    private void setupAds() {
        adUtil = new AdUtil(this, R.id.content_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adUtil.resume();
    }

    @Override
    protected void onPause() {
        adUtil.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        adUtil.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // set up menu to enable/disable remove ads button (toggled)
        menu.findItem(R.id.action_remove_ads).setEnabled(!adUtil.isRemovingAds());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
//                // TODO: 11/23/2020 show filter popup
//                FilterDialog fragment = new FilterDialog(this);
//                fragment.show(getSupportFragmentManager(), FilterDialog.TAG);
                break;

            case R.id.action_settings:
                startActivityForResult(new Intent(this, SettingsActivity.class),
                        SettingsActivity.REQUEST_CODE);
                return true;

            case R.id.action_show_changelog:
                ChangeLog changeLog = new ChangeLog(this);
                changeLog.getFullLogDialog().show();
                break;

            case R.id.action_show_tutorial:
                startActivity(new Intent(this, FirstUseActivity.class));
                break;

            case R.id.action_about:
                DialogUtil.About(MainActivity.this).show();
                break;

            case R.id.action_feedback:
                FeedbackUtil.composeFeedbackEmail(this);
                break;

            case R.id.action_feedback_bug_report:
                FeedbackUtil.composeBugReportEmail(this);
                break;

            case R.id.action_remove_ads:
                adUtil.beginPurchase();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleGameEntityLiveData(GameEntity gameEntity) {
        viewModel.setupComplete();
    }

    private void handleGameEntityListLiveData(List<GameEntity> gameEntityList) {
        Log.d(TAG, "handleGameEntityListLiveData: " + gameEntityList.size());
        if (gameEntityList.size() > 1) {
            //  display alert dialog with game list
        } else {
            GameEntity gameEntity = gameEntityList.get(0);
            Log.d(TAG, "handleGameEntityListLiveData: " + gameEntity.toString());
            viewModel.saveGameEntity(gameEntity);
            viewModel.fetchGameEntity(gameEntity.getUuid());
        }
    }

    private void handleLoadingEvent(boolean isLoading) {
        if (isLoading) {
            showLoading();
        } else {
            showLoaded();
        }
    }

    private void showLoading() {
        progressBar.show();
    }

    private void showLoaded() {
        progressBar.hide();
    }

    private void showSnackBar(String text) {
        Snackbar.make(findViewById(R.id.content_main), text, Snackbar.LENGTH_SHORT).show();
    }
}