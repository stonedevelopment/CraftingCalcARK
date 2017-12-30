package arc.resource.calculator.observers;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

public class ActivityLifeObserver implements LifecycleObserver {
    @OnLifecycleEvent( Lifecycle.Event.ON_CREATE )
    void onCreate() {}
}

