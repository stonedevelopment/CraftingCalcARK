package arc.resource.calculator.ui.filter;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.appbar.MaterialToolbar;

import arc.resource.calculator.R;

public class FilterSettingsFragment extends DialogFragment {
    public static final String TAG = FilterSettingsFragment.class.getCanonicalName();

    private MaterialToolbar toolbar;
    private RadioGroup radioGroup;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(setViews(inflater.inflate(R.layout.filter_settings_fragment, null)));
        return builder.create();
    }

    private View setViews(View rootView) {
        toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(listener -> {
            dismiss();
        });
        radioGroup = rootView.findViewById(R.id.contentPreferenceRadioGroup);
        return rootView;
    }
}