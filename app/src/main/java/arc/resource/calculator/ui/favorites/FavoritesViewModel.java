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

package arc.resource.calculator.ui.favorites;

import android.app.Application;

import androidx.annotation.NonNull;

import arc.resource.calculator.model.ui.InteractiveViewModel;

public class FavoritesViewModel extends InteractiveViewModel {
    public FavoritesViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void handleGameEntityLiveData() {
        super.handleGameEntityLiveData();
    }
}
