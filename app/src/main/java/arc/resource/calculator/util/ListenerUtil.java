package arc.resource.calculator.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.listeners.CraftingQueueListener;
import arc.resource.calculator.listeners.DisplayCaseListener;
import arc.resource.calculator.listeners.MainActivityListener;
import arc.resource.calculator.listeners.QueueEngramListener;
import arc.resource.calculator.listeners.QueueResourceListener;
import arc.resource.calculator.listeners.SendErrorReportListener;

public class ListenerUtil {
    private final String TAG = ListenerUtil.class.getSimpleName();

    private static ListenerUtil sInstance;

    private MainActivityListener mMainActivityListener;
    private List<DisplayCaseListener> mDisplayCaseListeners;
    private List<CraftingQueueListener> mCraftingQueueListeners;
    private List<QueueEngramListener> mQueueEngramListeners;
    private List<QueueResourceListener> mQueueResourceListeners;
    private List<SendErrorReportListener> mSendErrorReportListeners;

    public static ListenerUtil getInstance() {
        if ( sInstance == null ) {
            sInstance = new ListenerUtil();
        }

        return sInstance;
    }

    private ListenerUtil() {
        mDisplayCaseListeners = new ArrayList<>();
        mCraftingQueueListeners = new ArrayList<>();
        mQueueEngramListeners = new ArrayList<>();
        mQueueResourceListeners = new ArrayList<>();
        mSendErrorReportListeners = new ArrayList<>();
    }

    public void setMainActivityListener( MainActivityListener newListener ) {
        mMainActivityListener = newListener;
    }

    public void addDisplayCaseListener( DisplayCaseListener newListener ) {
        mDisplayCaseListeners.add( newListener );
    }

    public void addCraftingQueueListener( CraftingQueueListener newListener ) {
        mCraftingQueueListeners.add( newListener );
    }

    public void addCraftingQueueEngramListener( QueueEngramListener newListener ) {
        mQueueEngramListeners.add( newListener );
    }

    public void addCraftingQueueResourceListener( QueueResourceListener newListener ) {
        mQueueResourceListeners.add( newListener );
    }

    public void addSendErrorReportListener( SendErrorReportListener newListener ) {
        mSendErrorReportListeners.add( newListener );
    }

    public void removeSendErrorReportListener( SendErrorReportListener oldListener ) {
        mSendErrorReportListeners.remove( oldListener );
    }

    /**
     * MainActivityListener notifiers
     */

    public void notifyCategoryHierarchyResolved( String text ) {
        mMainActivityListener.onCategoryHierarchyResolved( text );
    }

    public void requestLayoutUpdate() {
        mMainActivityListener.onRequestLayoutUpdate();
    }

    /**
     * DisplayCaseListener notifiers
     */

    public void requestResetCategoryLevels( Context context ) {
        for ( DisplayCaseListener listener : mDisplayCaseListeners ) {
            listener.onRequestResetCategoryLevels( context );
        }
    }

    public void requestSaveCategoryLevels( Context context ) {
        for ( DisplayCaseListener listener : mDisplayCaseListeners ) {
            listener.onRequestSaveCategoryLevels( context );
        }
    }

    public void requestCategoryHierarchy( Context context ) {
        for ( DisplayCaseListener listener : mDisplayCaseListeners ) {
            listener.onRequestCategoryHierarchy( context );
        }
    }

    public void requestSearch( Context context, String searchText ) {
        for ( DisplayCaseListener listener : mDisplayCaseListeners ) {
            listener.onRequestSearch( context, searchText );
        }
    }

    public void notifyItemChanged( int position ) {
        for ( DisplayCaseListener listener : mDisplayCaseListeners ) {
            listener.onItemChanged( position );
        }
    }

    public void requestDisplayCaseDataSetChange( Context context ) {
        for ( DisplayCaseListener listener : mDisplayCaseListeners ) {
            listener.onRequestDisplayCaseDataSetChange( context );
        }
    }

    public void notifyDataSetChanged( Context context ) {
        for ( DisplayCaseListener listener : mDisplayCaseListeners ) {
            listener.onDisplayCaseDataSetChanged( context );
        }
    }

    /**
     * CraftingQueueListener notifiers
     */

    public void requestRemoveOneFromQueue( Context context, long engramId ) {
        for ( CraftingQueueListener listener : mCraftingQueueListeners ) {
            listener.onRequestRemoveOneFromQueue( context, engramId );
        }
    }

    public void requestRemoveAllFromQueue( Context context ) {
        for ( CraftingQueueListener listener : mCraftingQueueListeners ) {
            listener.onRequestRemoveAllFromQueue( context );
        }
    }

    public void requestIncreaseQuantity( Context context, int position ) {
        for ( CraftingQueueListener listener : mCraftingQueueListeners ) {
            listener.onRequestIncreaseQuantity( context, position );
        }
    }

    public void requestIncreaseQuantity( Context context, long engramId ) {
        for ( CraftingQueueListener listener : mCraftingQueueListeners ) {
            listener.onRequestIncreaseQuantity( context, engramId );
        }
    }

    public void requestUpdateQuantity( Context context, long engramId, int quantity ) {
        for ( CraftingQueueListener listener : mCraftingQueueListeners ) {
            listener.onRequestUpdateQuantity( context, engramId, quantity );
        }
    }

    public void notifyRowInserted( Context context, long queueId, long engramId, int quantity, boolean wasQueueEmpty ) {
        for ( CraftingQueueListener listener : mCraftingQueueListeners ) {
            listener.onRowInserted( context, queueId, engramId, quantity, wasQueueEmpty );
        }
    }

    public void notifyRowUpdated( Context context, long queueId, long engramId, int quantity ) {
        for ( CraftingQueueListener listener : mCraftingQueueListeners ) {
            listener.onRowUpdated( context, queueId, engramId, quantity );
        }
    }

    public void notifyRowDeleted( Context context, Uri uri, int positionStart, int itemCount, boolean isQueueEmpty ) {
        for ( CraftingQueueListener listener : mCraftingQueueListeners ) {
            listener.onRowDeleted( context, uri, positionStart, itemCount, isQueueEmpty );
        }
    }

    /**
     * QueueEngramListener notifiers
     */

    public void requestQueueEngramDataSetChange( Context context ) {
        for ( QueueEngramListener listener : mQueueEngramListeners ) {
            listener.onEngramDataSetChangeRequest( context );
        }
    }

    public void notifyQueueEngramDataSetChanged( boolean wasQueueEmpty ) {
        for ( QueueEngramListener listener : mQueueEngramListeners ) {
            listener.onEngramDataSetChanged( wasQueueEmpty );
        }
    }

    /**
     * QueueResourceListener notifiers
     */

    public void requestResourceDataSetChange( Context context ) {
        for ( QueueResourceListener listener : mQueueResourceListeners ) {
            listener.onResourceDataSetChangeRequest( context );
        }
    }

    public void notifyResourceDataSetChanged() {
        for ( QueueResourceListener listener : mQueueResourceListeners ) {
            listener.onResourceDataSetChanged();
        }
    }

    /**
     * SendErrorReportListener notifiers
     */

    public void emitSendErrorReportWithAlertDialog( String tag, Exception e ) {
        for ( SendErrorReportListener listener : mSendErrorReportListeners ) {
            listener.onSendErrorReport( tag, e, true );
        }
    }

    public void emitSendErrorReport( String tag, Exception e ) {
        for ( SendErrorReportListener listener : mSendErrorReportListeners ) {
            listener.onSendErrorReport( tag, e, false );
        }
    }
}