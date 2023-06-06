package com.example.appmovie;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("id")
    private String id;
    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    public Movie(String posterPath,String title,String releaseDate){

    }

    public String getId() { return id; }
    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
