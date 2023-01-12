package com.example.nyschools.model

import com.google.gson.annotations.SerializedName

data class SatScores(
    @SerializedName("dbn")
    val dbn: String,
    @SerializedName("school_name")
    val schoolName: String,
    @SerializedName("num_of_sat_test_takers")
    val testTakers: String,
    @SerializedName("sat_critical_reading_avg_score")
    val avgReading: String,
    @SerializedName("sat_math_avg_score")
    val avgMath: String,
    @SerializedName("sat_writing_avg_score")
    val avgWriting: String
)
