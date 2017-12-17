package arc.resource.calculator.views.switchers.listeners;

/**
 * Created by jared on 11/4/2017.
 */

public interface Observer {
    void notifyExceptionCaught( Exception e );

    void notifyInitializing();

    void notifyDataSetPopulated();

    void notifyDataSetEmpty();
}