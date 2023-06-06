package com.example.appmovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/popular")
    Call<Data> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
