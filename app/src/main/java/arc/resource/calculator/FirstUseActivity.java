package arc.resource.calculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;

public class FirstUseActivity extends AppIntro {

    public static final int REQUEST_CODE = 3000;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        addSlide( SlideFragment.newInstance(
                R.string.content_intro_title_first,
                R.string.content_intro_description_first,
                "scenary_island.jpg",
                "selfie_looking_to_the_side.png",
                R.layout.content_intro_last ) );

        addSlide( SlideFragment.newInstance( R.string.content_intro_title_crafting_stations,
                R.string.content_intro_description_crafting_stations,
                "screen_crafting_stations.jpg",
                "selfie.png" ) );

        addSlide( SlideFragment.newInstance( R.string.content_intro_title_folders,
                R.string.content_intro_description_folders,
                "screen_folders.jpg",
                "emote_wave.png",
                R.layout.content_intro_left ) );

        addSlide( SlideFragment.newInstance( R.string.content_intro_title_engrams,
                R.string.content_intro_description_engrams,
                "screen_engrams.jpg",
                "emote_cheer.png" ) );

        addSlide( SlideFragment.newInstance( R.string.content_intro_title_crafting_queue,
                R.string.content_intro_description_crafting_queue,
                "screen_crafting_queue.jpg",
                "emote_heart_with_wink.png",
                R.layout.content_intro_left ) );

        addSlide( SlideFragment.newInstance( R.string.content_intro_title_viewing_resources,
                R.string.content_intro_description_viewing_resources,
                "screen_view_inventory.jpg",
                "emote_salute.png" ) );

        addSlide( SlideFragment.newInstance( R.string.content_intro_title_inventory,
                R.string.content_intro_description_inventory,
                "screen_inventory.jpg",
                "emote_laugh.png" ) );

        addSlide( SlideFragment.newInstance( R.string.content_intro_title_dlc,
                R.string.content_intro_description_dlc,
                "screen_preferences.jpg",
                "emote_heart.png" ) );

        addSlide( SlideFragment.newInstance( R.string.content_intro_title_remove_ads,
                R.string.content_intro_description_remove_ads,
                "screen_remove_ads.jpg",
                "emote_evil.png" ) );

        addSlide( SlideFragment.newInstance( R.string.content_intro_title_last,
                R.string.content_intro_description_last,
                "scenary_island.jpg",
                "emote_thank.png",
                R.layout.content_intro_last ) );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishActivity( false );
    }

    @Override
    public void onSkipPressed( Fragment currentFragment ) {
        super.onSkipPressed( currentFragment );

        finishActivity( false );
    }

    @Override
    public void onDonePressed( Fragment currentFragment ) {
        super.onDonePressed( currentFragment );

        finishActivity( true );
    }

    private void finishActivity( boolean completedTutorial ) {
        if ( completedTutorial ) {
            setResult( RESULT_OK, getIntent() );
        } else {
            setResult( RESULT_CANCELED, getIntent() );
        }

        finish();
    }
}
