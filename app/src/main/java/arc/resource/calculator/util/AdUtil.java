package arc.resource.calculator.util;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import arc.resource.calculator.BuildConfig;
import arc.resource.calculator.R;

public class AdUtil {
    private static final String TAG = AdUtil.class.getSimpleName();

    private final Activity mActivity;
    private final int mLayoutId;
    private final AdView mAdView;

    public AdUtil( Activity activity, int layoutId ) {
        mAdView = new AdView( activity );

        mActivity = activity;
        mLayoutId = layoutId;
    }

    public void init() {
        if ( !BuildConfig.DEBUG )
            loadAdView();
    }

    public void resume() {
        if ( mAdView != null )
            mAdView.resume();
    }

    public void pause() {
        if ( mAdView != null )
            mAdView.pause();
    }

    public void destroy() {
        if ( mAdView != null )
            mAdView.destroy();
    }

    private void loadAdView() {
        Context context = mActivity.getApplicationContext();

        String adAppId = context.getString( R.string.banner_ad_app_id );
        String adUnitId = context.getString( R.string.banner_ad_unit_id );

        MobileAds.initialize( context, adAppId );

        if ( mAdView.getAdSize() == null )
            mAdView.setAdSize( AdSize.SMART_BANNER );

        if ( mAdView.getAdUnitId() == null )
            mAdView.setAdUnitId( adUnitId );

        ViewGroup layout = ( ViewGroup ) mActivity.findViewById( mLayoutId );
        layout.addView( mAdView );

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd( adRequest );
    }

    public void reloadAdView() {
        loadAdView();
    }

    public void unloadAdView() {
        ViewGroup layout = ( ViewGroup ) mActivity.findViewById( mLayoutId );
        layout.removeView( mAdView );

        destroy();
    }
}
