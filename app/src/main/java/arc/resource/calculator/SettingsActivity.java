package arc.resource.calculator;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.MenuItem;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace( android.R.id.content, new SettingsFragment() )
                .commit();
    }

    @Override
    public boolean onMenuItemSelected( int featureId, MenuItem item ) {
        int id = item.getItemId();
        if ( id == android.R.id.home ) {
            finish();
        }
        return super.onMenuItemSelected( featureId, item );
    }

    public static class SettingsFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {
        private static final String TAG = SettingsFragment.class.getSimpleName();

        @Override
        public void onCreate( Bundle savedInstanceState ) {
            super.onCreate( savedInstanceState );

            addPreferencesFromResource( R.xml.preferences );

            ListPreference pref = ( ListPreference ) findPreference( getString( R.string.pref_edit_text_level_key ) );
            pref.setEntries( getEntries() );
            pref.setEntryValues( pref.getEntries() );
        }

        private String[] getEntries() {
            int max = Integer.parseInt( getString( R.string.level_max_value ) );

            String[] entries = new String[max];

            for ( int i = 0; i < max; i++ ) {
                entries[i] = Integer.toString( i + 1 );
            }

            return entries;
        }

        @Override
        public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key ) {
            Preference pref = findPreference( key );

            if ( pref instanceof ListPreference ) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list (since they have separate labels/values).
                ListPreference listPref = ( ListPreference ) pref;
                int prefIndex = listPref.findIndexOfValue( key );
                if ( prefIndex >= 0 ) {
                    if ( key.equals( getString( R.string.pref_dlc_key ) ) ) {
                        listPref.setSummary( String.format(
                                getString( R.string.pref_dlc_description ),
                                listPref.getEntries()[prefIndex] )
                        );
                    } else if ( key.equals( getString( R.string.pref_edit_text_level_key ) ) ) {
                        listPref.setSummary( String.format(
                                getString( R.string.pref_edit_text_level_description ),
                                listPref.getEntries()[prefIndex] )
                        );
                    }
                }
            }

            getActivity().getIntent().putExtra( key, true );
            getActivity().setResult( RESULT_OK, getActivity().getIntent() );
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener( this );
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener( this );
        }
    }
}
