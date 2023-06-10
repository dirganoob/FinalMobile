package com.example.appmovie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data3 {
    @SerializedName("results")

    private List<Tvshow> results;

    public List<Tvshow> getData3() {
        return results;
    }
}
