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

package arc.resource.calculator.ui.splash;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textview.MaterialTextView;

import arc.resource.calculator.R;

public class SplashScreenActivity extends AppCompatActivity {

    private SplashScreenViewModel viewModel;

    private MaterialTextView phaseText;
    private MaterialTextView statusText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        phaseText = findViewById(R.id.phaseText);
        statusText = findViewById(R.id.statusText);
        progressBar = findViewById(R.id.loadingProgressBar);

        setupViewModel();
    }

    @SuppressLint("DefaultLocale")
    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(SplashScreenViewModel.class);
        viewModel.getPhaseEvent().observe(this, viewPhase -> {
            int phaseIndex = viewModel.getPhaseIndex();
            int phaseTotal = viewModel.getPhaseTotal();
            int phasePercentage = phaseIndex / phaseTotal;

            //  update text views of current phase
            phaseText.setText(String.format("Phase %d/%d", phaseIndex, phaseTotal));
            statusText.setText(viewPhase.toString()); // TODO: 4/12/2020  get status text of current phase

            //  update progress bar with phase progress
            progressBar.setSecondaryProgress(phasePercentage);
        });
    }
}
