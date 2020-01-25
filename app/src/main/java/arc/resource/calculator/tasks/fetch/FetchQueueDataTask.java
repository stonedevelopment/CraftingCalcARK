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

import android.content.Context;
import android.os.AsyncTask;
import android.util.LongSparseArray;

import java.lang.ref.WeakReference;

import arc.resource.calculator.model.map.QueueMap;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.repository.queue.QueueRepository;

public class FetchQueueDataTask extends AsyncTask<Void, Void, Boolean> {
    private final String TAG = FetchQueueDataTask.class.getSimpleName();

    private WeakReference<Context> mContext;
    private QueueMap mQueueMap;
    private FetchDataTaskObservable mDataTaskObservable;

    public FetchQueueDataTask(Context context, FetchDataTaskObserver observer) {
        mContext = new WeakReference<>(context);
        mQueueMap = new QueueMap();
        mDataTaskObservable = new FetchDataTaskObservable(observer);
    }

    Context getContext() {
        return mContext.get();
    }

    @Override
    protected void onPreExecute() {
        mDataTaskObservable.notifyPreFetch();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        mDataTaskObservable.notifyFetching();

        LongSparseArray<Integer> savedQueue = QueueRepository.convertJSONStringToQueue(getContext());
        if (savedQueue.size() > 0) {
            for (int i = 0; i < savedQueue.size(); i++) {
                long id = savedQueue.keyAt(i);
                int quantity = savedQueue.valueAt(i);

                try {
                    QueueEngram craftable = QueueRepository.query(getContext(), id, quantity);
                    mQueueMap.add(id, craftable);

                    if (isCancelled()) return false;
                } catch (Exception e) {
                    mDataTaskObservable.notifyFetchExceptionCaught(e);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean querySuccessful) {
        if (querySuccessful) {
            mDataTaskObservable.notifyFetchSuccess(mQueueMap);
        } else {
            mDataTaskObservable.notifyFetchFail();
        }
    }
}