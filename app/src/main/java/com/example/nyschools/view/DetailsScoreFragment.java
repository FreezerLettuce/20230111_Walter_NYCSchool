package com.example.nyschools.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nyschools.MainActivity;
import com.example.nyschools.R;
import com.example.nyschools.databinding.FragmentDetailsScoreBinding;
import com.example.nyschools.model.NYCSchool;
import com.example.nyschools.model.SatScores;
import com.example.nyschools.model.UIState;
import com.example.nyschools.viewmodel.MainViewModel;

import java.util.List;

public class DetailsScoreFragment extends Fragment {

    private static final String TAG = "DetailsScoreFragment";

    private FragmentDetailsScoreBinding binding;
    private MainViewModel vm;

    public DetailsScoreFragment() {
        // Required empty public constructor
    }

    public static DetailsScoreFragment newInstance() {
        return new DetailsScoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailsScoreBinding.inflate(inflater, container, false);

        populateSchool();

        vm.scores.observe(getViewLifecycleOwner(), uiState -> {
            if (uiState instanceof UIState.Success) {
                SatScores scores = ((UIState.Success<SatScores>) uiState).getResponse();
                Log.d(TAG, scores.toString());
                binding.tvSatLoadingText.setVisibility(View.GONE);
                updateScores(scores);

            } else if(uiState instanceof UIState.Loading) {
                binding.tvSatLoadingText.setVisibility(View.VISIBLE);
            } else {
                Log.e(TAG, ((UIState.Error) uiState).getMsg());
                binding.tvSatLoadingText.setVisibility(View.VISIBLE);
                binding.tvSatLoadingText.setText(String.format("No scores available for this school"));
            }
        });

        return binding.getRoot();
    }

    private void populateSchool() {
        NYCSchool selSchool = vm.selectedSchool;
        if (selSchool != null) {
            binding.tvSatSchoolName.setText(selSchool.getSchoolName());
            binding.tvSatSchoolOverview.setText(selSchool.getOverviewParagraph());
            binding.tvSchoolAddress.setText("Address: ".concat(selSchool.getAddress()));

            vm.getSelectedSATScore();
        } else {
            new AlertDialog.Builder(requireActivity())
                    .setTitle("Error occurred")
                    .setMessage("No school has been selected")
                    .setNegativeButton("DISMISS", (dialogInterface, i) -> {
                        MainActivity.initialiseSchoolFragment(requireActivity());
                        dialogInterface.dismiss();
                    })
                    .create()
                    .show();
        }
    }

    private void updateScores(SatScores scores) {
        if (scores != null) {
            binding.tvAvgMath.setText("Math: ".concat(scores.getAvgMath()));
            binding.tvAvgReading.setText("Reading: ".concat(scores.getAvgReading()));
            binding.tvAvgWriting.setText("Writing: ".concat(scores.getAvgWriting()));
            binding.tvTakers.setText("Takers: ".concat(scores.getTestTakers()));
        }
    }
}