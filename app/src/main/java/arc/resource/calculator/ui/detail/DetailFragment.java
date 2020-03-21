/*
 * Copyright (c) 2020 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */

package arc.resource.calculator.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import arc.resource.calculator.R;
import arc.resource.calculator.model.engram.DisplayEngram;

public class DetailFragment extends BottomSheetDialogFragment {
    public static final String TAG = DetailFragment.class.getCanonicalName();

    private DisplayEngram mEngram;
    private DetailViewModel mViewModel;

    private DetailFragment(DisplayEngram engram) {
        mEngram = engram;
    }

    public static DetailFragment newInstance(DisplayEngram engram) {
        return new DetailFragment(engram);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        final String imagePath = "file:///android_asset/" + mEngram.getImagePath();
        Picasso.with(getContext())
                .load(imagePath)
                .error(R.drawable.placeholder_empty)
                .placeholder(R.drawable.placeholder_empty)
                .into(imageView);

        MaterialTextView titleTextView = view.findViewById(R.id.titleTextView);
        titleTextView.setText(mEngram.getName());

        MaterialTextView craftingStationTextView = view.findViewById(R.id.craftingStationTextView);

        MaterialTextView folderTextView = view.findViewById(R.id.folderTextView);
        folderTextView.setText(mEngram.getFolder());

        MaterialTextView descriptionTextView = view.findViewById(R.id.descriptionTextView);

        return view;
    }
}
