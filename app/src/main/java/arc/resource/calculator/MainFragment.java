package arc.resource.calculator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jared on 12/31/2017.
 */

public class MainFragment extends Fragment {

  /**
   * ViewModel
   * DataBinding
   * DataAdapter (RecyclerView.Adapter)
   */

  FloatingActionButton mFab;

  BottomSheetBehavior mBottomSheet;

  View.OnClickListener mFabClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      hideFab();
      showBottomSheet();
    }
  };

  BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback =
      new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
          switch (newState) {
            case BottomSheetBehavior.STATE_EXPANDED:
              hideFab();
              break;
            case BottomSheetBehavior.STATE_HIDDEN:
              showFab();
              break;
          }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
      };

  public MainFragment() {
    //  Empty constructor as required
  }

  public static MainFragment newInstance() {
    return new MainFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    //  DataBinding = dataBinding.inflate(args..)

    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    setupSnackbar();

    setupFab();

    setupBottomSheet();

    setupListAdapter();


  }

  private void setupListAdapter() {

  }

  private void setupSnackbar() {
  }


  void setupFab() {
    mFab = getActivity().findViewById(R.id.fab);
    mFab.setOnClickListener(mFabClickListener);
  }

  void hideFab() {
    mFab.hide();
  }

  void showFab() {
    mFab.show();
  }

  void setupBottomSheet() {
    mBottomSheet = BottomSheetBehavior.from(
        getActivity().findViewById(R.id.bottom_sheet));

    hideBottomSheet();
  }

  void hideBottomSheet() {
    mBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
  }

  void showBottomSheet() {
    mBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
  }
}
