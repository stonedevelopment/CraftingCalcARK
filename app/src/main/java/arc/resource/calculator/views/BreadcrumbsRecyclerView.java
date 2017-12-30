package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import arc.resource.calculator.R;

/**
 * Actions this class needs to respond to:
 * When User navigates forward (up), 1 level at a time.
 * When User navigates backward (down), 1 or more level at a time.
 * <p>
 * Actions this class notifies others of:
 * When User clicks on Item to navigate into, if not last Item in List.
 */
public class BreadcrumbsRecyclerView extends RecyclerView {

    Observable mObservable = new Observable();


    public interface NavigationObserver {
        /**
         * User navigated forward (up), into next level
         */
        void onNavigateUp();

        /**
         * User navigated backward (down), out of previous level or more.
         *
         * @param levelAmount Amount of levels to go back.
         */
        void onNavigateDown( int levelAmount );

        /**
         * User navigated to Location via other methods (Search)
         *
         * @param location_id ROWID of location (Category) to navigate to.
         */
        void onNavigate( long location_id );
    }

    public BreadcrumbsRecyclerView( Context context ) {
        super( context );
    }

    public BreadcrumbsRecyclerView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public BreadcrumbsRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
    }

//    public void onCreate( Listener listener ) {
//        Observer observer = new Observer() {
//            @Override
//            public void update( Observable o, Object arg ) {
//
//            }
//        };
//
//        setLayoutManager( new GridLayoutManager( getContext(), 1, GridLayoutManager.HORIZONTAL, false ) );
//        setListener( listener );
//        setAdapter( new Adapter( getContext(), this ) );
//    }
//
//    public void onResume() {
//        getAdapter().resume();
//    }
//
//    public void onPause() {
//        getAdapter().pause();
//    }
//
//    public void onDestroy() {
//        getAdapter().destroy();
//    }
//
//    private void setListener() {
//
//    }
//
//
//    public void resume() {
//        getCraftingQueue().resume();
//    }
//
//    public void pause() {
//        getCraftingQueue().pause( getContext() );
//    }
//
//    public void destroy() {
//        getCraftingQueue().destroy();
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
//        View itemView = LayoutInflater.from( parent.getContext() ).
//                inflate( R.layout.list_item_craftable_queue, parent, false );
//
//        return new ViewHolder( itemView );
//    }
//
//    @Override
//    public void onBindViewHolder( ViewHolder holder, int position ) {
//        getAdapter().
//    }
//
//    @Override
//    public long getItemId( int position ) {
//        try {
//            return getCraftingQueue().getCraftable( position ).getId();
//        } catch ( PositionOutOfBoundsException e ) {
//            getObserver().notifyExceptionCaught( e );
//        }
//
//        return super.getItemId( position );
//    }
//
//    @Override
//    public int getItemCount() {
//        return getCraftingQueue().getSize();
//    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final String TAG = ViewHolder.class.getSimpleName();

        private final TextView mNameView;

        private int mPosition;

        ViewHolder( View view ) {
            super( view );

            mNameView = view.findViewById( R.id.name_view );

            view.setOnClickListener( this );
        }

        TextView getNameView() {
            return mNameView;
        }

        void setPosition( int position ) {
            mPosition = position;
        }

        /**
         * Event which the User clicks on entire View.
         * <p>
         * TODO:    Check if in last place, if so: do nothing.
         * TODO:    Check if in last place, if not: change category to View's category_id
         *
         * @param view
         */
        @Override
        public void onClick( View view ) {

        }

    }

    public class Adapter extends RecyclerView.Adapter<ViewHolder> {
        List<Item> items;

        public Adapter() {
            items = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
            return null;
        }

        @Override
        public void onBindViewHolder( ViewHolder holder, int position ) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class Item {
            public String name;
            public long category_id;

        }
    }

}
