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

package arc.resource.calculator.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import arc.resource.calculator.listeners.NavigationObserver;

public class NavigationTextView extends AppCompatTextView {
    private static final String TAG = NavigationTextView.class.getSimpleName();

    private NavigationObserver.Listener mListener = new NavigationObserver.Listener() {
        @Override
        public void onUpdate(String text) {
            setText(text);
        }
    };

    public NavigationTextView(Context context) {
        super(context);
    }

    public NavigationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void create() {
        NavigationObserver.getInstance().registerListener(mListener);
    }

    public void resume() {
    }

    public void pause() {
    }

    public void destroy() {
        NavigationObserver.getInstance().unregisterListener(mListener);
    }
}
