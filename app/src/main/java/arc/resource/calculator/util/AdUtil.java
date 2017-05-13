package arc.resource.calculator.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.EmptyRequestListener;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;

import javax.annotation.Nonnull;

import arc.resource.calculator.BuildConfig;
import arc.resource.calculator.MainApplication;
import arc.resource.calculator.R;

import static arc.resource.calculator.util.PurchaseUtil.SKU_FEATURE_DISABLE_ADS;

public class AdUtil {
    private static final String TAG = AdUtil.class.getSimpleName();

    private final Activity mActivity;
    private final int mLayoutId;
    private final AdView mAdView;
    private ActivityCheckout mCheckout;
    private boolean mLoaded = false;
    public boolean mRemoveAds = true;

    public AdUtil( Activity activity, int layoutId ) {
        mAdView = new AdView( activity );

        mActivity = activity;
        mLayoutId = layoutId;
        mCheckout = Checkout.forActivity( mActivity, MainApplication.get( mActivity ).getBilling() );

        mCheckout.start();
        mCheckout.loadInventory( Inventory.Request.create().loadAllPurchases(), new InventoryCallback() );
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

        mCheckout.stop();
    }

    private void loadAdView() {
        if ( mLoaded )
            return;

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

        mLoaded = true;
    }

    private void unloadAdView() {
        ViewGroup layout = ( ViewGroup ) mActivity.findViewById( mLayoutId );
        layout.removeView( mAdView );
        mLoaded = false;

        destroy();
    }

    public void beginPurchase() {
        mCheckout.startPurchaseFlow( ProductTypes.IN_APP, SKU_FEATURE_DISABLE_ADS, null, new PurchaseListener() );
    }

    public boolean onActivityResult( int requestCode, int resultCode, Intent data ) {
        return mCheckout.onActivityResult( requestCode, resultCode, data );
    }

    private void showSnackBar( String message ) {
        if ( BuildConfig.DEBUG )
            Snackbar.make( mActivity.findViewById( mLayoutId ), message, Snackbar.LENGTH_SHORT ).show();
    }

    private class InventoryCallback implements Inventory.Callback {
        @Override
        public void onLoaded( @Nonnull Inventory.Products products ) {
            final Inventory.Product product = products.get( ProductTypes.IN_APP );

            // billing is not supported, user can't purchase anything. Don't show ads in this case
            if ( !product.supported )
                return;

            if ( product.isPurchased( SKU_FEATURE_DISABLE_ADS ) ) {
                showSnackBar( SKU_FEATURE_DISABLE_ADS + " was previously purchased." );
                return;
            }

            showSnackBar( SKU_FEATURE_DISABLE_ADS + " was not purchased." );
            mRemoveAds = false;
            loadAdView();
        }
    }

    private class PurchaseListener extends EmptyRequestListener<Purchase> {
        @Override
        public void onSuccess( @Nonnull Purchase purchase ) {
            showSnackBar( SKU_FEATURE_DISABLE_ADS + " was just purchased successfully." );
            mRemoveAds = true;
            unloadAdView();
        }
    }
}
