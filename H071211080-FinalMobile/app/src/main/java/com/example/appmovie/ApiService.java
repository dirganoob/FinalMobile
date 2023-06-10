package com.example.appmovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/popular")
    Call<Data> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Data2> getMovieDetails(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<Data3> getPopularTVShows(@Query("api_key") String apiKey);

    @GET("tv/{id}")
    Call<Data4> getTVShowDetails(@Path("id") int tvShowId, @Query("api_key") String apiKey);
}
