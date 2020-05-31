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

import arc.resource.calculator.util.ExceptionUtil;

public class ExceptionObservable {
    private static final String TAG = ExceptionObservable.class.getSimpleName();

    private static ExceptionObservable sInstance;
    private Observer mObserver;

    public interface Observer {
        void onException(String tag, Exception e);

        void onFatalException(String tag, Exception e);
    }

    public static ExceptionObservable getInstance() {
        if (sInstance == null) sInstance = new ExceptionObservable();
        return sInstance;
    }

    public void registerObserver(Observer observer) {
        mObserver = observer;
    }

    public void unregisterObserver() {
        mObserver = null;
    }

    public void notifyExceptionCaught(String tag, Exception e) {
        //  send silent error report
        ExceptionUtil.SendErrorReport(tag, e, false);

        //  notify observer that an exception was thrown, allow observer to decide action
        if (mObserver != null)
            mObserver.onException(tag, e);
    }

    public void notifyFatalExceptionCaught(String tag, Exception e) {
        //  send silent error report
        ExceptionUtil.SendErrorReport(tag, e, false);

        //  notify observer that a fatal exception was thrown, allow observer to decide action
        if (mObserver != null)
            mObserver.onFatalException(tag, e);
    }
}