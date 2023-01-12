package com.example.nyschools.service;

import com.example.nyschools.model.NYCSchool;
import com.example.nyschools.model.SatScores;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class RepositoryImpl implements Repository {

    private ServiceAPI service;

    public RepositoryImpl(ServiceAPI givenService){
        service = givenService;
    }

    @Override
    public Single<List<NYCSchool>> fetchSchools() {
        return service.getAllSchools();
    }

    @Override
    public Single<List<SatScores>> fetchSatScores() {
        return service.getAllScores();
    }
}
