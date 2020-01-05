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

package arc.resource.calculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QueueViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mSnackBar = new MutableLiveData<>();

    MutableLiveData<String> getSnackBar() {
        return mSnackBar;
    }

    void setSnackBarMessage(String s) {
        getSnackBar().setValue(s);
    }
}
