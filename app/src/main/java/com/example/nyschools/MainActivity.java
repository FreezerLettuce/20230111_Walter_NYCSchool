package com.example.nyschools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.nyschools.databinding.ActivityMainBinding;
import com.example.nyschools.model.NYCSchool;
import com.example.nyschools.model.UIState;
import com.example.nyschools.view.SchoolsFragment;
import com.example.nyschools.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static void initialiseSchoolFragment(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, new SchoolsFragment())
                .addToBackStack(null)
                .commit();
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialiseSchoolFragment(this);
    }
}