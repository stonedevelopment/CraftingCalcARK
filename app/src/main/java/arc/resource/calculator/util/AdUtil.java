package arc.resource.calculator.util;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import arc.resource.calculator.R;

public class AdUtil {

    public static void loadAdView( Activity activity ) {
        Context context = activity.getApplicationContext();

        MobileAds.initialize( context, context.getString( R.string.banner_ad_app_id ) );

        AdView adView = ( AdView ) activity.findViewById( R.id.banner_ad );
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd( adRequest );
    }
}
