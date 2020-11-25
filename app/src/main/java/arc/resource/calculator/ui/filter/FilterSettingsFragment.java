package arc.resource.calculator.ui.filter;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;

import arc.resource.calculator.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class FilterSettingsFragment extends DialogFragment {
    public static final String TAG = FilterSettingsFragment.class.getCanonicalName();

    private MaterialToolbar toolbar;
    private RadioGroup radioGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setViews(inflater.inflate(R.layout.filter_settings_fragment, container, false));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            dismiss();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        return transaction.commit();
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