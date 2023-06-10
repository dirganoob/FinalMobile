package com.example.appmovie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data2 {
    @SerializedName("results")

    private Movie results;

    public Movie getData2() {
        return results;
    }
}
