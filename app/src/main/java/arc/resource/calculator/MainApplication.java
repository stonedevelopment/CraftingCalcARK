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

import android.app.Activity;
import android.app.Application;

import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.PlayStoreListener;

import javax.annotation.Nonnull;

import arc.resource.calculator.model.Encryption;

import static arc.resource.calculator.util.PurchaseUtil.buildPublicKey;

public class MainApplication extends Application {

    @Nonnull
    private final Billing mBilling = new Billing(this, new Billing.DefaultConfiguration() {
        @Nonnull
        @Override
        public String getPublicKey() {
            return Encryption.decrypt(buildPublicKey(), BuildConfig.APPLICATION_ID);
        }
    });

    public static MainApplication get(Activity activity) {
        return (MainApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBilling.addPlayStoreListener(new PlayStoreListener() {
            @Override
            public void onPurchasesChanged() {

            }
        });
    }

    @Nonnull
    public Billing getBilling() {
        return mBilling;
    }
}
