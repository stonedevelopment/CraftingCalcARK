package arc.resource.calculator.model.ui;

import arc.resource.calculator.model.SingleLiveEvent;

public interface LoadingViewModel {
    SingleLiveEvent<Boolean> isLoadingEvent = new SingleLiveEvent<>();

    default boolean isLoading() {
        return isLoadingEvent.getValue() == null ? false : isLoadingEvent.getValue();
    }

    default SingleLiveEvent<Boolean> getLoadingEvent() {
        return isLoadingEvent;
    }

    default void setIsLoading(boolean isLoading) {
        isLoadingEvent.setValue(isLoading);
    }
}
