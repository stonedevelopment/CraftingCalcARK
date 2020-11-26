package arc.resource.calculator.model.ui;

import arc.resource.calculator.model.SingleLiveEvent;

public interface SnackBarViewModel {
    SingleLiveEvent<String> snackBarMessageEvent = new SingleLiveEvent<>();

    default SingleLiveEvent<String> getSnackBarMessageEvent() {
        return snackBarMessageEvent;
    }

    default void setSnackBarMessage(String message) {
        snackBarMessageEvent.setValue(message);
    }

    default void sendMessageToSnackBar(String message) {
        setSnackBarMessage(message);
    }
}
