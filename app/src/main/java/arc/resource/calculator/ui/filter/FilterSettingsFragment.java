package arc.resource.calculator.ui.filter;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import arc.resource.calculator.R;

public class FilterSettingsFragment extends DialogFragment {
    public static final String TAG = FilterSettingsFragment.class.getCanonicalName();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity());
        builder.setView(R.layout.filter_settings_fragment);
        return builder.create();
    }
}