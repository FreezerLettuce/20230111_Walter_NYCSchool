package com.example.nyschools.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nyschools.R;
import com.example.nyschools.adapter.SchoolClicked;
import com.example.nyschools.adapter.SchoolsAdapter;
import com.example.nyschools.databinding.FragmentSchoolsBinding;
import com.example.nyschools.model.NYCSchool;
import com.example.nyschools.model.UIState;
import com.example.nyschools.viewmodel.MainViewModel;

import java.util.List;

public class SchoolsFragment extends Fragment implements SchoolClicked {

    private static final String TAG = "SchoolsFragment";

    private FragmentSchoolsBinding binding;
    private MainViewModel vm;
    private SchoolsAdapter schoolsAdapter;

    public SchoolsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        schoolsAdapter = new SchoolsAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSchoolsBinding.inflate(inflater, container, false);

        binding.rvSchools.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvSchools.setAdapter(schoolsAdapter);

        vm.schools.observe(getViewLifecycleOwner(), uiState -> {
            if (uiState instanceof UIState.Success) {
                binding.loadingState.setVisibility(View.GONE);
                binding.rvSchools.setVisibility(View.VISIBLE);
                List<NYCSchool> newSchools = ((UIState.Success<List<NYCSchool>>) uiState).getResponse();
                Log.d(TAG, newSchools.toString());
                schoolsAdapter.updateSchools(newSchools);

            } else if(uiState instanceof UIState.Loading) {
                binding.loadingState.setVisibility(View.VISIBLE);
                binding.rvSchools.setVisibility(View.GONE);
            } else {
                String msg = ((UIState.Error) uiState).getMsg();
                Log.e(TAG, msg);
                binding.loadingState.setVisibility(View.GONE);
                binding.rvSchools.setVisibility(View.GONE);

                new AlertDialog.Builder(requireActivity())
                        .setTitle("Error occurred")
                        .setMessage(msg)
                        .setPositiveButton("Retry", (dialogInterface, i) -> {
                            vm.retryGetSchools();
                            dialogInterface.dismiss();
                        })
                        .setNegativeButton("DISMISS", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                        .create()
                        .show();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onSchoolClicked(NYCSchool school) {
        vm.selectedSchool = school;

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragment, DetailsScoreFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}