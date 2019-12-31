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

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;

public class FirstUseActivity extends AppIntro {

    public static final int REQUEST_CODE = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SlideFragment.newInstance(
                R.string.content_intro_title_first,
                R.string.content_intro_description_first,
                "scenary_island.jpg",
                "emote_wave.webp",
                R.layout.content_intro_with_bob));

        addSlide(SlideFragment.newInstance(R.string.content_intro_title_crafting_stations,
                R.string.content_intro_description_crafting_stations,
                "screen_crafting_stations.jpg"));

        addSlide(SlideFragment.newInstance(R.string.content_intro_title_folders,
                R.string.content_intro_description_folders,
                "screen_folders.jpg",
                R.layout.content_intro_right));

        addSlide(SlideFragment.newInstance(R.string.content_intro_title_engrams,
                R.string.content_intro_description_engrams,
                "screen_engrams.jpg"));

        addSlide(SlideFragment.newInstance(R.string.content_intro_title_crafting_queue,
                R.string.content_intro_description_crafting_queue,
                "screen_crafting_queue.jpg",
                R.layout.content_intro_right));

        addSlide(SlideFragment.newInstance(R.string.content_intro_title_viewing_resources,
                R.string.content_intro_description_viewing_resources,
                "screen_view_inventory.jpg"));

        addSlide(SlideFragment.newInstance(R.string.content_intro_title_inventory,
                R.string.content_intro_description_inventory,
                "screen_inventory.jpg"));

        addSlide(SlideFragment.newInstance(R.string.content_intro_title_inventory_raw_materials,
                R.string.content_intro_description_inventory_raw_materials,
                "screen_inventory_raw_materials.jpg"));

        addSlide(SlideFragment.newInstance(R.string.content_intro_title_dlc,
                R.string.content_intro_description_dlc,
                "screen_preferences.jpg"));

        addSlide(SlideFragment.newInstance(R.string.content_intro_title_remove_ads,
                R.string.content_intro_description_remove_ads,
                "screen_remove_ads.jpg"));

        addSlide(SlideFragment.newInstance(R.string.content_intro_title_last,
                R.string.content_intro_description_last,
                "scenary_island.jpg",
                "emote_thank.webp",
                R.layout.content_intro_with_bob));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishActivity(false);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        finishActivity(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        finishActivity(true);
    }

    private void finishActivity(boolean completedTutorial) {
        if (completedTutorial) {
            setResult(RESULT_OK, getIntent());
        } else {
            setResult(RESULT_CANCELED, getIntent());
        }

        finish();
    }
}
