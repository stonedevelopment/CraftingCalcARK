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

package arc.resource.calculator.tasks.fetch;

public class FetchDataTaskObservable {

    private FetchDataTaskObserver mObserver;

    public FetchDataTaskObservable(FetchDataTaskObserver observer) {
        mObserver = observer;
    }

    public FetchDataTaskObserver getObserver() {
        return mObserver;
    }

    public void notifyPreFetch() {
        if (getObserver() != null)
            getObserver().onPreFetch();
    }

    public void notifyFetching() {
        if (getObserver() != null)
            getObserver().onFetching();
    }

    public void notifyFetchSuccess() {
        if (getObserver() != null)
            getObserver().onFetchSuccess();
    }

    public void notifyFetchExceptionCaught(Exception e) {
        if (getObserver() != null)
            getObserver().onFetchException(e);
    }

    public void notifyFetchFail() {
        if (getObserver() != null)
            getObserver().onFetchFail();
    }
}
