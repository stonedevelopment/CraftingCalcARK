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

package arc.resource.calculator.tasks.fetch.queue;

import arc.resource.calculator.model.map.QueueMap;
import arc.resource.calculator.tasks.fetch.FetchDataTaskObservable;
import arc.resource.calculator.tasks.fetch.FetchDataTaskObserver;

class FetchQueueDataTaskObservable extends FetchDataTaskObservable {
    FetchQueueDataTaskObservable(FetchDataTaskObserver observer) {
        super(observer);
    }

    @Override
    public FetchQueueDataTaskObserver getObserver() {
        return (FetchQueueDataTaskObserver) super.getObserver();
    }

    void notifyFetchSuccess(QueueMap queueMap) {
        if (getObserver() != null)
            getObserver().onFetchSuccess(queueMap);
    }
}
