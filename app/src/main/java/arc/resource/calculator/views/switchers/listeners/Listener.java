package arc.resource.calculator.views.switchers.listeners;

public interface Listener {
    void onError( Exception e );

    void onInit();

    void onPopulated();

    void onEmpty();
}