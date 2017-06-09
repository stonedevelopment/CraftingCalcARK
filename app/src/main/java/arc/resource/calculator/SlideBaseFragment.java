package arc.resource.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.paolorotolo.appintro.ISlideSelectionListener;
import com.github.paolorotolo.appintro.util.LogHelper;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public abstract class SlideBaseFragment extends Fragment implements ISlideSelectionListener {
    protected static final String ARG_MAIN_IMAGE = "image";
    protected static final String ARG_SELFIE_IMAGE = "selfie";
    protected static final String ARG_TITLE = "title";
    protected static final String ARG_DESC = "desc";
    protected static final String ARG_LAYOUT = "layout";

    private static final String TAG = LogHelper.makeLogTag( SlideBaseFragment.class );

    private int title;
    private int description;
    private String mainImage;
    private String selfieImage;
    private int layoutId;

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setRetainInstance( true );

        if ( getArguments() != null && getArguments().size() != 0 ) {
            mainImage = getArguments().getString( ARG_MAIN_IMAGE );
            selfieImage = getArguments().getString( ARG_SELFIE_IMAGE );
            title = getArguments().getInt( ARG_TITLE );
            description = getArguments().getInt( ARG_DESC );
            layoutId = getArguments().getInt( ARG_LAYOUT );
        }
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        if ( savedInstanceState != null ) {
            mainImage = savedInstanceState.getString( ARG_MAIN_IMAGE );
            selfieImage = savedInstanceState.getString( ARG_SELFIE_IMAGE );
            title = savedInstanceState.getInt( ARG_TITLE );
            description = savedInstanceState.getInt( ARG_DESC );
            layoutId = savedInstanceState.getInt( ARG_LAYOUT );
        }
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState ) {
        View v = inflater.inflate( layoutId, container, false );

        TextView t = ( TextView ) v.findViewById( R.id.title );
        t.setText( getString( title ) );

        TextView d = ( TextView ) v.findViewById( R.id.description );
        d.setText( String.format( Locale.getDefault(), "\"%1$s\"", getString( description ) ) );

        if ( mainImage != null ) {
            ImageView i = ( ImageView ) v.findViewById( R.id.image );
            Picasso.with( getContext() )
                    .load( buildImagePath( mainImage ) )
                    .error( R.drawable.placeholder_empty )
                    .placeholder( R.drawable.placeholder_empty )
                    .into( i );
        }

        if ( selfieImage != null ) {
            ImageView s = ( ImageView ) v.findViewById( R.id.selfie );
            Picasso.with( getContext() )
                    .load( buildImagePath( selfieImage ) )
                    .error( R.drawable.placeholder_empty )
                    .placeholder( R.drawable.placeholder_empty )
                    .into( s );
        }

        return v;
    }

    @Override
    public void onSaveInstanceState( Bundle outState ) {
        outState.putString( ARG_MAIN_IMAGE, mainImage );
        outState.putString( ARG_SELFIE_IMAGE, selfieImage );
        outState.putInt( ARG_TITLE, title );
        outState.putInt( ARG_DESC, description );
        outState.putInt( ARG_LAYOUT, layoutId );
        super.onSaveInstanceState( outState );
    }

    @Override
    public void onSlideDeselected() {
        LogHelper.d( TAG, String.format( "Slide %s has been deselected.", title ) );
    }

    @Override
    public void onSlideSelected() {
        LogHelper.d( TAG, String.format( "Slide %s has been selected.", title ) );
    }

    private String buildImagePath( String imageFile ) {
        return "file:///android_asset/" + imageFile;
    }
}
