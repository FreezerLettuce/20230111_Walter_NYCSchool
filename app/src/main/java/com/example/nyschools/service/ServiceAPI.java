package com.example.nyschools.service;

import com.example.nyschools.model.NYCSchool;
import com.example.nyschools.model.SatScores;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ServiceAPI {

    String BASE_URL = "https://data.cityofnewyork.us/resource/";
    String SCHOOLS = "s3k6-pzi2.json";
    String SAT_SCORES = "f9bf-2cp4.json";

    @GET(SCHOOLS)
    Single<List<NYCSchool>> getAllSchools();

    @GET(SAT_SCORES)
    Single<List<SatScores>> getAllScores();
}
