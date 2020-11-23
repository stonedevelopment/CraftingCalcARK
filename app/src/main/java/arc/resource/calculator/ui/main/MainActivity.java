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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import arc.resource.calculator.ChangeLog;
import arc.resource.calculator.DetailActivity;
import arc.resource.calculator.FirstUseActivity;
import arc.resource.calculator.R;
import arc.resource.calculator.SettingsActivity;
import arc.resource.calculator.ui.filter.FilterSettingsFragment;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.DialogUtil;
import arc.resource.calculator.util.FeedbackUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MainViewModel viewModel;
    private AdUtil adUtil;

    //  loading views
    private ContentLoadingProgressBar progressBar;

    //  content views
    private BottomNavigationView mBottomNav;
    private View mFragment;

    // Purchase flow -> disable menu option to disable ads
    // CreateOptionsMenu -> disable menu option to disable ads if purchased

    // TODO: 5/27/2017 Error popup to have BobOnTheCob image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();

        setupViewModel();

        setupAds();

        showChangeLog();
    }

    private void setupViews() {
        mBottomNav = findViewById(R.id.bottomNavigationView);
        mFragment = findViewById(R.id.navHostContainer);
        progressBar = findViewById(R.id.loadingProgressBar);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getGameEntityEvent().observe(this, gameEntity -> {
            viewModel.setIsLoading(false);
        });
        viewModel.getLoadingEvent().observe(this, isLoading -> {
            if (isLoading) {
                hideViews();
            } else {
                showViews();
            }
        });
        viewModel.getStartActivityForResultTrigger().observe(this, intent -> {
            int requestCode = intent.getIntExtra(DetailActivity.REQUEST_EXTRA_CODE, -1);
            startActivityForResult(intent, requestCode);
        });
        viewModel.getSnackBarMessageEvent().observe(this, this::showSnackBar);
    }

    // TODO: 6/13/2020 How to change navigation panes on demand, save position from preiouvs use
    private void setupNavigation() {
        NavController navController = Navigation.findNavController(this, R.id.navHostContainer);
        NavigationUI.setupWithNavController(mBottomNav, navController);
    }

    private void setupAds() {
        adUtil = new AdUtil(this, R.id.content_main);
    }

    private void showViews() {
        progressBar.hide();

        mFragment.setVisibility(View.VISIBLE);
        mBottomNav.setVisibility(View.VISIBLE);

        setupNavigation();
    }

    private void hideViews() {
        progressBar.show();

        mFragment.setVisibility(View.GONE);
        mBottomNav.setVisibility(View.GONE);
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
        final boolean show = super.onPrepareOptionsMenu(menu);

        // set up menu to enable/disable remove ads button (toggled)
        menu.findItem(R.id.action_remove_ads).setEnabled(!adUtil.isRemovingAds());

        return show;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                // TODO: 11/23/2020 show filter popup
                FilterSettingsFragment fragment = new FilterSettingsFragment();
                fragment.show(getSupportFragmentManager(), FilterSettingsFragment.TAG);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!adUtil.onActivityResult(requestCode, resultCode, data)) {
        }
    }

    // TODO: 6/13/2020 Change changelog
    private void showChangeLog() {
//        try {
//            ChangeLog changeLog = new ChangeLog(this);
//
//            if (changeLog.firstRun()) {
//                changeLog.getLogDialog().show();
//            }
//        } catch (Exception e) {
//            // do nothing
//        }
    }

    private void showSnackBar(String text) {
        Snackbar.make(findViewById(R.id.content_main), text, Snackbar.LENGTH_SHORT).show();
    }
}