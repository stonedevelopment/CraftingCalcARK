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

package arc.resource.calculator.listeners;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.util.ExceptionUtil;

public class ExceptionObserver {
    private static final String TAG = ExceptionObserver.class.getSimpleName();

    private static ExceptionObserver sInstance;

    private List<Listener> mListeners;

    public interface Listener {
        void onException(String tag, Exception e);

        void onFatalException(String tag, Exception e);
    }

    public static ExceptionObserver getInstance() {
        if (sInstance == null)
            sInstance = new ExceptionObserver();

        return sInstance;
    }

    private ExceptionObserver() {
        mListeners = new ArrayList<>();
    }

    public void registerListener(Listener listener) {
        if (!mListeners.contains(listener))
            mListeners.add(listener);
    }

    public void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }

    public void notifyExceptionCaught(String tag, Exception e) {
        if (mListeners.size() > 0) {
            for (Listener listener : mListeners) {
                listener.onException(tag, e);
            }
        } else {
            ExceptionUtil.SendErrorReport(tag, e);
        }
    }

    public void notifyFatalExceptionCaught(String tag, Exception e) {
        if (mListeners.size() > 0) {
            for (Listener listener : mListeners) {
                listener.onFatalException(tag, e);
            }
        } else {
            ExceptionUtil.SendErrorReport(tag, e);
        }
    }
}