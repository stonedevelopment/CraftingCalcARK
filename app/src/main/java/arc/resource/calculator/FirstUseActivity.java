package arc.resource.calculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class FirstUseActivity extends AppIntro {

    public static final int REQUEST_CODE = 3000;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        addSlide( SampleSlide.newInstance( R.layout.content_intro_first ) );
        addSlide( AppIntroFragment.newInstance(
                "Crafting Stations",
                "This will usually be the first screen you'll see, if filtering by Crafting Station is enabled. " +
                        "All you have to do is choose wich station by simply clicking on it. Easy!",
                R.drawable.scenary_island, getResources().getColor( R.color.colorPrimary ) ) );
        addSlide( AppIntroFragment.newInstance(
                "Folders",
                "Hooray! Now that we've chosen a Crafting Station, we can move through the folders " +
                        "(matched up exactly with in-game folders), until you find the Engram you " +
                        "wish to add to the Crafting Queue. Simple!",
                R.drawable.prefs_icons_level, getResources().getColor( R.color.colorPrimary ) ) );
        addSlide( AppIntroFragment.newInstance(
                "Engrams",
                "You're doing great so far! Now, when you find the Engram you want to 'craft', simply " +
                        "click on it to 'add' it to the Crafting Queue. Once added, click on it " +
                        "to increase its quantity. Magic!",
                R.drawable.prefs_icons_station, getResources().getColor( R.color.colorPrimary ) ) );
        addSlide( AppIntroFragment.newInstance(
                "Crafting Queue",
                "This is the Crafting Queue. Anything you click on above will go down here. As in the previous " +
                        "page, you can click on any icon to increase its quantity or 'long press' it " +
                        "to see a menu that shows more options. If you wish to start over, click the 'Clear Queue' " +
                        "button at the bottom of the screen. Amazing!",
                R.drawable.prefs_icons_folder, getResources().getColor( R.color.colorPrimary ) ) );
        addSlide( AppIntroFragment.newInstance(
                "Viewing Resources (Inventory)",
                "Ready to craft? Let's get started! Click the View Inventory button at the top and " +
                        "you'll be taken to the Inventory screen, where you'll see all the Resources " +
                        "needed to craft your newly added items. Woot!",
                R.drawable.prefs_icons_level, getResources().getColor( R.color.colorPrimary ) ) );
        addSlide( AppIntroFragment.newInstance(
                "Inventory",
                "You still there? Good! This is the newest feature to A:RC - the Inventory screen. " +
                        "This allows you to see which Resources (materials) you need in order to craft " +
                        "your 'Crafting Queue'. Keeping with the theme of in-game UI, I wanted to " +
                        "line up this feature for future changes. Big things are coming!",
                R.drawable.prefs_icons_station, getResources().getColor( R.color.colorPrimary ) ) );
        addSlide( AppIntroFragment.newInstance(
                "Primitive+ & Scorched Earth",
                "Did you know that A:RC allows you to change DLC versions? It's true! Just use the main menu " +
                        "and click on Settings. Once in the Settings screen, simply click on Change DLC " +
                        "and choose which version you're currently playing. Boom!",
                R.drawable.prefs_icons_folder, getResources().getColor( R.color.colorPrimary ) ) );
        addSlide( AppIntroFragment.newInstance(
                "Remove Ads",
                "One last thing before I let you go. Nobody likes ads, I understand this, but they're " +
                        "a great way for me to continue working on great projects such as A:RC without " +
                        "asking you for moneys. With that said, if you do wish to contribute more, " +
                        "the option is available to Remove Ads. Just saying. ;)",
                R.drawable.prefs_icons_level, getResources().getColor( R.color.colorPrimary ) ) );
//        addSlide( AppIntroFragment.newInstance(
//                "That's All Folks!",
//                getString( R.string.content_first_use_description_last ),
//                R.drawable.prefs_icons_level, getResources().getColor( R.color.colorPrimary ) ) );
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
