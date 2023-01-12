package com.example.nyschools.service;

import com.example.nyschools.model.NYCSchool;
import com.example.nyschools.model.SatScores;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface Repository {
    Single<List<NYCSchool>> fetchSchools();
    Single<List<SatScores>> fetchSatScores();
}
