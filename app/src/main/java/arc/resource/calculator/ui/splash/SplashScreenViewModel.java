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

package arc.resource.calculator.ui.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import arc.resource.calculator.model.SingleLiveEvent;

public class SplashScreenViewModel extends AndroidViewModel {
    private SingleLiveEvent<SplashScreenViewState> viewState = new SingleLiveEvent<>();
    private SingleLiveEvent<SplashScreenViewPhase> viewPhase = new SingleLiveEvent<>();

    public SplashScreenViewModel(@NonNull Application application) {
        super(application);
    }

    SingleLiveEvent<SplashScreenViewState> getViewState() {
        return viewState;
    }

    private void setViewState(SingleLiveEvent<SplashScreenViewState> viewState) {
        this.viewState = viewState;
    }

    SingleLiveEvent<SplashScreenViewPhase> getViewPhase() {
        return viewPhase;
    }

    private void setViewPhase(SplashScreenViewPhase viewPhase) {
        this.viewPhase.setValue(viewPhase);
    }

    void beginPhases() {
        setViewPhase(SplashScreenViewPhase.CheckVersion);
    }
}
