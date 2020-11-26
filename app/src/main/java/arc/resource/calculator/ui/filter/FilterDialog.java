package arc.resource.calculator.ui.filter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.radiobutton.MaterialRadioButton;

import arc.resource.calculator.R;

public class FilterDialog extends DialogFragment {
    public static final String TAG = FilterDialog.class.getCanonicalName();

    private final FilterDialogViewModel viewModel;

    private MaterialToolbar toolbar;
    private MaterialRadioButton contentPreferencePrimaryRadioButton;
    private MaterialRadioButton contentPreferenceDlcMapRadioButton;
    private AppCompatSpinner contentPreferenceDlcMapSpinner;
    private MaterialRadioButton contentPreferenceDlcTotalConversionRadioButton;
    private AppCompatSpinner contentPreferenceDlcTotalConversionSpinner;

    public FilterDialog() {
        viewModel = new ViewModelProvider(requireActivity()).get(FilterDialogViewModel.class);
        viewModel.injectDependencies(requireActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View rootView = setViews(inflater.inflate(R.layout.filter_settings_fragment, null));
        builder.setView(rootView);
        return builder.create();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
        setupViews();
    }

    private View setViews(View rootView) {
        toolbar = rootView.findViewById(R.id.toolbar);
        contentPreferencePrimaryRadioButton = rootView.findViewById(R.id.contentPreferencePrimaryRadioButton);
        contentPreferenceDlcMapRadioButton = rootView.findViewById(R.id.contentPreferenceDlcMapRadioButton);
        contentPreferenceDlcMapSpinner = rootView.findViewById(R.id.contentPreferenceDlcMapSpinner);
        contentPreferenceDlcTotalConversionRadioButton = rootView.findViewById(R.id.contentPreferenceDlcTotalConversionRadioButton);
        contentPreferenceDlcTotalConversionSpinner = rootView.findViewById(R.id.contentPreferenceDlcTotalConversionSpinner);
        return rootView;
    }

    private void setupViewModel() {
        viewModel.getLoadingEvent().observe(requireActivity(), isLoaded -> {
            if (isLoaded) {
                // TODO: 11/26/2020 create loading events for data load
            }
        });
    }

    private void setupViews() {
        toolbar.setNavigationOnClickListener(listener -> dismiss());
        toolbar.setOnMenuItemClickListener(item -> {
            Log.d(TAG, item.getTitle().toString());
            return false;
        });
        contentPreferencePrimaryRadioButton.setOnClickListener(v -> onContentPreferenceChange(v.getId()));
        contentPreferenceDlcMapRadioButton.setOnClickListener(v -> onContentPreferenceChange(v.getId()));
        contentPreferenceDlcMapSpinner.setAdapter(new FilterContentPreferenceItemAdapter(requireActivity(), viewModel));
        contentPreferenceDlcMapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: ");
                onContentPreferenceChange(R.id.contentPreferenceDlcMapSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        contentPreferenceDlcTotalConversionRadioButton.setOnClickListener(v -> onContentPreferenceChange(v.getId()));
        contentPreferenceDlcTotalConversionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onContentPreferenceChange(R.id.contentPreferenceDlcTotalConversionSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Log.d(TAG, "onDismiss()");
        super.onDismiss(dialog);
    }

    public void onContentPreferenceChange(int id) {
        Log.d(TAG, "onContentPreferenceChange: " + id);
        switch (id) {
            case R.id.contentPreferencePrimaryRadioButton:
                contentPreferencePrimaryRadioButton.setChecked(true);
                contentPreferenceDlcMapRadioButton.setChecked(false);
                contentPreferenceDlcTotalConversionRadioButton.setChecked(false);
                break;
            case R.id.contentPreferenceDlcMapRadioButton:
            case R.id.contentPreferenceDlcMapSpinner:
                contentPreferencePrimaryRadioButton.setChecked(false);
                contentPreferenceDlcMapRadioButton.setChecked(true);
                contentPreferenceDlcTotalConversionRadioButton.setChecked(false);
                break;
            case R.id.contentPreferenceDlcTotalConversionRadioButton:
            case R.id.contentPreferenceDlcTotalConversionSpinner:
                contentPreferencePrimaryRadioButton.setChecked(false);
                contentPreferenceDlcMapRadioButton.setChecked(false);
                contentPreferenceDlcTotalConversionRadioButton.setChecked(true);
                break;
        }
    }
}