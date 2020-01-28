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

package arc.resource.calculator.ui.explorer;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import arc.resource.calculator.listeners.NavigationObserver;

// TODO: 1/27/2020 Create ExplorerNavigationTextView ViewModel
public class ExplorerNavigationTextView extends AppCompatTextView {
    private static final String TAG = ExplorerNavigationTextView.class.getSimpleName();

    private NavigationObserver.Listener mListener = new NavigationObserver.Listener() {
        @Override
        public void onUpdate(String text) {
            setText(text);
        }
    };

    public ExplorerNavigationTextView(Context context) {
        super(context);
    }

    public ExplorerNavigationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExplorerNavigationTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onCreate() {
        //  do nothing
    }

    public void onResume() {
        NavigationObserver.getInstance().registerListener(mListener);
    }

    public void onPause() {
        NavigationObserver.getInstance().unregisterListener(mListener);
    }

    public void onDestroy() {
        //  do nothing
    }
}
