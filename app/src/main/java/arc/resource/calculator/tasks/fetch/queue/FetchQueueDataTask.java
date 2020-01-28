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

import android.content.Context;
import android.util.Log;
import android.util.LongSparseArray;

import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.model.map.QueueMap;
import arc.resource.calculator.repository.queue.QueueRepository;
import arc.resource.calculator.tasks.fetch.FetchDataTask;

public class FetchQueueDataTask extends FetchDataTask {
    private final String TAG = FetchQueueDataTask.class.getSimpleName();

    private QueueMap mQueueMap;

    public FetchQueueDataTask(Context context, FetchQueueDataTaskObservable observable) {
        super(context, observable);
        mQueueMap = new QueueMap();
    }

    @Override
    public FetchQueueDataTaskObservable getObservable() {
        return (FetchQueueDataTaskObservable) super.getObservable();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d(TAG, "doInBackground: notifyFetching");
        getObservable().notifyFetching();

        LongSparseArray<Integer> savedQueue = QueueRepository.convertJSONStringToQueue(getContext());
        if (savedQueue.size() > 0) {
            for (int i = 0; i < savedQueue.size(); i++) {
                long id = savedQueue.keyAt(i);
                int quantity = savedQueue.valueAt(i);

                try {
                    QueueEngram engram = QueueRepository.query(getContext(), id, quantity);
                    mQueueMap.add(id, engram);

                    if (isCancelled()) return false;
                } catch (Exception e) {
                    getObservable().notifyFetchExceptionCaught(e);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean querySuccessful) {
        if (querySuccessful) {
            getObservable().notifyFetchSuccess(mQueueMap);
        } else {
            getObservable().notifyFetchFail();
        }
    }
}