package com.example.appmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Detail extends AppCompatActivity {

    private ImageView img2,img3,back;
    private TextView tv4,tv5,tv6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        back  = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detail.this,MainActivity.class);
                startActivity(intent);
            }
        });
        getDataApi();
    }

    private void getDataApi() {
        if (isNetworkAvailable()) {
            Intent intent = getIntent();
            String movieId = intent.getStringExtra("movie_id");
            Toast.makeText(this, movieId, Toast.LENGTH_SHORT).show();
            Call<Data2> call = ApiConfig.getApiService().getMovieDetails(Integer.valueOf(movieId), "35254a98cc59f9518caf1bacbf0f5792");
            call.enqueue(new Callback<Data2>() {
                @Override
                public void onResponse(Call<Data2> call, Response<Data2> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Detail.this, "test", Toast.LENGTH_SHORT).show();
                        if (response.body() != null) {
                            Movie movie = response.body().getData2();
                            String judul = getIntent().getStringExtra("judul");
                            String rating = getIntent().getStringExtra("rating");
                            String synopsis = getIntent().getStringExtra("synopsis");
                            String backdropPath = getIntent().getStringExtra("backdrop");
                            String poster = getIntent().getStringExtra("poster");
                            tv4.setText(judul);
                            tv5.setText(rating);
                            tv6.setText(synopsis);
                            Glide.with(Detail.this)
                                    .load("https://image.tmdb.org/t/p/w500" + backdropPath)
                                    .into(img2);
                            Glide.with(Detail.this)
                                    .load("https://image.tmdb.org/t/p/w500" + poster)
                                    .into(img3);
                        }
                    } else {
                        Toast.makeText(Detail.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Data2> call, Throwable t) {
                    Toast.makeText(Detail.this, "Unable to fetch data!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
}
}
