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

package arc.resource.calculator.model.ui;

import arc.resource.calculator.model.ui.interactive.InteractiveFragment;
import arc.resource.calculator.model.ui.interactive.InteractiveLoadState;

public class InteractiveGameFragment extends InteractiveFragment {
    public static final String TAG = InteractiveGameFragment.class.getSimpleName();

    @Override
    public InteractiveGameViewModel getViewModel() {
        return (InteractiveGameViewModel) super.getViewModel();
    }

    @Override
    protected void setupViewModel() {
        super.setupViewModel();
        getViewModel().setup(requireActivity());
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        getMainViewModel().getGameEntityLiveData().observe(getViewLifecycleOwner(),
                gameEntity -> getViewModel().setGameEntity(gameEntity));
    }

    @Override
    protected void handleLoadingEvent(InteractiveLoadState loadState) {
        super.handleLoadingEvent(loadState);
        if (loadState == InteractiveLoadState.Loaded) {
            startViewModel();
        }
    }
}
