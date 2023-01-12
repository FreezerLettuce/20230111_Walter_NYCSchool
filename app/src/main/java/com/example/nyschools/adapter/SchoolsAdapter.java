package com.example.nyschools.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nyschools.databinding.NycSchoolItemBinding;
import com.example.nyschools.model.NYCSchool;

import java.util.ArrayList;
import java.util.List;

public class SchoolsAdapter extends RecyclerView.Adapter<SchoolViewHolder> {

    private List<NYCSchool> schoolsSet = new ArrayList<>();
    private SchoolClicked schoolClicked;

    public SchoolsAdapter(SchoolClicked schoolClicked) {
        this.schoolClicked = schoolClicked;
    }

    public void updateSchools(List<NYCSchool> newSchools) {
        schoolsSet.clear();
        schoolsSet.addAll(newSchools);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SchoolViewHolder(
                NycSchoolItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolViewHolder holder, int position) {
        holder.bindSchool(schoolsSet.get(position), schoolClicked);
    }

    @Override
    public int getItemCount() {
        return schoolsSet.size();
    }
}
