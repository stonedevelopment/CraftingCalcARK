/*
 * Copyright (c) 2019 Jared Stone
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

package arc.resource.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import arc.resource.calculator.adapters.ViewPagerAdapter;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.listeners.PrefsObserver;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.DialogUtil;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.FeedbackUtil;

import static arc.resource.calculator.DetailActivity.ADD;
import static arc.resource.calculator.DetailActivity.ERROR;
import static arc.resource.calculator.DetailActivity.REMOVE;
import static arc.resource.calculator.DetailActivity.RESULT_CODE;
import static arc.resource.calculator.DetailActivity.RESULT_EXTRA_NAME;
import static arc.resource.calculator.DetailActivity.UPDATE;

public class MainActivity extends AppCompatActivity
        implements ExceptionObservable.Observer {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String INTENT_KEY_DID_UPDATE = "DID_UPDATE";

    private MainViewModel mViewModel;

    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private AdUtil mAdUtil;

    // Purchase flow -> disable menu option to disable ads
    // CreateOptionsMenu -> disable menu option to disable ads if purchased

    // TODO: 5/27/2017 Error popup to have BobOnTheCob image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewModel();

        registerListeners();

        setupViewPager();

        setupBottomNavigation();

        setupAds();

        showChangeLog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerListeners();
        mAdUtil.resume();
    }

    @Override
    protected void onPause() {
        mAdUtil.pause();
        unregisterListeners();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAdUtil.destroy();
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
        menu.findItem(R.id.action_remove_ads).setEnabled(!mAdUtil.isRemovingAds());

        return show;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
                mAdUtil.beginPurchase();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!mAdUtil.onActivityResult(requestCode, resultCode, data)) {
            if (data != null) {
                Bundle extras = data.getExtras();

                switch (requestCode) {
                    case DetailActivity.REQUEST_CODE:
                        assert extras != null;
                        int extraResultCode = extras.getInt(RESULT_CODE);

                        if (resultCode == RESULT_OK) {
                            String name = extras.getString(RESULT_EXTRA_NAME);
//                            long id = extras.getLong( RESULT_EXTRA_ID );
//                            int quantity = extras.getInt( RESULT_EXTRA_QUANTITY );

                            switch (extraResultCode) {
                                case REMOVE:
//                                    QueueRepository.getInstance().delete( id );

                                    showSnackBar(
                                            String.format(getString(R.string.toast_details_removed_format), name));
                                    break;

                                case UPDATE:
//                                    QueueRepository.getInstance().setQuantity( this, id, quantity );

                                    showSnackBar(
                                            String.format(getString(R.string.toast_details_updated_format), name));
                                    break;

                                case ADD:
//                                    QueueRepository.getInstance().setQuantity( this, id, quantity );

                                    showSnackBar(String.format(getString(R.string.toast_details_added_format), name));
                                    break;
                            }
                        } else {
                            if (extraResultCode == ERROR) {
                                Exception e = (Exception) extras.get(RESULT_EXTRA_NAME);

                                if (e != null) {
                                    showSnackBar(getString(R.string.toast_details_error));

                                    ExceptionObservable.getInstance().notifyExceptionCaught(TAG, e);
                                }
                            } else {
                                showSnackBar(getString(R.string.toast_details_no_change));
                            }
                        }
                        break;

                    case SettingsActivity.REQUEST_CODE:
                        if (resultCode == RESULT_OK) {
                            assert extras != null;
                            boolean dlcValueChange = extras.getBoolean(getString(R.string.pref_dlc_key));
                            boolean categoryPrefChange = extras
                                    .getBoolean(getString(R.string.pref_filter_category_key));
                            boolean stationPrefChange = extras
                                    .getBoolean(getString(R.string.pref_filter_station_key));
                            boolean levelPrefChange = extras
                                    .getBoolean(getString(R.string.pref_filter_level_key));
                            boolean levelValueChange = extras
                                    .getBoolean(getString(R.string.pref_edit_text_level_key));
                            boolean refinedPrefChange = extras
                                    .getBoolean(getString(R.string.pref_filter_refined_key));

                            showSnackBar(getString(R.string.toast_settings_success));

                            PrefsObserver.getInstance().notifyPreferencesChanged(
                                    dlcValueChange, categoryPrefChange, stationPrefChange, levelPrefChange,
                                    levelValueChange, refinedPrefChange);
                        } else {
                            showSnackBar(getString(R.string.toast_settings_fail));
                        }
                        break;
                }
            } else {
                showSnackBar(getString(R.string.toast_settings_fail));
            }
        }
    }

    //  Soft Exception
    //      Report exception
    //      Allow app to continue operation as normal
    @Override
    public void onException(String tag, Exception e) {
        ExceptionUtil.SendErrorReport(tag, e);
    }

    //  Hard Exception
    //      Report exception
    //      Show Error Dialog window
    @Override
    public void onFatalException(final String tag, final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ExceptionUtil.SendErrorReportWithAlertDialog(MainActivity.this, tag, e);
            }
        });
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.getStartActivityForResultTrigger().observe(this, new androidx.lifecycle.Observer() {
            @Override
            public void onChanged(Intent intent) {
                int requestCode = intent.getIntExtra(DetailActivity.REQUEST_EXTRA_CODE, -1);
                startActivityForResult(intent, requestCode);
            }
        });
    }

    private void registerListeners() {
        ExceptionObservable.getInstance().registerListener(this);
    }

    private void unregisterListeners() {
        ExceptionObservable.getInstance().unregisterListener(this);
    }

    private void setupViewPager() {
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MenuItem menuItem = mBottomNavigationView.getMenu().getItem(position);
                int itemId = menuItem.getItemId();
                mBottomNavigationView.setSelectedItemId(itemId);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupBottomNavigation() {
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int position = menuItem.getOrder();
                mViewPager.setCurrentItem(position);
                return true;
            }
        });
    }

    private void setupAds() {
        mAdUtil = new AdUtil(this, R.id.content_main);
    }

    private void showChangeLog() {
        try {
            ChangeLog changeLog = new ChangeLog(this);

            if (changeLog.firstRun()) {
                changeLog.getLogDialog().show();
            }
        } catch (Exception e) {
            // do nothing
        }
    }

    private void showSnackBar(String text) {
        Snackbar.make(findViewById(R.id.content_main), text, Snackbar.LENGTH_SHORT).show();
    }
}