package com.example.nyschools.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nyschools.databinding.NycSchoolItemBinding;
import com.example.nyschools.model.NYCSchool;

public class SchoolViewHolder extends RecyclerView.ViewHolder {

    NycSchoolItemBinding binding;

    public SchoolViewHolder(NycSchoolItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindSchool(NYCSchool school, SchoolClicked schoolClicked) {
        binding.tvSchoolName.setText(school.getSchoolName());
        binding.tvSchoolAddress.setText(school.getAddress());

        binding.btnSatScores.setOnClickListener(view -> {
            schoolClicked.onSchoolClicked(school);
        });
    }
}
