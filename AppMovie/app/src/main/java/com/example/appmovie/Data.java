package com.example.appmovie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("results")

    private List<Movie> results;

    public List<Movie> getData() {
        return results;
    }
}
