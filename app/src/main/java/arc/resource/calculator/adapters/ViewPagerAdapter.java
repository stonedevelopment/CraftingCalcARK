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

package arc.resource.calculator.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import arc.resource.calculator.ui.explorer.ExplorerFragment;
import arc.resource.calculator.ui.queue.QueueFragment;
import arc.resource.calculator.ui.search.SearchFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 2:
                return QueueFragment.newInstance();
            case 1:
                return SearchFragment.newInstance();
            case 0:
            default:
                return ExplorerFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
