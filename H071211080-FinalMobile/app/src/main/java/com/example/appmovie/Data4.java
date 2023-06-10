package com.example.appmovie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data4 {
    @SerializedName("results")

    private Tvshow results;

    public Tvshow getData4() {
        return results;
    }
}
