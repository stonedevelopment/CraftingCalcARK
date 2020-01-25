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

import arc.resource.calculator.model.map.SortableMap;

class FetchDataTaskObservable {

    private FetchDataTaskObserver mObserver;

    FetchDataTaskObservable(FetchDataTaskObserver observer) {
        mObserver = observer;
    }

    void notifyPreFetch() {
        if (mObserver != null)
            mObserver.onPreFetch();
    }

    void notifyFetching() {
        if (mObserver != null)
            mObserver.onFetching();
    }

    void notifyFetchSuccess(SortableMap fetchedMap) {
        if (mObserver != null)
            mObserver.onFetchSuccess(fetchedMap);
    }

    void notifyFetchExceptionCaught(Exception e) {
        if (mObserver != null)
            mObserver.onFetchException(e);
    }

    void notifyFetchFail() {
        if (mObserver != null)
            mObserver.onFetchFail();
    }
}
