package arc.resource.calculator.util;

public class ListenerUtil {
    private final String TAG = ListenerUtil.class.getSimpleName();

    private static ListenerUtil sInstance;

    public static ListenerUtil getInstance() {
        if ( sInstance == null )
            sInstance = new ListenerUtil();

        return sInstance;
    }

    private ListenerUtil() {
    }

}